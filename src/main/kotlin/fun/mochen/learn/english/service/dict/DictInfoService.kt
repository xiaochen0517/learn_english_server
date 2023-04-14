package `fun`.mochen.learn.english.service.dict

import com.baomidou.mybatisplus.extension.service.IService
import `fun`.mochen.learn.english.core.domain.dict.WordExampleSentence
import `fun`.mochen.learn.english.core.domain.dict.WordInfo
import `fun`.mochen.learn.english.core.domain.dict.WordQueryBody
import `fun`.mochen.learn.english.entity.dict.DictWordInfo

interface DictInfoService : IService<DictWordInfo> {

    fun queryWordInfo(wordQueryBody: WordQueryBody): WordInfo

    fun queryWordExampleSentence(wordQueryBody: WordQueryBody): List<WordExampleSentence>

}
