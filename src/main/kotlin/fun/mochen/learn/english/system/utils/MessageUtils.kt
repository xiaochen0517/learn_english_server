package `fun`.mochen.learn.english.system.utils

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

class MessageUtils {

    companion object {

        /**
         * 根据消息键和参数 获取消息 委托给spring messageSource
         *
         * @param code 消息键
         * @param args 参数
         * @return 获取国际化翻译值
         */
        @JvmStatic
        fun message(code: String, vararg args: Any): String {
            val messageSource = SpringUtils.getBean(MessageSource::class.java)
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
        }
    }
}
