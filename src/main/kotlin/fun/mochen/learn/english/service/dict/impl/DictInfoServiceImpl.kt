package `fun`.mochen.learn.english.service.dict.impl

import `fun`.mochen.learn.english.core.domain.dict.WordFinderType
import `fun`.mochen.learn.english.core.domain.dict.WordInfo
import `fun`.mochen.learn.english.dict.finder.WordFinderFactory
import `fun`.mochen.learn.english.service.dict.DictInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DictInfoServiceImpl : DictInfoService {

    @Autowired
    private lateinit var wordFinderFactory: WordFinderFactory

    override fun queryWordInfo(word: String): WordInfo {
        return wordFinderFactory.getWordFinder(WordFinderType.YOUDAO).queryWord(word)
    }

}
