package `fun`.mochen.learn.english.system.utils

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import java.lang.NullPointerException

class SpringUtils : BeanFactoryPostProcessor, ApplicationContextAware {

    companion object {
        @JvmField
        var beanFactory: ConfigurableListableBeanFactory? = null

        @JvmField
        var applicationContext: ApplicationContext? = null


        /**
         * 获取对象
         *
         * @param name
         * @return Object 一个以所给名字注册的bean的实例
         * @throws org.springframework.beans.BeansException
         */
        @Throws(BeansException::class)
        fun <T> getBean(name: String): T {
            if (beanFactory == null) {
                throw NullPointerException("beanFactory is null")
            }
            return beanFactory!!.getBean(name) as T
        }

        /**
         * 获取类型为requiredType的对象
         *
         * @param clz
         * @return
         * @throws org.springframework.beans.BeansException
         */
        @Throws(BeansException::class)
        fun <T> getBean(clz: Class<T>): T {
            if (beanFactory == null) {
                throw NullPointerException("beanFactory is null")
            }
            return beanFactory!!.getBean(clz)
        }

    }

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        Companion.beanFactory = beanFactory
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }
}
