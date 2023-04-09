package `fun`.mochen.learn.english.core.domain

import com.alibaba.fastjson.annotation.JSONType
import `fun`.mochen.learn.english.constant.HttpStatus
import java.io.Serializable

//@JSONType(ignores = arrayOf("empty", "size", "values", "\$super$", "entries", "keys", "companion"))
data class AjaxResult(
    var code: Int = 0,
    var msg: String = "",
    var data: Any? = null
) : Serializable {

    companion object {
        @JvmField
        val CODE_TAG = "code"

        @JvmField
        val MSG_TAG = "msg"

        @JvmField
        val DATA_TAG = "data"

        /**
         * 返回成功消息
         *
         * @return 成功消息
         */
        @JvmStatic
        fun success(): AjaxResult {
            return success("操作成功")
        }

        /**
         * 返回成功数据
         *
         * @return 成功消息
         */
        @JvmStatic
        fun success(data: Any?): AjaxResult {
            return success("操作成功", data)
        }

        /**
         * 返回成功消息
         *
         * @param msg 返回内容
         * @return 成功消息
         */
        @JvmStatic
        fun success(msg: String?): AjaxResult {
            return success(msg, null)
        }

        /**
         * 返回成功消息
         *
         * @param msg 返回内容
         * @param data 数据对象
         * @return 成功消息
         */
        @JvmStatic
        fun success(msg: String?, data: Any?): AjaxResult {
            return AjaxResult(HttpStatus.SUCCESS, msg!!, data)
        }

        /**
         * 返回警告消息
         *
         * @param msg 返回内容
         * @return 警告消息
         */
        @JvmStatic
        fun warn(msg: String): AjaxResult {
            return warn(msg, null)
        }

        /**
         * 返回警告消息
         *
         * @param msg 返回内容
         * @param data 数据对象
         * @return 警告消息
         */
        @JvmStatic
        fun warn(msg: String, data: Any?): AjaxResult {
            return AjaxResult(HttpStatus.WARN, msg, data)
        }

        /**
         * 返回错误消息
         *
         * @return 错误消息
         */
        @JvmStatic
        fun error(): AjaxResult {
            return error("操作失败")
        }

        /**
         * 返回错误消息
         *
         * @param msg 返回内容
         * @return 错误消息
         */
        @JvmStatic
        fun error(msg: String): AjaxResult {
            return error(msg, null)
        }

        /**
         * 返回错误消息
         *
         * @param msg 返回内容
         * @param data 数据对象
         * @return 错误消息
         */
        @JvmStatic
        fun error(msg: String, data: Any?): AjaxResult {
            return AjaxResult(HttpStatus.ERROR, msg, data)
        }

        /**
         * 返回错误消息
         *
         * @param code 状态码
         * @param msg 返回内容
         * @return 错误消息
         */
        @JvmStatic
        fun error(code: Int, msg: String): AjaxResult {
            return AjaxResult(code, msg, null)
        }

    }

    constructor() : this(0, "操作成功", null)

    constructor(code: Int, msg: String) : this(code, msg, null)

    constructor(data: Any?) : this(0, "操作成功", data)
}
