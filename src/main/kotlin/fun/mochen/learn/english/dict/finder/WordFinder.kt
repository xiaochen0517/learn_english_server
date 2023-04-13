package `fun`.mochen.learn.english.dict.finder

import `fun`.mochen.learn.english.core.domain.dict.WordInfo

interface WordFinder {

    fun queryWord(word: String): WordInfo

}
