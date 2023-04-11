package `fun`.mochen.learn.english.system.service

import cn.hutool.core.util.StrUtil
import `fun`.mochen.learn.english.constant.UserConstants
import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.service.user.UserService
import `fun`.mochen.learn.english.system.exception.user.UserNotExistsException
import `fun`.mochen.learn.english.system.exception.user.UserPasswordNotMatchException
import `fun`.mochen.learn.english.system.redis.RedisCache
import `fun`.mochen.learn.english.system.security.context.AuthenticationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class LoginService {

    @Autowired
    private lateinit var tokenService: TokenService

    @Resource
    private lateinit var authenticationManager: AuthenticationManager

    fun login(username: String, password: String, code: String?, uuid: String?): String {
        // 验证码校验
        validateCaptcha(username, code, uuid)
        // 登录前置校验
        loginPreCheck(username, password)
        // 用户验证
        val authentication: Authentication? = try {
            val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
            AuthenticationContextHolder.setContext(authenticationToken)
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authenticationManager.authenticate(authenticationToken)
        } catch (exception: Exception) {
            if (exception is BadCredentialsException) {
                throw UserPasswordNotMatchException()
            } else {
                throw exception
            }
        } finally {
            AuthenticationContextHolder.clearContext()
        }
        val loginUser: LoginUser = authentication!!.principal as LoginUser
        recordLoginInfo(loginUser.userId)
        // 生成token
        return tokenService.createToken(loginUser)
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    fun validateCaptcha(username: String, code: String?, uuid: String?) {
//        val captchaEnabled: Boolean = configService.selectCaptchaEnabled()
//        if (captchaEnabled) {
//            val verifyKey: String = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "")
//            val captcha: String = redisCache.getCacheObject(verifyKey)
//            redisCache.deleteObject(verifyKey)
//            if (captcha == null) {
//                AsyncManager.me().execute(
//                    AsyncFactory.recordLogininfor(
//                        username,
//                        Constants.LOGIN_FAIL,
//                        MessageUtils.message("user.jcaptcha.expire")
//                    )
//                )
//                throw CaptchaExpireException()
//            }
//            if (!code.equals(captcha, ignoreCase = true)) {
//                AsyncManager.me().execute(
//                    AsyncFactory.recordLogininfor(
//                        username,
//                        Constants.LOGIN_FAIL,
//                        MessageUtils.message("user.jcaptcha.error")
//                    )
//                )
//                throw CaptchaException()
//            }
//        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    fun loginPreCheck(username: String, password: String) {
        // 用户名或密码为空 错误
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
//            AsyncManager.me().execute(
//                AsyncFactory.recordLoginInfo(
//                    username,
//                    BaseConstants.LOGIN_FAIL,
//                    MessageUtils.message("not.null")
//                )
//            )
            throw UserNotExistsException()
        }
        // 密码如果不在指定范围内 错误
        if (password.length < UserConstants.PASSWORD_MIN_LENGTH
            || password.length > UserConstants.PASSWORD_MAX_LENGTH
        ) {
//            AsyncManager.me().execute(
//                AsyncFactory.recordLoginInfo(
//                    username,
//                    BaseConstants.LOGIN_FAIL,
//                    MessageUtils.message("user.password.not.match")
//                )
//            )
            throw UserPasswordNotMatchException()
        }
        // 用户名不在指定范围内 错误
        if (username.length < UserConstants.USERNAME_MIN_LENGTH
            || username.length > UserConstants.USERNAME_MAX_LENGTH
        ) {
//            AsyncManager.me().execute(
//                AsyncFactory.recordLoginInfo(
//                    username,
//                    BaseConstants.LOGIN_FAIL,
//                    MessageUtils.message("user.password.not.match")
//                )
//            )
            throw UserPasswordNotMatchException()
        }
        // IP黑名单校验
//        val blackStr: String = configService.selectConfigByKey("sys.login.blackIPList")
//        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
//            AsyncManager.me().execute(
//                AsyncFactory.recordLoginInfo(
//                    username,
//                    Constants.LOGIN_FAIL,
//                    MessageUtils.message("login.blocked")
//                )
//            )
//            throw BlackListException()
//        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    fun recordLoginInfo(userId: Long?) {
        // TODO 记录登录信息
    }

}
