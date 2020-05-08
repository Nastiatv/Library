package by.runa.lib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import by.runa.lib.utils.FacebookConnectionSignUp;
import by.runa.lib.utils.FacebookSignInAdapter;

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

		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/books/**", "/login", "/logout", "/users/**", "/css/**", "/img/**").permitAll()
				.antMatchers("/admin/**", "/departments/**").hasRole("ADMIN")
				.antMatchers("/orders/**", "/feedbacks/**").hasAnyRole("ADMIN","USER")

				.and().formLogin().loginPage("/login").defaultSuccessUrl("/users/{id}", true).permitAll()
				.and().logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/bye").permitAll()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(dataSource).authoritiesByUsernameQuery(
				"SELECT user.username as username, role.name as role FROM user INNER JOIN user_role ON "
						+ "user.id = user_role.user_id INNER JOIN role ON user_role.role_id = role.id WHERE user.username = ?")
				.usersByUsernameQuery("select username, password, 1 as enabled from user where user.username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
			FacebookConnectionSignUp facebookConnectionSignUp, UsersConnectionRepository usersConnectionRepository) {
		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignUp);
		ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator,
				usersConnectionRepository, new FacebookSignInAdapter());
		providerSignInController.setPostSignInUrl("/books/");
		return providerSignInController;
	}
}