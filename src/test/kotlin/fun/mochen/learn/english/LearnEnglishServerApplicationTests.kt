package `fun`.mochen.learn.english

import com.alibaba.fastjson2.JSON
import `fun`.mochen.learn.english.LearnEnglishServerApplication
import `fun`.mochen.learn.english.mapper.TestTableMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [LearnEnglishServerApplication::class])
class LearnEnglishServerApplicationTests {

    @Autowired
    private lateinit var testTableMapper: TestTableMapper

    @Test
    fun contextLoads() {
        val selectList = testTableMapper.selectList(null)
        println(JSON.toJSONString(selectList))
    }

}
