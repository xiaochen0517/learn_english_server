package `fun`.mochen.learn.english.controller.dict

import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.core.domain.dict.WordQueryBody
import `fun`.mochen.learn.english.service.dict.DictInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dict")
class DictInfoController {

    @Autowired
    private lateinit var dictInfoService: DictInfoService

    @PutMapping("/query/word")
    fun queryWordInfo(@RequestBody word: WordQueryBody): AjaxResult {
        return AjaxResult.success("查询成功", dictInfoService.queryWordInfo(word))
    }

    @PutMapping("/query/word/example/sentence")
    fun queryWordExampleSentence(@RequestBody word: WordQueryBody): AjaxResult {
        return AjaxResult.success("查询成功", dictInfoService.queryWordExampleSentence(word))
    }

}
