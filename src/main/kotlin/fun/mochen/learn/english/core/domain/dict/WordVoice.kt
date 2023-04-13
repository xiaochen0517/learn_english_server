package `fun`.mochen.learn.english.core.domain.dict

data class WordVoice(
    // 查询内容音频地址
    val queryVoiceUrl: String? = null,
    // 翻译内容音频地址
    val transVoiceUrl: String? = null,
    // 美式发音音频地址
    val usVoiceUrl: String? = null,
    // 英式发音音频地址
    val ukVoiceUrl: String? = null,
)
