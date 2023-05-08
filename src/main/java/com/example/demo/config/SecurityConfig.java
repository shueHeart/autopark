package com.example.demo.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.demo.controller.LoginController;
import com.example.demo.manager.service.UserDetailsManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsManager managerDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//    	http.csrf().disable().authorizeRequests()
//        .anyRequest().permitAll();

//		http.csrf().disable().authorizeRequests().antMatchers("/auth", "/login", "/create/manager", "/manager")
//				.permitAll().anyRequest().authenticated();

		http.authorizeRequests()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/enterprises")
				.permitAll()
			.and().httpBasic()
			.and().csrf().disable();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		log.info("loooooog");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(managerDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException{
        return new TelegramBotsApi(DefaultBotSession.class);
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////            .inMemoryAuthentication()
////                .withUser("user").password("{noop}1").roles("USER");
//    	auth.jdbcAuthentication()
//	        .dataSource(dataSource)
//	        .usersByUsernameQuery("SELECT username, password, enabled FROM manager WHERE username = ?")
//	        .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
//
//        
//    }
}
