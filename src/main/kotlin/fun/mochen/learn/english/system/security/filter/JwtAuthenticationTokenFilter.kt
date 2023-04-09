package `fun`.mochen.learn.english.system.security.filter

import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.web.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenService: TokenService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val loginUser: LoginUser? = tokenService.getLoginUser(request)
        if (loginUser != null) {
            tokenService.verifyToken(loginUser)
            val authenticationToken = UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities())
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
    }
}
