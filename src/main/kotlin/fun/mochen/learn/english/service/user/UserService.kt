package `fun`.mochen.learn.english.service.user

import com.baomidou.mybatisplus.extension.service.IService
import `fun`.mochen.learn.english.entity.user.User

interface UserService : IService<User> {

    fun selectUserByUsername(username: String): User?

}
