package `fun`.mochen.learn.english.system.exception

import cn.hutool.core.util.StrUtil
import `fun`.mochen.learn.english.system.utils.MessageUtils

open class BaseException(
    var module: String?,
    var code: Int?,
    var args: Array<Any?>?,
    override var message: String? = null,
) : RuntimeException() {

    constructor(code: Int?, message: String?) : this(null, code, null, message)

}
