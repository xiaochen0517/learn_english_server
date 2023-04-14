package `fun`.mochen.learn.english.core.domain.dict

data class WordExampleSentence(
    var id: Long? = null,
    // 例句
    val en: String,
    // 例句翻译
    val zh: String,
)
