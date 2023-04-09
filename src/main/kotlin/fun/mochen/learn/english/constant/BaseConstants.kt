package `fun`.mochen.learn.english.constant

class BaseConstants {

    companion object {

        /**
         * UTF-8 字符集
         */
        const val UTF8 = "UTF-8"

        /**
         * GBK 字符集
         */
        const val GBK = "GBK"

        /**
         * www主域
         */
        const val WWW = "www."

        /**
         * http请求
         */
        const val HTTP = "http://"

        /**
         * https请求
         */
        const val HTTPS = "https://"

        /**
         * 通用成功标识
         */
        const val SUCCESS = "0"

        /**
         * 通用失败标识
         */
        const val FAIL = "1"

        /**
         * 登录成功
         */
        const val LOGIN_SUCCESS = "Success"

        /**
         * 注销
         */
        const val LOGOUT = "Logout"

        /**
         * 注册
         */
        const val REGISTER = "Register"

        /**
         * 登录失败
         */
        const val LOGIN_FAIL = "Error"

        /**
         * 验证码有效期（分钟）
         */
        const val CAPTCHA_EXPIRATION = 2

        /**
         * 令牌
         */
        const val TOKEN = "token"

        /**
         * 令牌前缀
         */
        const val TOKEN_PREFIX = "Bearer "

        /**
         * 令牌前缀
         */
        const val LOGIN_USER_KEY = "login_user_key"

        /**
         * 用户ID
         */
        const val JWT_USERID = "userid"

        /**
         * 用户名称
         */
        val JWT_USERNAME: String = "sub"

        /**
         * 用户头像
         */
        const val JWT_AVATAR = "avatar"

        /**
         * 创建时间
         */
        const val JWT_CREATED = "created"

        /**
         * 用户权限
         */
        const val JWT_AUTHORITIES = "authorities"

        /**
         * 资源映射路径 前缀
         */
        const val RESOURCE_PREFIX = "/profile"

        /**
         * RMI 远程方法调用
         */
        const val LOOKUP_RMI = "rmi:"

        /**
         * LDAP 远程方法调用
         */
        const val LOOKUP_LDAP = "ldap:"

        /**
         * LDAPS 远程方法调用
         */
        const val LOOKUP_LDAPS = "ldaps:"

        /**
         * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
         */
        val JOB_WHITELIST_STR = arrayOf("com.ruoyi")

        /**
         * 定时任务违规的字符
         */
        val JOB_ERROR_STR = arrayOf(
            "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ruoyi.common.utils.file", "com.ruoyi.common.config"
        )
    }
}
