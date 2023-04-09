package `fun`.mochen.learn.english.controller

import `fun`.mochen.learn.english.entity.TestTable
import `fun`.mochen.learn.english.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    private lateinit var testService: TestService

    @RequestMapping("/get")
    fun getTest(): List<TestTable> {
        return testService.getTest()
    }
}
