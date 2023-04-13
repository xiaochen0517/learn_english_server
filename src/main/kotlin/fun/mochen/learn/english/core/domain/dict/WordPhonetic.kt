package `fun`.mochen.learn.english.core.domain.dict

data class WordPhonetic(
    // 国际音标
    val defPhonetic: String? = null,
    // 美式音标
    val usPhonetic: String? = null,
    // 英式音标
    val ukPhonetic: String? = null,
)
