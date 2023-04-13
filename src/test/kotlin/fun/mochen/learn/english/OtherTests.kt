package `fun`.mochen.learn.english

import com.alibaba.fastjson.JSON
import org.junit.jupiter.api.Test

class OtherTests {

    @Test
    fun testJsonParse(){
        val list: List<String> = JSON.parseArray(null, String::class.java) ?: listOf()
        println(list)
    }
}
