package `fun`.mochen.learn.english.system.utils

import `fun`.mochen.learn.english.constant.HttpStatus
import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.system.exception.ServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SecurityUtils {

    companion object {

        private val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(SecurityUtils::class.java)

        /**
         * 用户ID
         */
        @JvmStatic
        fun getUserId(): Long? {
            return try {
                getLoginUser().userId
            } catch (e: Exception) {
                throw ServiceException("获取用户ID异常", HttpStatus.UNAUTHORIZED)
            }
        }

        /**
         * 获取用户账户
         */
        @JvmStatic
        fun getUsername(): String? {
            return try {
                getLoginUser().getUsername()
            } catch (e: Exception) {
                throw ServiceException("获取用户账户异常", HttpStatus.UNAUTHORIZED)
            }
        }

        /**
         * 获取用户
         */
        @JvmStatic
        fun getLoginUser(): LoginUser {
            return try {
                getAuthentication().principal as LoginUser
            } catch (e: Exception) {
                throw ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED)
            }
        }

        /**
         * 获取Authentication
         */
        @JvmStatic
        fun getAuthentication(): Authentication {
            return SecurityContextHolder.getContext().authentication
        }

        /**
         * 生成BCryptPasswordEncoder密码
         *
         * @param password 密码
         * @return 加密字符串
         */
        @JvmStatic
        fun encryptPassword(password: String?): String? {
            val passwordEncoder = BCryptPasswordEncoder()
            return passwordEncoder.encode(password)
        }

        /**
         * 判断密码是否相同
         *
         * @param rawPassword 真实密码
         * @param encodedPassword 加密后字符
         * @return 结果
         */
        @JvmStatic
        fun matchesPassword(rawPassword: String?, encodedPassword: String?): Boolean {
            val passwordEncoder = BCryptPasswordEncoder()
            return passwordEncoder.matches(rawPassword, encodedPassword)
        }

        /**
         * 是否为管理员
         *
         * @param userId 用户ID
         * @return 结果
         */
        @JvmStatic
        fun isAdmin(userId: Long?): Boolean {
            return userId != null && 1L == userId
        }
    }
}
