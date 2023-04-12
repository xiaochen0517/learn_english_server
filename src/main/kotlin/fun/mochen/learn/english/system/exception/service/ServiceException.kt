package `fun`.mochen.learn.english.system.exception.service

import `fun`.mochen.learn.english.system.exception.BaseException

open class ServiceException(code: Int, message: String) : BaseException(code, message)
