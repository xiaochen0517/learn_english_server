package `fun`.mochen.learn.english.core.common.utils

import java.util.regex.Pattern
import cn.hutool.core.util.StrUtil as HutoolStrUtil

open class StrUtil {
    companion object : HutoolStrUtil() {

        @JvmStatic
        fun replaceAll(text: String, regex: Pattern, replacement: String): String {
            return regex.matcher(text).replaceAll(replacement)
        }

        /**
         * 是否包含字符串
         *
         * @param str 验证字符串
         * @param strs 字符串组
         * @return 包含返回true
         */
        @JvmStatic
        fun inStringIgnoreCase(str: String, vararg strs: String): Boolean {
            for (s in strs) {
                if (str.equals(s.trim(), ignoreCase = true)) {
                    return true
                }
            }
            return false
        }

        @JvmStatic
        fun isWord(word: String): Boolean {
            return word.matches(Regex("^[a-zA-Z-]+$"))
        }

        @JvmStatic
        fun checkWord(word: String) {
            if (!isWord(word)) {
                throw IllegalArgumentException("param is not a word, param: $word")
            }
        }
    }
}
