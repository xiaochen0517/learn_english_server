package `fun`.mochen.learn.english.core.domain.dict

data class WordVoice(
    // 查询内容音频地址
    val queryVoiceUrl: String,
    // 翻译内容音频地址
    val transVoiceUrl: String,
    // 美式发音音频地址
    val usVoiceUrl: String,
    // 英式发音音频地址
    val ukVoiceUrl: String,
)
