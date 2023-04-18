package `fun`.mochen.learn.english.core.domain.word

import `fun`.mochen.learn.english.core.domain.BasePageRequest

class WordBookRequest: BasePageRequest() {

    @JvmField
    var name: String? = null

    @JvmField
    var wordBookIds: List<Long> = mutableListOf()

}
