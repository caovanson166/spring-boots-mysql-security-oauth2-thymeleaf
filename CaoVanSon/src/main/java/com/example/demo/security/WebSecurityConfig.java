package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.demo.security.oauth2.CustomOAuth2User;
import com.example.demo.security.oauth2.CustomOAuth2UserService;
import com.example.demo.security.oauth2.Oauth2LoginSuccess;
import com.example.demo.security.oauth2.UserService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailService();
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/User/login", "/oauth/**").authenticated().anyRequest().permitAll().and()
				.formLogin()
					.loginProcessingUrl("/j_spring_security_login")
					.loginPage("/")
					.usernameParameter("email")
					.passwordParameter("password")
					.defaultSuccessUrl("/User/login")
					.failureUrl("/?error=true")
					.permitAll()
				.and().oauth2Login()
					.loginPage("/")
					.userInfoEndpoint()
					.userService(oauthUserService)
				.and()
//					.successHandler(oauth2LoginSuccess)
//	                    .redirectionEndpoint();
//	                    .defaultSuccessUrl("/User/login")
				.and().logout().logoutSuccessUrl("/?logout=true").permitAll();
	}

	@Autowired
	private CustomOAuth2UserService oauthUserService;
	@Autowired
	private Oauth2LoginSuccess oauth2LoginSuccess;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

//	    .exceptionHandling()
//        .accessDeniedPage("/403"); ng dung ko du quyen truy cap
	@Bean
	public PersistentTokenRepository pe() {
		JdbcTokenRepositoryImpl tokenn = new JdbcTokenRepositoryImpl();
		tokenn.setDataSource(dataSource);
		return tokenn;
	}
}
