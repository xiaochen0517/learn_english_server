package `fun`.mochen.learn.english.service.user

import `fun`.mochen.learn.english.entity.user.User

interface UserService {

    fun selectUserByUsername(username: String): User?

}
