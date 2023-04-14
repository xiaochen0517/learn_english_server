package `fun`.mochen.learn.english.system.exception.service.openai

import `fun`.mochen.learn.english.system.exception.service.ServiceException

class OpenAiResponseException(override var message: String) : ServiceException(452, message) {
}
