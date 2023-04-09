package `fun`.mochen.learn.english.system.manager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationManager : AuthenticationManager {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(authentication: Authentication): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(authentication.name)
        if (!passwordEncoder.matches(authentication.credentials.toString(), userDetails.password)) {
            throw BadCredentialsException("密码不正确")
        }
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
    }
}
