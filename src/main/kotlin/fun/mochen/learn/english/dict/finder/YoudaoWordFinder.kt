package `fun`.mochen.learn.english.dict.finder

import cn.hutool.core.util.IdUtil
import cn.hutool.crypto.SecureUtil
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import `fun`.mochen.learn.english.core.domain.dict.*
import `fun`.mochen.learn.english.system.exception.service.query.QueryErrorException
import `fun`.mochen.learn.english.system.exception.service.query.QueryNullException
import `fun`.mochen.learn.english.system.utils.HttpUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

@Component
@Scope("prototype")
class YoudaoWordFinder : WordFinder {

    companion object {

        private val log = LoggerFactory.getLogger(YoudaoWordFinder::class.java)

        private const val WORD_KEY = "q"

        private const val FROM_KEY = "from"

        private const val TO_KEY = "to"

        private const val APP_KEY = "appKey"

        private const val SALT_KEY = "salt"

        private const val SIGN_KEY = "sign"

        private const val SIGN_TYPE_KEY = "signType"

        private const val CUR_TIME_KEY = "curtime"

        private const val FROM_VALUE = "en"

        private const val TO_VALUE = "zh-CHS"

        private const val SIGN_TYPE_VALUE = "v3"
    }

    @Value("\${dict.youdao.api-url}")
    private lateinit var apiUrl: String

    @Value("\${dict.youdao.app-id}")
    private lateinit var appId: String

    @Value("\${dict.youdao.app-key}")
    private lateinit var appKey: String

    override fun queryWord(word: String): WordInfo {
        val requestMap = buildRequestInfo(word)
        val response = HttpUtils.postForm(this.apiUrl, requestMap)
        val responseJson = response ?: throw QueryNullException()
        return parseResponse(responseJson)
    }

    private fun buildRequestInfo(word: String): Map<String, String> {
        val salt = IdUtil.simpleUUID()
        val currentTime = getCurrentUTCTimestampInSeconds().toString()
        val sha256 = buildSign(word, salt, currentTime)
        return mapOf(
            WORD_KEY to word,
            FROM_KEY to FROM_VALUE,
            TO_KEY to TO_VALUE,
            APP_KEY to appId,
            SALT_KEY to salt,
            SIGN_KEY to sha256,
            SIGN_TYPE_KEY to SIGN_TYPE_VALUE,
            CUR_TIME_KEY to currentTime
        )
    }

    private fun buildSign(word: String, salt: String, currentTime: String): String {
        val input = if (word.length <= 20) word else
            word.substring(0, 10) + word.length + word.substring(word.length - 10, word.length)
        return SecureUtil.sha256().digestHex(appId + input + salt + currentTime + appKey) ?: ""
    }

    private fun getCurrentUTCTimestampInSeconds(): Long {
        val now = Instant.now()
        val epoch = Instant.EPOCH
        val duration = Duration.between(epoch, now)
        return duration.seconds
    }

    private fun parseResponse(response: String): WordInfo {
        // 解析返回结果
        val jsonPathParse = JsonPath.parse(response)
        // 检查返回内容是否正确
        checkResponseData(jsonPathParse)
        return WordInfo(
            // 获取查询内容
            word = jsonPathParse.read("$.query"),
            // 获取音标
            phonetic = getWordPhonetic(jsonPathParse),
            // 获取发音地址
            voice = getWordVoice(jsonPathParse),
            // 获取解释内容
            explains = jsonPathParse.read("$.basic.explains"),
            // 获取派生词
            derivedForms = getWordDerivedForms(jsonPathParse)
        )
    }

    private fun checkResponseData(jsonPathParse: DocumentContext) {
        // 判断是否有错误
        val errorCode = jsonPathParse.read<String>("$.errorCode")
        if (errorCode != "0") throw QueryErrorException("错误码: $errorCode，请查看有道词典API文档")
        // 检查当前查询是否为单词
        val isWord = jsonPathParse.read<Boolean>("$.isWord") ?: false
        if (isWord.not()) throw QueryErrorException("当前查询内容不是单词")
    }

    private fun getWordPhonetic(jsonPathParse: DocumentContext): WordPhonetic {
        val defPhonetic = jsonPathParse.read<String>("$.basic.phonetic")
        val usPhonetic = jsonPathParse.read<String>("$.basic.us-phonetic")
        val ukPhonetic = jsonPathParse.read<String>("$.basic.uk-phonetic")
        return WordPhonetic(defPhonetic, usPhonetic, ukPhonetic)
    }

    private fun getWordVoice(jsonPathParse: DocumentContext): WordVoice {
        val queryVoiceUrl = jsonPathParse.read<String>("$.speakUrl")
        val transVoiceUrl = jsonPathParse.read<String>("$.tSpeakUrl")
        val usVoiceUrl = jsonPathParse.read<String>("$.basic.us-speech")
        val ukVoiceUrl = jsonPathParse.read<String>("$.basic.uk-speech")
        return WordVoice(queryVoiceUrl, transVoiceUrl, usVoiceUrl, ukVoiceUrl)
    }

    private fun getWordDerivedForms(jsonPathParse: DocumentContext): List<WordDerivedForms> {
        try {
            val derivedFormsList = jsonPathParse.read<List<Map<String, String>>>("$.basic.wfs..wf")
            return derivedFormsList.map { WordDerivedForms(it["name"] ?: "", it["value"] ?: "") }
        }catch (e: Exception){
            log.error("解析派生词失败", e)
            return emptyList()
        }
    }
}
