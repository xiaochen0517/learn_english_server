package `fun`.mochen.learn.english.service.user.impl

import `fun`.mochen.learn.english.entity.User
import `fun`.mochen.learn.english.mapper.UserMapper
import `fun`.mochen.learn.english.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun selectUserByUsername(username: String): User {
        return userMapper.selectOneByUsername(username)
    }

}
