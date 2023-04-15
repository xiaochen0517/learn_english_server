package `fun`.mochen.learn.english.core.domain.dict

data class WordExampleSentence(
    var id: Long? = null,
    // 例句
    var en: String,
    // 例句翻译
    var zh: String,
) {
    constructor() : this(null, "", "")
}
