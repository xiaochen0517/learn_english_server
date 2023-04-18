package `fun`.mochen.learn.english.controller.word

import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.core.domain.word.WordBookRequest
import `fun`.mochen.learn.english.service.word.WordBookInfoService
import `fun`.mochen.learn.english.service.word.WordBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/word/book")
class WordBookController {

    @Autowired
    private lateinit var wordBookService: WordBookService

    @Autowired
    private lateinit var wordBookInfoService: WordBookInfoService

    @GetMapping("/list")
    fun listWordBookByUser(@RequestBody wordBookRequest: WordBookRequest, request: HttpServletRequest): AjaxResult {
        return AjaxResult.success(wordBookService.listWordBookByUser(wordBookRequest, request))
    }

    @PostMapping("/create")
    fun createWordBook(@RequestBody wordBookRequest: WordBookRequest, request: HttpServletRequest): AjaxResult {
        return AjaxResult.success(wordBookService.createWordBook(wordBookRequest, request))
    }

    @DeleteMapping("/delete")
    fun deleteWordBook(@RequestBody wordBookRequest: WordBookRequest): AjaxResult {
        wordBookInfoService.removeBatchByIds(wordBookRequest.wordBookIds)
        return AjaxResult.success("删除成功")
    }
}
