package `fun`.mochen.learn.english.system.utils

import cn.hutool.core.convert.Convert
import `fun`.mochen.learn.english.core.common.utils.StrUtil
import `fun`.mochen.learn.english.constant.BaseConstants
import org.apache.commons.lang3.StringUtils
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class ServletUtils {
    companion object {
        /**
         * 获取String参数
         */
        @JvmStatic
        fun getParameter(name: String): String {
            return getRequest().getParameter(name)
        }

        /**
         * 获取String参数
         */
        @JvmStatic
        fun getParameter(name: String, defaultValue: String): String {
            return Convert.toStr(getRequest().getParameter(name), defaultValue)
        }

        /**
         * 获取Integer参数
         */
        @JvmStatic
        fun getParameterToInt(name: String?): Int? {
            return Convert.toInt(getRequest().getParameter(name))
        }

        /**
         * 获取Integer参数
         */
        @JvmStatic
        fun getParameterToInt(name: String?, defaultValue: Int?): Int? {
            return Convert.toInt(getRequest().getParameter(name), defaultValue)
        }

        /**
         * 获取Boolean参数
         */
        @JvmStatic
        fun getParameterToBool(name: String?): Boolean? {
            return Convert.toBool(getRequest().getParameter(name))
        }

        /**
         * 获取Boolean参数
         */
        @JvmStatic
        fun getParameterToBool(name: String?, defaultValue: Boolean?): Boolean? {
            return Convert.toBool(getRequest().getParameter(name), defaultValue)
        }

        /**
         * 获得所有请求参数
         *
         * @param request 请求对象[ServletRequest]
         * @return Map
         */
        @JvmStatic
        fun getParams(request: ServletRequest): Map<String, Array<String>> {
            val map = request.parameterMap
            return Collections.unmodifiableMap(map)
        }

        /**
         * 获得所有请求参数
         *
         * @param request 请求对象[ServletRequest]
         * @return Map
         */
        @JvmStatic
        fun getParamMap(request: ServletRequest): Map<String, String>? {
            val params: MutableMap<String, String> = HashMap()
            for ((key, value) in getParams(request)) {
                params[key] = StringUtils.join(value, ",")
            }
            return params
        }

        /**
         * 获取request
         */
        @JvmStatic
        fun getRequest(): HttpServletRequest {
            return getRequestAttributes()!!.getRequest()
        }

        /**
         * 获取response
         */
        @JvmStatic
        fun getResponse(): HttpServletResponse? {
            return getRequestAttributes()?.getResponse()
        }

        /**
         * 获取session
         */
        @JvmStatic
        fun getSession(): HttpSession? {
            return getRequest().session
        }

        @JvmStatic
        fun getRequestAttributes(): ServletRequestAttributes? {
            val attributes = RequestContextHolder.getRequestAttributes()
            return attributes as ServletRequestAttributes?
        }

        /**
         * 将字符串渲染到客户端
         *
         * @param response 渲染对象
         * @param string 待渲染的字符串
         */
        @JvmStatic
        fun renderString(response: HttpServletResponse, string: String?) {
            try {
                response.status = 200
                response.contentType = "application/json"
                response.characterEncoding = "utf-8"
                response.writer.print(string)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         * 是否是Ajax异步请求
         *
         * @param request
         */
        @JvmStatic
        fun isAjaxRequest(request: HttpServletRequest): Boolean {
            val accept = request.getHeader("accept")
            if (accept != null && accept.contains("application/json")) {
                return true
            }
            val xRequestedWith = request.getHeader("X-Requested-With")
            if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
                return true
            }
            val uri = request.requestURI
            if (StrUtil.inStringIgnoreCase(uri, ".json", ".xml")) {
                return true
            }
            val ajax = request.getParameter("__ajax")
            return StrUtil.inStringIgnoreCase(ajax, "json", "xml")
        }

        /**
         * 内容编码
         *
         * @param str 内容
         * @return 编码后的内容
         */
        @JvmStatic
        fun urlEncode(str: String?): String? {
            return try {
                URLEncoder.encode(str, BaseConstants.UTF8)
            } catch (e: UnsupportedEncodingException) {
                StringUtils.EMPTY
            }
        }

        /**
         * 内容解码
         *
         * @param str 内容
         * @return 解码后的内容
         */
        @JvmStatic
        fun urlDecode(str: String?): String? {
            return try {
                URLDecoder.decode(str, BaseConstants.UTF8)
            } catch (e: UnsupportedEncodingException) {
                StringUtils.EMPTY
            }
        }
    }
}
