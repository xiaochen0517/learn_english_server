package `fun`.mochen.learn.english.dict.openai

import com.alibaba.fastjson.JSON
import `fun`.mochen.learn.english.core.domain.openai.OpenAiMessage
import `fun`.mochen.learn.english.core.domain.openai.OpenAiReqBody
import `fun`.mochen.learn.english.system.utils.HttpUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenAiService {

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(OpenAiService::class.java)

        private const val CONTENT_TYPE = "Content-Type"

        private const val APPLICATION_JSON = "application/json"

        private const val AUTHOR = "Authorization"

        private const val API_KEY_PREFIX = "Bearer "
    }

    @Value("\${openai.prompt}")
    private lateinit var prompt: String

    @Value("\${openai.api-url}")
    private lateinit var apiUrl: String

    @Value("\${openai.api-key}")
    private lateinit var apiKey: String

    @Value("\${openai.model}")
    private lateinit var model: String

    fun queryWordExampleSentence(word: String): String {
        val headerMap = mapOf(CONTENT_TYPE to APPLICATION_JSON, AUTHOR to API_KEY_PREFIX + apiKey)
        val messages = listOf(OpenAiMessage("system", prompt), OpenAiMessage("user", word))
        val requestData = OpenAiReqBody(model, messages)
        val requestJson = JSON.toJSONString(requestData)
        val response = HttpUtils.postJsonWithHeader(apiUrl, requestJson, headerMap)
        // TODO 解析返回结果
        return response ?: "[\"none info\"]"
    }

}
