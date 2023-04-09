package `fun`.mochen.learn.english.system.utils

import com.alibaba.fastjson2.JSON
import org.slf4j.LoggerFactory

class AddressUtils {
    companion object {

        private val log = LoggerFactory.getLogger(AddressUtils::class.java)

        // IP地址查询
        const val IP_URL = "http://whois.pconline.com.cn/ipJson.jsp"

        // 未知地址
        const val UNKNOWN = "XX XX"

        fun getRealAddressByIP(ip: String): String {
            // 内网不查询
            if (IpUtil.internalIp(ip.toByteArray())) {
                return "内网IP"
            }
//            if (false) {
//                try {
//                    val rspStr: String = HttpUtils.sendGet(IP_URL, "ip=$ip&json=true", Constants.GBK)
//                    if (StringUtils.isEmpty(rspStr)) {
//                        log.error("获取地理位置异常 {}", ip)
//                        return UNKNOWN
//                    }
//                    val obj = JSON.parseObject(rspStr)
//                    val region = obj.getString("pro")
//                    val city = obj.getString("city")
//                    return String.format("%s %s", region, city)
//                } catch (e: Exception) {
//                    log.error("获取地理位置异常 {}", ip)
//                }
//            }
            return UNKNOWN
        }
    }
}
