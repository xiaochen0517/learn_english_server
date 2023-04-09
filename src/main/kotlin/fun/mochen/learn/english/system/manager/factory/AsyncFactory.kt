package `fun`.mochen.learn.english.system.manager.factory

import org.slf4j.LoggerFactory
import java.util.*

class AsyncFactory {

    companion object {

        private val sys_user_logger = LoggerFactory.getLogger("sys-user")

        /**
         * 记录登录信息
         *
         * @param username 用户名
         * @param status 状态
         * @param message 消息
         * @param args 列表
         * @return 任务task
         */
        @JvmStatic
        fun recordLoginInfo(
            username: String?, status: String, message: String,
            vararg args: Any?
        ): TimerTask {
            return object : TimerTask() {
                override fun run() {}
            }
        }

        /**
         * 操作日志记录
         *
         * @param log 操作日志信息
         * @return 任务task
         */
        @JvmStatic
        fun recordOperationLog(log: Any): TimerTask {
            return object : TimerTask() {
                override fun run() {
                }
            }
        }
    }
}
