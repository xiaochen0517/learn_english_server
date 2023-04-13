package `fun`.mochen.learn.english.core.domain.dict

data class WordInfo(
    val id: Long? = null,
    // 查询内容
    val word: String,
    // 音标
    val phonetic: WordPhonetic,
    // 音频
    val voice: WordVoice,
    // 解释
    val explains: List<String>,
    // 派生词
    val derivedForms: List<WordDerivedForms?> = emptyList(),
)
