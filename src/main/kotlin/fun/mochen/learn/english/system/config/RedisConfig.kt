package `fun`.mochen.learn.english.system.config

import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableCaching
class RedisConfig : CachingConfigurerSupport() {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory!!)
        val serializer = FastJson2JsonRedisSerializer(Any::class.java)

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = serializer

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = serializer
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun limitScript(): DefaultRedisScript<Long> {
        val redisScript = DefaultRedisScript<Long>()
        redisScript.setScriptText(limitScriptText())
        redisScript.resultType = Long::class.java
        return redisScript
    }

    /**
     * 限流脚本
     */
    private fun limitScriptText(): String {
        return """local key = KEYS[1]
            |local count = tonumber(ARGV[1])
            |local time = tonumber(ARGV[2])
            |local current = redis.call('get', key);
            |if current and tonumber(current) > count then
            |    return tonumber(current);
            |end
            |current = redis.call('incr', key)
            |if tonumber(current) == 1 then
            |    redis.call('expire', key, time)
            |end
            |return tonumber(current);""".trimMargin("|")
    }
}
