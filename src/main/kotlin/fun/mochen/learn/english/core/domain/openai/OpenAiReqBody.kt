package `fun`.mochen.learn.english.core.domain.openai

data class OpenAiReqBody(
    val model: String,
    var messages: List<OpenAiMessage>,
)
