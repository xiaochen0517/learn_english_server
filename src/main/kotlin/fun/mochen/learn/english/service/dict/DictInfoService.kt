package `fun`.mochen.learn.english.service.dict

import `fun`.mochen.learn.english.core.domain.dict.WordInfo
import javax.servlet.http.HttpServletRequest

interface DictInfoService {

    fun queryWordInfo(word: String, request: HttpServletRequest): WordInfo

}
