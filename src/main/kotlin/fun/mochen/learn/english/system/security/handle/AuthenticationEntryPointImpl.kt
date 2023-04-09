package `fun`.mochen.learn.english.system.security.handle

import com.alibaba.fastjson2.JSON
import `fun`.mochen.learn.english.constant.HttpStatus
import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.system.utils.ServletUtils
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationEntryPointImpl : AuthenticationEntryPoint, Serializable {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val code: Int = HttpStatus.UNAUTHORIZED
        val msg = "请求访问：${request?.requestURI}，认证失败，无法访问系统资源"
        ServletUtils.renderString(response!!, JSON.toJSONString(AjaxResult.error(code, msg)))
    }

}
