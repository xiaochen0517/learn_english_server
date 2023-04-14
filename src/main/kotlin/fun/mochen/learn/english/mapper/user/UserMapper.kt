package `fun`.mochen.learn.english.mapper.user

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.user.User
import org.springframework.stereotype.Repository

@Repository
interface UserMapper : BaseMapper<User> {
    fun selectOneByUsername(username: String): User

}
