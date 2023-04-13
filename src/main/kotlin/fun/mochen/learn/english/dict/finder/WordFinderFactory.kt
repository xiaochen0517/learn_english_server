package `fun`.mochen.learn.english.dict.finder

import `fun`.mochen.learn.english.core.domain.dict.WordFinderType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class WordFinderFactory {

    @Autowired
    private lateinit var youdaoWordFinder: YoudaoWordFinder

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    fun getWordFinder(wordFinderType: WordFinderType): WordFinder {
        return when (wordFinderType) {
            WordFinderType.YOUDAO -> applicationContext.getBean(YoudaoWordFinder::class.java)
            else -> throw IllegalArgumentException("不支持的查询类型")
        }
    }

}
