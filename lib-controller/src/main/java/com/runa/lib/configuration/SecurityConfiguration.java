package com.runa.lib.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/", "/users/**","/departments/**",
				"/roles/**",
				"/orders/**",
				"/notifications/**",
				"/feedbacks/**",
				"/books/**", "/login", "/logout", "/register", "/css/**", "/img/**")
				.permitAll().antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/bye").permitAll().and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("test").password("{noop}test").roles("ADMIN1");
	}
}