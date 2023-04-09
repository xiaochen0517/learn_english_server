package `fun`.mochen.learn.english.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserMapper: BaseMapper<User> {
    fun selectOneByUsername(username: String): User
}
