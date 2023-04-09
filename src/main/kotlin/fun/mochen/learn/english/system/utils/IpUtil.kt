package `fun`.mochen.learn.english.system.utils

import org.apache.commons.lang3.StringUtils
import javax.servlet.http.HttpServletRequest

class IpUtil {
    companion object {

        const val REGX_0_255 = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)"

        // 匹配 ip
        const val REGX_IP = "(($REGX_0_255\\.){3}$REGX_0_255)"
        const val REGX_IP_WILDCARD =
            "(((\\*\\.){3}\\*)|($REGX_0_255(\\.\\*){3})|($REGX_0_255\\.$REGX_0_255)(\\.\\*){2}|(($REGX_0_255\\.){3}\\*))"

        // 匹配网段
        const val REGX_IP_SEG = "($REGX_IP\\-$REGX_IP)"

        /**
         * 获取客户端IP
         *
         * @return IP地址
         */
        fun getIpAddr(): String {
            return getIpAddr(ServletUtils.getRequest())
        }

        /**
         * 获取客户端IP
         *
         * @param request 请求对象
         * @return IP地址
         */
        fun getIpAddr(request: HttpServletRequest?): String {
            if (request == null) {
                return "unknown"
            }
            var ip: String? = request.getHeader("x-forwarded-for")
            if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
                ip = request.getHeader("Proxy-Client-IP")
            }
            if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
                ip = request.getHeader("X-Forwarded-For")
            }
            if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
                ip = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
                ip = request.getHeader("X-Real-IP")
            }
            if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
                ip = request.remoteAddr
            }
            return if ("0:0:0:0:0:0:0:1" == ip) "127.0.0.1" else getMultistageReverseProxyIp(ip)
        }

        /**
         * 从多级反向代理中获得第一个非unknown IP地址
         *
         * @param ip 获得的IP地址
         * @return 第一个非unknown IP地址
         */
        fun getMultistageReverseProxyIp(ip: String?): String {
            // 多级反向代理检测
            var ip = ip
            if (ip?.indexOf(",")!! > 0) {
                val ips = ip.trim { it <= ' ' }.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                for (subIp in ips) {
                    if (false == isUnknown(subIp)) {
                        ip = subIp
                        break
                    }
                }
            }
            return StringUtils.substring(ip, 0, 255)
        }

        /**
         * 检测给定字符串是否为未知，多用于检测HTTP请求相关
         *
         * @param checkString 被检测的字符串
         * @return 是否未知
         */
        fun isUnknown(checkString: String?): Boolean {
            return StringUtils.isBlank(checkString) || "unknown".equals(checkString, ignoreCase = true)
        }

        fun internalIp(addr: ByteArray): Boolean {
            if (addr.size < 2) {
                return true
            }
            val b0: Byte = addr.get(0)
            val b1: Byte = addr.get(1)
            // 10.x.x.x/8
            // 10.x.x.x/8
            val SECTION_1: Byte = 0x0A
            // 172.16.x.x/12
            // 172.16.x.x/12
            val SECTION_2 = 0xAC.toByte()
            val SECTION_3 = 0x10.toByte()
            val SECTION_4 = 0x1F.toByte()
            // 192.168.x.x/16
            // 192.168.x.x/16
            val SECTION_5 = 0xC0.toByte()
            val SECTION_6 = 0xA8.toByte()
            return when (b0) {
                SECTION_1 -> true
                SECTION_2 -> {
                    if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                        return true
                    }
                    when (b1) {
                        SECTION_6 -> return true
                    }
                    false
                }

                SECTION_5 -> {
                    when (b1) {
                        SECTION_6 -> return true
                    }
                    false
                }

                else -> false
            }
        }

    }
}
