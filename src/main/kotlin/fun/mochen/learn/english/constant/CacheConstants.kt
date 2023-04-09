package `fun`.mochen.learn.english.constant

class CacheConstants {

    companion object {
        /**
         * 登录用户 redis key
         */
        @JvmField
        val LOGIN_TOKEN_KEY = "login_tokens:"

        /**
         * 验证码 redis key
         */
        @JvmField
        val CAPTCHA_CODE_KEY = "captcha_codes:"

        /**
         * 参数管理 cache key
         */
        @JvmField
        val SYS_CONFIG_KEY = "sys_config:"

        /**
         * 字典管理 cache key
         */
        @JvmField
        val SYS_DICT_KEY = "sys_dict:"

        /**
         * 防重提交 redis key
         */
        @JvmField
        val REPEAT_SUBMIT_KEY = "repeat_submit:"

        /**
         * 限流 redis key
         */
        @JvmField
        val RATE_LIMIT_KEY = "rate_limit:"

        /**
         * 登录账户密码错误次数 redis key
         */
        @JvmField
        val PWD_ERR_CNT_KEY = "pwd_err_cnt:"
    }

}
