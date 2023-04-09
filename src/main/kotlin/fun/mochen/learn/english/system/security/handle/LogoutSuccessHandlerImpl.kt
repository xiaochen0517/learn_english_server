package `fun`.mochen.learn.english.system.security.handle

import com.alibaba.fastjson2.JSON
import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.system.utils.ServletUtils
import `fun`.mochen.learn.english.web.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class LogoutSuccessHandlerImpl : LogoutSuccessHandler {

    @Autowired
    private lateinit var tokenService: TokenService

    override fun onLogoutSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val loginUser: LoginUser? = tokenService.getLoginUser(request!!)
//        val userName: String? = loginUser?.getUsername()
        // 删除用户缓存记录
        tokenService.delLoginUser(loginUser?.token!!)
        // 记录用户退出日志
//        AsyncManager.me().execute(AsyncFactory.recordLoginInfo(userName, BaseConstants.LOGOUT, "退出成功"))
        ServletUtils.renderString(response!!, JSON.toJSONString(AjaxResult.success("退出成功")))
    }
}
