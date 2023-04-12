package `fun`.mochen.learn.english.system.handle

import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.system.exception.BaseException
import `fun`.mochen.learn.english.system.exception.service.ServiceException
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
        log.error("请求地址'${requestURI}',不支持'${exception.method}'请求")
        return AjaxResult.error(exception.message!!)
    }

    /**
     * 业务异常
     * @param exception 业务异常
     * @param request 请求
     */
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException, request: HttpServletRequest?): AjaxResult? {
        log.error(exception.message, exception)
        val code: Int = exception.code ?: 500
        return AjaxResult.error(code, exception.message ?: "业务异常")
    }
}
