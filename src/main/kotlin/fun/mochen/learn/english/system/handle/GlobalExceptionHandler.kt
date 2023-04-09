package `fun`.mochen.learn.english.system.handle

import cn.hutool.core.util.ObjectUtil
import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.system.exception.BaseException
import `fun`.mochen.learn.english.system.exception.ServiceException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupported(
        exception: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest
    ): AjaxResult {
        val requestURI = request.requestURI
        log.error("请求地址'{}',不支持'{}'请求", requestURI, exception.method)
        return AjaxResult.error(exception.message!!)
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException, request: HttpServletRequest?): AjaxResult? {
        log.error(exception.message, exception)
        val code: Int = exception.code ?: 500
        return AjaxResult.error(code, exception.message ?: "业务异常")
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException, request: HttpServletRequest?): AjaxResult? {
        log.error(exception.message, exception)
        val code: Int = exception.code ?: 500
        return AjaxResult.error(code, exception.message ?: "业务异常")
    }
}
