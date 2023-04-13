package `fun`.mochen.learn.english.system.config

import `fun`.mochen.learn.english.system.properties.PermitAllUrlProperties
import `fun`.mochen.learn.english.system.security.filter.JwtAuthenticationTokenFilter
import `fun`.mochen.learn.english.system.security.handle.AuthenticationEntryPointImpl
import `fun`.mochen.learn.english.system.security.handle.LogoutSuccessHandlerImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    companion object {
        private val log = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    /**
     * 认证失败处理类
     */
    @Autowired
    private lateinit var unauthorizedHandler: AuthenticationEntryPointImpl

    /**
     * 退出处理类
     */
    @Autowired
    private lateinit var logoutSuccessHandler: LogoutSuccessHandlerImpl

    /**
     * token认证过滤器
     */
    @Autowired
    private lateinit var authenticationTokenFilter: JwtAuthenticationTokenFilter

    /**
     * 跨域过滤器
     */
    @Autowired
    private lateinit var corsFilter: CorsFilter

    /**
     * 允许匿名访问的地址
     */
    @Autowired
    private lateinit var permitAllUrl: PermitAllUrlProperties

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        // 注解标记允许匿名访问的url
        val registry = httpSecurity.authorizeRequests()
        permitAllUrl.getUrls().forEach { url ->
            log.info("允许匿名访问的url: $url")
            registry.antMatchers(url).permitAll()
        }

        httpSecurity
            // CSRF禁用，因为不使用session
            .csrf().disable()
            // 禁用HTTP响应标头
            .headers().cacheControl().disable().and()
            // 认证失败处理类
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // 过滤请求
            .authorizeRequests()
            // 对于登录login 注册register 验证码captchaImage 允许匿名访问
            .antMatchers("/login", "/register", "/captchaImage").permitAll()
            // 静态资源，可匿名访问
            .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**")
            .permitAll()
            // 文档地址，可匿名访问
            .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**")
            .permitAll()
            // 测试地址，可匿名访问
            .antMatchers("/test/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .headers().frameOptions().disable()
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter::class.java)
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter::class.java)
        // 认证信息管理
        httpSecurity.userDetailsService(userDetailsService)

        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
