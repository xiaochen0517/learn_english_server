package `fun`.mochen.learn.english.service.user.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import `fun`.mochen.learn.english.entity.user.User
import `fun`.mochen.learn.english.mapper.user.UserMapper
import `fun`.mochen.learn.english.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : ServiceImpl<UserMapper, User>(), UserService {

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun selectUserByUsername(username: String): User {
        return userMapper.selectOneByUsername(username)
    }

}
