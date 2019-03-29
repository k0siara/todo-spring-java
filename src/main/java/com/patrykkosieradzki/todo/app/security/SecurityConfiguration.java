package com.patrykkosieradzki.todo.app.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().hasAnyAuthority("ADMIN", "USER")
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login").failureUrl("/login?error")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .and()
                .logout().logoutSuccessUrl("/");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);


    }
}
