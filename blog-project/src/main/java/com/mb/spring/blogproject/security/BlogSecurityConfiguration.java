package com.mb.spring.blogproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class BlogSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/page").permitAll()
                .antMatchers("/loginSignup").permitAll()
                .antMatchers("/validateLogin").permitAll()
                .antMatchers("/pageAfterLogin").permitAll()
                .antMatchers("/addNewUser").permitAll()
                .antMatchers("/newBlog").permitAll()
                .antMatchers("/addBlog").permitAll()
                .antMatchers("/updateBlog").permitAll()
                .antMatchers("/addComment").permitAll()
                .antMatchers("/deleteComment").permitAll()
                .antMatchers("/editBlog").permitAll()
                .antMatchers("/viewBlog").permitAll()
                .antMatchers("/deleteBlog").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/filter-by-author").permitAll()
                .antMatchers("/filter-by-date-time").permitAll()
                .antMatchers("/filter-by-tags").permitAll()
                .antMatchers("/sort").permitAll()
                .antMatchers("/sort-non-logged-in").permitAll()
                .antMatchers("/logout").permitAll()
                .and()
                .formLogin()
                .loginPage("/").permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/loginSignup")
                .permitAll();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
