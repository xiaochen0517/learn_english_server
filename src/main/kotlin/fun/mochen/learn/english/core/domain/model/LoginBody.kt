package `fun`.mochen.learn.english.core.domain.model

import lombok.Data

@Data
class LoginBody(

    /**
     * 用户名
     */
    var username: String,

    /**
     * 用户密码
     */
    val password: String,

    /**
     * 验证码
     */
    val code: String? = null,

    /**
     * 唯一标识
     */
    val uuid: String? = null
) {
}
