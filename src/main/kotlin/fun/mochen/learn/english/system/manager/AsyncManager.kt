package `fun`.mochen.learn.english.system.manager

import `fun`.mochen.learn.english.system.utils.SpringUtils
import `fun`.mochen.learn.english.system.utils.Threads
import java.util.*
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private val OPERATE_DELAY_TIME = 10

    /**
     * 异步操作任务调度线程池
     */
    private val executor: ScheduledExecutorService = SpringUtils.getBean("scheduledExecutorService")

    companion object {

        @JvmField
        val me = AsyncManager()

        @JvmStatic
        fun me(): AsyncManager {
            return me
        }

    }

    /**
     * 单例模式
     */
    private fun AsyncManager() {}


    /**
     * 执行任务
     *
     * @param task 任务
     */
    fun execute(task: TimerTask?) {
        executor.schedule(task, OPERATE_DELAY_TIME.toLong(), TimeUnit.MILLISECONDS)
    }

    /**
     * 停止任务线程池
     */
    fun shutdown() {
        Threads.shutdownAndAwaitTermination(executor)
    }
}
