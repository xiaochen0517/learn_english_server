package `fun`.mochen.learn.english.core.common.utils

import java.util.regex.Pattern

class StrUtil : cn.hutool.core.util.StrUtil() {
    companion object {

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
    }
}
