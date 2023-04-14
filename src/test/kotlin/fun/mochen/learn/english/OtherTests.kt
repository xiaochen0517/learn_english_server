package `fun`.mochen.learn.english

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.JsonPathException
import org.junit.jupiter.api.Test

class OtherTests {

    @Test
    fun testJsonParse() {
        val list: List<String> = JSON.parseArray(null, String::class.java) ?: listOf()
        println(list)
    }

    @Test
    fun testJsonPathException() {
        val jsonStr = "{\"data\":{\"id\":123,\"name\":\"mochen\"}}"
        val parse = JsonPath.parse(jsonStr)
        try {
            parse.read<JSONObject>("$.data")
            println("success")
        } catch (e: JsonPathException) {
            println("error")
        }
    }
}
