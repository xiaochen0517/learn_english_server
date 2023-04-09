package `fun`.mochen.learn.english.common.annotation

import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RepeatSubmit(

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    val interval: Int = 5000,

    /**
     * 提示消息
     */
    val message: String = "不允许重复提交，请稍候再试"
) {}
