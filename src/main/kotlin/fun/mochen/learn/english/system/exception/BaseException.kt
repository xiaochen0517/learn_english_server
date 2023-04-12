package `fun`.mochen.learn.english.system.exception

import cn.hutool.core.util.StrUtil
import `fun`.mochen.learn.english.system.utils.MessageUtils

open class BaseException(
    var code: Int,
    override var message: String,
) : RuntimeException() {
}
