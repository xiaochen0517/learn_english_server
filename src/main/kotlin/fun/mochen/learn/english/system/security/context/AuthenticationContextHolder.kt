package `fun`.mochen.learn.english.system.security.context

import org.springframework.security.core.Authentication

class AuthenticationContextHolder {

    companion object {
        @JvmField
        val contextHolder = ThreadLocal<Authentication>()

        @JvmStatic
        fun getContext(): Authentication {
            return contextHolder.get()
                ?: throw IllegalStateException("No Authentication object found in SecurityContext")
        }

        @JvmStatic
        fun setContext(context: Authentication) {
            contextHolder.set(context)
        }

        @JvmStatic
        fun clearContext() {
            contextHolder.remove()
        }

    }
}
