package `fun`.mochen.learn.english.controller.user

import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.service.openai.OpenAiService
import `fun`.mochen.learn.english.service.user.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    private lateinit var testService: TestService

    @Autowired
    private lateinit var openAiService: OpenAiService

    @RequestMapping("/get")
    fun getTest(): AjaxResult {
        return AjaxResult.success(testService.getTest())
    }

    @RequestMapping("/openai")
    fun openai(word: String): AjaxResult {
        return AjaxResult.success(openAiService.queryWordExampleSentence(word))
    }
}
