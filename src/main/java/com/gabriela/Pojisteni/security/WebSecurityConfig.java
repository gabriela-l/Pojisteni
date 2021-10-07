package com.gabriela.Pojisteni.security;

import com.gabriela.Pojisteni.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /* Pro přístup do aplikace je nutné přihlášení, volný přístup pouze k registraci a loginu
        aplikace rozlišuje uživatelské role, user může pouze zobrazit seznam uživatelů a pojištění */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/register_success").hasAnyAuthority("Admin", "User", "Editor")
                .antMatchers("/").hasAnyAuthority("Admin", "User", "Editor")
                .antMatchers("/users").hasAnyAuthority("Admin", "User", "Editor")
                .antMatchers("/insurances").hasAnyAuthority("Admin", "User", "Editor")
                .antMatchers("/users/detail/*").hasAnyAuthority("Admin", "User", "Editor")
                .antMatchers("/user/edit/*").hasAnyAuthority("Admin", "Editor", "Editor")
                .antMatchers("/user/delete/*").hasAuthority("Admin")
                .antMatchers("/user/save").hasAuthority("Admin")
                .antMatchers("/insurance/edit/*").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/insurance/delete/*").hasAuthority("Admin")
                .antMatchers("/insurance/save").hasAuthority("Admin")
                .antMatchers("/login").authenticated()
                .and()
                .formLogin()
                    .defaultSuccessUrl("/users", true)
                    .loginPage("/login")
                    .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();
    }
}
