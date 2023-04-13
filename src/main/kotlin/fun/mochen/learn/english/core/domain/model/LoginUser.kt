package `fun`.mochen.learn.english.core.domain.model

import com.alibaba.fastjson2.annotation.JSONField
import `fun`.mochen.learn.english.entity.user.User
import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Data
class LoginUser(

    /**
     * 用户ID
     */
    var userId: Long?,

    /**
     * 用户唯一标识
     */
    var token: String = "",

    /**
     * 登录时间
     */
    var loginTime: Long = 0,

    /**
     * 过期时间
     */
    var expireTime: Long = 0,

    /**
     * 登录IP地址
     */
    var ipaddr: String?,

    /**
     * 登录地点
     */
    var loginLocation: String?,

    /**
     * 浏览器类型
     */
    var browser: String?,

    /**
     * 操作系统
     */
    var os: String?,

    /**
     * 权限列表
     */
    var permissions: Set<String>?,

    /**
     * 用户信息
     */
    var user: User?,
) : UserDetails {


    constructor(user: User?, permissions: Set<String>?)
            : this(null, "", 0, 0,
        null, null, null, null, permissions, user) {
    }

    constructor(userId: Long, user: User?): this(userId, "", 0, 0,
        null, null, null, null, null, user)

    constructor(userId: Long?, user: User?, permissions: Set<String>?)
            : this(userId, "", 0, 0,
        null, null, null, null, permissions, user)

    @JSONField(serialize = false)
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    @JSONField(serialize = false)
    override fun getPassword(): String? {
        return this.user!!.password
    }

    override fun getUsername(): String? {
        return this.user!!.username
    }

    @JSONField(serialize = false)
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JSONField(serialize = false)
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JSONField(serialize = false)
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JSONField(serialize = false)
    override fun isEnabled(): Boolean {
        return true
    }
}
