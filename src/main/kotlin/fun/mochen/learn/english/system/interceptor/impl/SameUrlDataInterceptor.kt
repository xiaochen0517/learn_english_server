package `fun`.mochen.learn.english.system.interceptor.impl

import `fun`.mochen.learn.english.core.common.annotation.RepeatSubmit
import `fun`.mochen.learn.english.system.interceptor.RepeatSubmitInterceptor
import `fun`.mochen.learn.english.system.redis.RedisCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class SameUrlDataInterceptor : RepeatSubmitInterceptor() {

    val REPEAT_PARAMS = "repeatParams"

    val REPEAT_TIME = "repeatTime"

    // 令牌自定义标识
    @Value("\${token.header}")
    private lateinit var header: String

    @Autowired
    private lateinit var redisCache: RedisCache

    override fun isRepeatSubmit(request: HttpServletRequest?, annotation: RepeatSubmit?): Boolean {
        return false
    }
}
