package `fun`.mochen.learn.english.service.dict

import `fun`.mochen.learn.english.core.domain.dict.WordInfo

interface DictInfoService {

    fun queryWordInfo(word: String): WordInfo

}
