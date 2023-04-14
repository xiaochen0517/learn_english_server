package `fun`.mochen.learn.english.service.openai

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.JsonPathException
import `fun`.mochen.learn.english.core.domain.dict.WordExampleSentence
import `fun`.mochen.learn.english.core.domain.openai.OpenAiMessage
import `fun`.mochen.learn.english.core.domain.openai.OpenAiReqBody
import `fun`.mochen.learn.english.system.exception.service.openai.OpenAiResponseException
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

    fun queryWordExampleSentence(word: String): MutableList<WordExampleSentence> {
        val headerMap = mapOf(CONTENT_TYPE to APPLICATION_JSON, AUTHOR to API_KEY_PREFIX + apiKey)
        val messages = listOf(OpenAiMessage("system", prompt), OpenAiMessage("user", word))
        val requestData = OpenAiReqBody(model, messages)
        val requestJson = JSON.toJSONString(requestData)
        val response = HttpUtils.postJsonWithHeader(apiUrl, requestJson, headerMap)
        // 解析返回结果
        val jsonPathParse = JsonPath.parse(response)
        try {
            // 尝试获取错误对象，如果获取到则说明执行失败
            jsonPathParse.read<JSONObject>("$.error")
            // 获取错误信息
            val errorMsg = jsonPathParse.read<String>("$.error.message")
            throw OpenAiResponseException("OpenAi response error: $errorMsg")
        } catch (ignored: JsonPathException) {
            // 没有获取到错误对象，说明执行成功
        }
        // 获取返回结果
        try {
            val resultJson = jsonPathParse.read<String>("$.choices[0].message.content")
            val examSentList = JSON.parseArray(resultJson, WordExampleSentence::class.java)
            if (examSentList.isEmpty()) {
                throw OpenAiResponseException("Example sentence is empty")
            }
            return examSentList
        } catch (exception: Exception) {
            // 获取返回json失败，将结果和异常信息打印到日志中
            log.error("OpenAi response info: \n$response")
            log.error("exception info", exception)
            throw OpenAiResponseException("OpenAi response error: ${exception.message}")
        }
    }

}
