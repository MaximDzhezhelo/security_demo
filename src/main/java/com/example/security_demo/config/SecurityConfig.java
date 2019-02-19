package com.example.security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.security_demo.controller.FirstAnotherController.*;
import static com.example.security_demo.controller.SecondAnotherController.SECOND_URL_ANOTHER;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    static final String METRIC_ROLE = "PROMETHEUS";
    static final String FIRST_ANOTHER_ROLE = "FIRST_ANOTHER";
    static final String SECOND_ANOTHER_ROLE = "SECOND_ANOTHER";

    private final String metricLogin;
    private final String metricPass;

    private final String firstAnotherLogin;
    private final String firstAnotherPass;

    private final String secondAnotherLogin;
    private final String secondAnotherPass;

    public SecurityConfig(final Environment env) {
        metricLogin = env.getProperty("metric.login");
        metricPass = env.getProperty("metric.password");

        firstAnotherLogin = env.getProperty("firstAnother.login");
        firstAnotherPass = env.getProperty("firstAnother.password");

        secondAnotherLogin = env.getProperty("secondAnother.login");
        secondAnotherPass = env.getProperty("secondAnother.password");
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(metricLogin).password(metricPass).roles(METRIC_ROLE).build());
        manager.createUser(User.withUsername(firstAnotherLogin).password(firstAnotherPass).roles(FIRST_ANOTHER_ROLE).build());
        manager.createUser(User.withUsername(secondAnotherLogin).password(secondAnotherPass).roles(SECOND_ANOTHER_ROLE).build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(URL_SHOW_HEALTH).permitAll()
                .antMatchers(URL_SHOW_METRICS+ "**").hasRole(METRIC_ROLE)
                .antMatchers(FIRST_URL_ANOTHER + "**").hasRole(FIRST_ANOTHER_ROLE)
                .antMatchers(SECOND_URL_ANOTHER + "**").hasRole(SECOND_ANOTHER_ROLE)
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

}