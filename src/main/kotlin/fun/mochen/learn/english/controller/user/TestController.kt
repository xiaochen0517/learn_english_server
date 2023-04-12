package `fun`.mochen.learn.english.controller.user

import `fun`.mochen.learn.english.core.domain.AjaxResult
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
    fun getTest(): AjaxResult {
        return AjaxResult.success(testService.getTest())
    }
}
