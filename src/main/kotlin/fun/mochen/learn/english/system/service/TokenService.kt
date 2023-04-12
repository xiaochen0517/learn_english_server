package `fun`.mochen.learn.english.system.service

import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.StrUtil
import eu.bitwalker.useragentutils.UserAgent
import `fun`.mochen.learn.english.constant.BaseConstants
import `fun`.mochen.learn.english.constant.CacheConstants
import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.system.redis.RedisCache
import `fun`.mochen.learn.english.system.utils.AddressUtils
import `fun`.mochen.learn.english.system.utils.IpUtil
import `fun`.mochen.learn.english.system.utils.ServletUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest

@Component
class TokenService {

    companion object {

        protected const val MILLIS_SECOND: Long = 1000

        protected const val MILLIS_MINUTE = 60 * MILLIS_SECOND

        private const val MILLIS_MINUTE_TEN = 20 * 60 * 1000L
    }

    // 令牌自定义标识
    @Value("\${token.header}")
    private lateinit var header: String

    // 令牌秘钥
    @Value("\${token.secret}")
    private lateinit var secret: String

    // 令牌有效期（默认30分钟）
    @Value("\${token.expireTime}")
    private var expireTime = 0

    @Autowired
    private lateinit var redisCache: RedisCache

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    fun getLoginUser(request: HttpServletRequest): LoginUser? {
        // 获取请求携带的令牌
        val token = getToken(request)
        if (StrUtil.isNotEmpty(token)) try {
            val claims: Claims = parseToken(token)
            // 解析对应的权限以及用户信息
            val uuid = claims[BaseConstants.LOGIN_USER_KEY] as String
            val userKey = getTokenKey(uuid)
            return redisCache.getCacheObject(userKey)
        } catch (_: Exception) {
            // ignore
        }
        return null
    }

    /**
     * 设置用户身份信息
     */
    fun setLoginUser(loginUser: LoginUser) {
        if (StringUtils.isNotEmpty(loginUser.token)) {
            refreshToken(loginUser)
        }
    }

    /**
     * 删除用户身份信息
     */
    fun delLoginUser(token: String) {
        if (StringUtils.isNotEmpty(token)) {
            val userKey = getTokenKey(token)
            redisCache.deleteObject(userKey)
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    fun createToken(loginUser: LoginUser): String {
        val token: String = IdUtil.fastUUID()
        loginUser.token = token
        setUserAgent(loginUser)
        refreshToken(loginUser)
        val claims: MutableMap<String, Any> = mutableMapOf()
        claims[BaseConstants.LOGIN_USER_KEY] = token
        return createToken(claims)
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    fun verifyToken(loginUser: LoginUser) {
        val expireTime: Long = loginUser.expireTime
        val currentTime = System.currentTimeMillis()
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser)
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    fun refreshToken(loginUser: LoginUser) {
        loginUser.loginTime = System.currentTimeMillis()
        loginUser.expireTime = loginUser.loginTime + expireTime * MILLIS_MINUTE
        // 根据uuid将loginUser缓存
        val userKey = getTokenKey(loginUser.token)
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES)
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    fun setUserAgent(loginUser: LoginUser) {
        val userAgent: UserAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"))
        val ip: String = IpUtil.getIpAddr()
        loginUser.ipaddr = ip
        loginUser.loginLocation = AddressUtils.getRealAddressByIP(ip)
        loginUser.browser = userAgent.getBrowser().getName()
        loginUser.os = userAgent.getOperatingSystem().getName()
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private fun createToken(claims: Map<String, Any>): String {
        val signingKey = SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS512.jcaName)
        return Jwts.builder()
            .setClaims(claims)
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact()
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private fun parseToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    fun getUsernameFromToken(token: String): String? {
        val claims: Claims = parseToken(token)
        return claims.getSubject()
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    fun getToken(request: HttpServletRequest): String {
        var token = request.getHeader(header)
        if (StringUtils.isNotEmpty(token) && token.startsWith(BaseConstants.TOKEN_PREFIX)) {
            token = token.replace(BaseConstants.TOKEN_PREFIX, "")
        }
        return token ?: ""
    }

    private fun getTokenKey(uuid: String?): String {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid
    }
}
