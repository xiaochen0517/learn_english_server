package `fun`.mochen.learn.english.service.dict.impl

import cn.hutool.core.util.IdUtil
import cn.hutool.crypto.SecureUtil
import `fun`.mochen.learn.english.service.dict.DictInfoService
import `fun`.mochen.learn.english.system.exception.service.QueryNullException
import `fun`.mochen.learn.english.system.utils.HttpUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class DictInfoServiceImpl : DictInfoService {

    @Value("\${dict.youdao.app-id}")
    private lateinit var appId: String

    @Value("\${dict.youdao.app-key}")
    private lateinit var appKey: String

    override fun queryWordInfo(word: String): String {
        var input = ""
        if (word.length <= 20)
            input = word
        else
            input = word.substring(0, 10) + word.length + word.substring(word.length - 10, word.length)
        val salt = IdUtil.simpleUUID()
        val currentTime = getCurrentUTCTimestampInSeconds().toString()
        val sha256 = SecureUtil.sha256().digestHex(appId + input + salt + currentTime + appKey)
        var requestMap = mutableMapOf<String, String>()
        requestMap["q"] = word
        requestMap["from"] = "en"
        requestMap["to"] = "zh-CHS"
        requestMap["appKey"] = appId
        requestMap["salt"] = salt
        requestMap["sign"] = sha256
        requestMap["signType"] = "v3"
        requestMap["curtime"] = currentTime
        val response = HttpUtils.postForm("https://openapi.youdao.com/api", requestMap)
        return response ?: throw QueryNullException()
    }

    fun getCurrentUTCTimestampInSeconds(): Long {
        val now = Instant.now()
        val epoch = Instant.EPOCH
        val duration = Duration.between(epoch, now)
        return duration.seconds
    }
}
