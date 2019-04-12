package com.patrykkosieradzki.todo.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    public SecurityConfiguration() {

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint() {
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<>();
        entryPoints.put(new AntPathRequestMatcher("/api/**"), new Http403ForbiddenEntryPoint());

        DelegatingAuthenticationEntryPoint defaultEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
        defaultEntryPoint.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        return defaultEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .requestCache().requestCache(new CustomRequestCache())
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/**", "/h2console/**").permitAll()
                    .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                    .anyRequest().hasAnyAuthority("ADMIN", "USER")
                .and()
                    .exceptionHandling().authenticationEntryPoint(delegatingAuthenticationEntryPoint())
                .and()
                    .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login").failureUrl("/login?error")
                    .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .and()
                    .logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("ADMIN")

                .and()

                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .authorities("USER")

                .and()

                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
