package `fun`.mochen.learn.english.service.user

import `fun`.mochen.learn.english.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService {

    fun selectUserByUsername(username: String): User?

}
