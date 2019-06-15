package com.patrykkosieradzki.todo.app.security.config;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.app.security.*;
import com.patrykkosieradzki.todo.app.security.jwt.JwtTokenFilter;
import com.patrykkosieradzki.todo.app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class MultiHttpSecurityConfiguration {

    private UserDetailsService userDetailsService;

    @Autowired
    public MultiHttpSecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Order(1)
    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter  {

        private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
        private AccessDeniedHandler accessDeniedHandler;
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        public ApiSecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                                        AccessDeniedHandlerImpl accessDeniedHandler,
                                        JwtTokenProvider jwtTokenProvider) {
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.accessDeniedHandler = accessDeniedHandler;
            this.jwtTokenProvider = jwtTokenProvider;
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                    .exceptionHandling()
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                        .antMatcher("/api/**")
                        .authorizeRequests()
                        .antMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                    .and()
                        .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                        .addFilterBefore(new ExceptionHandlerFilter(), JwtTokenFilter.class);
        }

    }

    @Order(2)
    @Configuration
    public static class VaadinSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .requestCache().requestCache(new CustomRequestCache())
                    .and()
                    .headers().frameOptions().disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/h2console/**", "/activate", "/register", "/").permitAll()
                    .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html**", "/webjars/**").permitAll()
                    .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                    .anyRequest().hasAnyRole("ADMIN", "USER")
                    .and()
                    //.exceptionHandling().authenticationEntryPoint(delegatingAuthenticationEntryPoint())
                    //.and()
                    .formLogin().loginPage(AppConstants.LOGIN_URL).permitAll().loginProcessingUrl(AppConstants.LOGIN_URL).failureUrl(AppConstants.LOGIN_FAILURE_URL)
                    .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                    .and()
                    .logout().logoutSuccessUrl(AppConstants.ROOT_URL);
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


}
