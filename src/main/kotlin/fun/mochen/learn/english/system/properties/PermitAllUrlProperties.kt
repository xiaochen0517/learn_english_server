package `fun`.mochen.learn.english.system.properties

import `fun`.mochen.learn.english.core.common.annotation.Anonymous
import `fun`.mochen.learn.english.core.common.utils.StrUtil
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.util.regex.Pattern

@Configuration
class PermitAllUrlProperties : InitializingBean, ApplicationContextAware {

    companion object {
        @JvmField
        val PATTERN = Pattern.compile("\\{(.*?)\\}")

        @JvmField
        val ASTERISK = "*"
    }

    private lateinit var applicationContext: ApplicationContext

    private var urls: MutableList<String> = mutableListOf()

    fun getUrls(): List<String?> {
        return urls
    }

    fun setUrls(urls: MutableList<String>) {
        this.urls = urls
    }

    override fun afterPropertiesSet() {
        val mapping = applicationContext.getBean(RequestMappingHandlerMapping::class.java)
        val map = mapping.handlerMethods

        map.keys.forEach {
            val handlerMethod = map[it]
            // 获取函数上的注解
            val method = AnnotationUtils.findAnnotation(handlerMethod!!.method, Anonymous::class.java)
            method?.let { anonymous ->
                it.patternsCondition?.patterns?.forEach { url ->
                    urls.add(StrUtil.replaceAll(url, PATTERN, ASTERISK))
                }
            }
            // 获取类上的注解
            val controller = AnnotationUtils.findAnnotation(handlerMethod.beanType, Anonymous::class.java)
            controller?.let { anonymous ->
                it.patternsCondition?.patterns?.forEach { url ->
                    urls.add(StrUtil.replaceAll(url, PATTERN, ASTERISK))
                }
            }
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

}
