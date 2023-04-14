package `fun`.mochen.learn.english.system.interceptor

import com.alibaba.fastjson2.JSON
import `fun`.mochen.learn.english.core.common.annotation.RepeatSubmit
import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.system.utils.ServletUtils
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
abstract class RepeatSubmitInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return if (handler is HandlerMethod) {
            val method = handler.method
            val annotation: RepeatSubmit? = method.getAnnotation(RepeatSubmit::class.java)
            if (isRepeatSubmit(request, annotation)) {
                val ajaxResult: AjaxResult = AjaxResult.error(annotation?.message!!)
                ServletUtils.renderString(response, JSON.toJSONString(ajaxResult))
                return false
            }
            true
        } else {
            true
        }
    }

    /**
     * 验证是否重复提交由子类实现具体防重复提交的规则
     *
     * @param request
     * @return
     * @throws Exception
     */
    abstract fun isRepeatSubmit(request: HttpServletRequest?, annotation: RepeatSubmit?): Boolean

}
