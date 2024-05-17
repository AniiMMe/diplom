package com.example.diplom.config.security;



import com.example.diplom.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**","/js/**","/css/**","/registration","/infoBox","/").permitAll()
                .antMatchers("/admin/**", "/user/**").hasAuthority("ADMIN")
                .antMatchers("/employee/**").hasAuthority("CLIENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").successHandler(successHandler())
                .failureUrl("/login/error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return  CustomAuthenticationSuccessHandler.getInstance();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
