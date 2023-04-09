package `fun`.mochen.learn.english.system.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit


/**
 * spring redis 工具类
 *
 * @author ruoyi
 */
@Component
class RedisCache {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    fun <T> setCacheObject(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    fun setCacheObject(key: String, value: Any, timeout: Int, timeUnit: TimeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout.toLong(), timeUnit)
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    fun expire(key: String, timeout: Long): Boolean {
        return expire(key, timeout, TimeUnit.SECONDS)
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    fun expire(key: String, timeout: Long, unit: TimeUnit): Boolean {
        return redisTemplate.expire(key, timeout, unit)
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key)
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    fun <T> getCacheObject(key: String): T {
        val operation = redisTemplate.opsForValue()
        val obj = operation.get(key) ?: throw NullPointerException("redis key is null")
        return obj as T
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    fun deleteObject(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    fun deleteObject(collection: Collection<String>): Boolean {
        return redisTemplate.delete(collection) > 0
    }


    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    fun setCacheList(key: String, dataList: List<*>): Long {
        val count: Long? = redisTemplate.opsForList().rightPushAll(key, *dataList.toTypedArray())
        return count ?: 0
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    fun <T> getCacheList(key: String): List<T> {
        val list = redisTemplate.opsForList().range(key, 0, -1)
            ?: throw NullPointerException("redis key is null")
        return list as List<T>
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    fun keys(pattern: String): Collection<String> {
        return redisTemplate.keys(pattern)
    }
}
