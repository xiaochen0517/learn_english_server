package `fun`.mochen.learn.english.system.service

import `fun`.mochen.learn.english.constant.CacheConstants
import `fun`.mochen.learn.english.entity.User
import `fun`.mochen.learn.english.system.exception.user.UserPasswordNotMatchException
import `fun`.mochen.learn.english.system.exception.user.UserPasswordRetryLimitExceedException
import `fun`.mochen.learn.english.system.redis.RedisCache
import `fun`.mochen.learn.english.system.security.context.AuthenticationContextHolder
import `fun`.mochen.learn.english.system.utils.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class PasswordService {

    @Autowired
    private lateinit var redisCache: RedisCache

    @Value(value = "\${user.password.open}")
    private val open = false

    @Value(value = "\${user.password.max-retry-count}")
    private val maxRetryCount = 0

    @Value(value = "\${user.password.lock-time}")
    private val lockTime = 0

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private fun getCacheKey(username: String): String {
        return CacheConstants.PWD_ERR_CNT_KEY + username
    }

    fun validate(user: User) {
        val usernamePasswordAuthenticationToken: Authentication = AuthenticationContextHolder.getContext()
        val username = usernamePasswordAuthenticationToken.name
        val password = usernamePasswordAuthenticationToken.credentials.toString()
        var retryCount = 0
        try {
            retryCount = redisCache.getCacheObject(getCacheKey(username))
        } catch (exception: Exception) {
            // ignore
        }
        if (open && retryCount >= Integer.valueOf(maxRetryCount).toInt()) {
            // 记录用户登录失败次数过多日志
            throw UserPasswordRetryLimitExceedException(maxRetryCount, lockTime)
        }
        if (!matches(user, password)) {
            retryCount = retryCount + 1
            // 记录用户密码错误日志
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES)
            throw UserPasswordNotMatchException()
        } else {
            clearLoginRecordCache(username)
        }
    }

    fun matches(user: User, rawPassword: String?): Boolean {
        return SecurityUtils.matchesPassword(rawPassword, user.password)
    }

    fun clearLoginRecordCache(loginName: String) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName))
        }
    }
}
