package `fun`.mochen.learn.english.system.config

import `fun`.mochen.learn.english.constant.BaseConstants
import `fun`.mochen.learn.english.system.interceptor.RepeatSubmitInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResourcesConfig : WebMvcConfigurer {


    @Autowired
    private lateinit var repeatSubmitInterceptor: RepeatSubmitInterceptor

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        /** 本地文件上传路径  */
        registry.addResourceHandler(BaseConstants.RESOURCE_PREFIX + "/**")
            .addResourceLocations("file:D:/WorkSpace/OtherFiles/uploadPath/")
    }

    /**
     * 自定义拦截规则
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**")
    }

    /**
     * 跨域配置
     */
    @Bean
    fun corsFilter(): CorsFilter? {
        val config = CorsConfiguration()
        config.allowCredentials = true
        // 设置访问源地址
        config.addAllowedOriginPattern("*")
        // 设置访问源请求头
        config.addAllowedHeader("*")
        // 设置访问源请求方法
        config.addAllowedMethod("*")
        // 有效期 1800秒
        config.maxAge = 1800L
        // 添加映射路径，拦截一切请求
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        // 返回新的CorsFilter
        return CorsFilter(source)
    }
}
