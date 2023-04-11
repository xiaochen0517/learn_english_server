package `fun`.mochen.learn.english.controller

import `fun`.mochen.learn.english.core.domain.AjaxResult
import `fun`.mochen.learn.english.core.domain.model.LoginBody
import `fun`.mochen.learn.english.system.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @Autowired
    private lateinit var loginService: LoginService

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    fun login(@RequestBody loginBody: LoginBody): AjaxResult {
        val ajax: AjaxResult = AjaxResult.success()
        // 生成令牌
        val token: String = loginService.login(loginBody.username, loginBody.password, loginBody.code, loginBody.uuid)
        ajax.data = token
        return ajax
    }
}
