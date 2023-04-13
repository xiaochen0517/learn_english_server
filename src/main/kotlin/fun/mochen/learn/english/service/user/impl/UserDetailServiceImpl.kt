package `fun`.mochen.learn.english.service.user.impl

import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.entity.User
import `fun`.mochen.learn.english.service.user.UserService
import `fun`.mochen.learn.english.system.exception.service.ServiceException
import `fun`.mochen.learn.english.system.exception.user.UserHasBeenDisabledException
import `fun`.mochen.learn.english.system.exception.user.UserNotExistsException
import `fun`.mochen.learn.english.system.service.PasswordService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserDetailServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var passwordService: PasswordService

    companion object {
        val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(UserDetailServiceImpl::class.java)
    }

    @Autowired
    private lateinit var userService: UserService

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.selectUserByUsername(username ?: "")
        if (user == null) {
            log.info("登录用户：{} 不存在.", username)
            throw UserNotExistsException()
        }
        if (user.status != 0) {
            log.info("登录用户：{} 已被禁用.", username)
            throw UserHasBeenDisabledException()
        }
        passwordService.validate(user)
        return createLoginUser(user)
    }

    fun createLoginUser(user: User): UserDetails {
        return LoginUser(user.id ?: 0, user)
    }
}
