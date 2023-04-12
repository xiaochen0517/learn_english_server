package `fun`.mochen.learn.english.controller.dict

import com.alibaba.fastjson.JSON
import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.service.dict.DictInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dict")
class DictInfoController {

    @Autowired
    private lateinit var dictInfoService: DictInfoService

    @GetMapping("/query/word")
    fun queryWordInfo(word: String): AjaxResult {

        return AjaxResult.success("查询成功", JSON.parse(dictInfoService.queryWordInfo(word)))
    }

}
