package by.runa.lib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Import(value = { ServiceConfig.class })
@EnableScheduling
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private javax.sql.DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests().antMatchers("/", "/users/**", "/departments/**", "/roles/**", "/orders/**", "/feedbacks/**",
		"/books/**", "/login", "/logout", "/register", "/css/**", "/img/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("Admin")
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/bye").permitAll().and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(dataSource).authoritiesByUsernameQuery(
				"SELECT user.email as email, role.name as role FROM user INNER JOIN user_role ON "
						+ "user.id = user_role.user_id INNER JOIN role ON user_role.role_id = role.id WHERE user.email = ?")
				.usersByUsernameQuery("select email, password, 1 as enabled from user where email=?");
	}

}