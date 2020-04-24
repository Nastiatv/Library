package by.runa.lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/403").setViewName("403");
	}

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry connectionFactoryRegistry = new ConnectionFactoryRegistry();
		connectionFactoryRegistry.addConnectionFactory(facebookConnectionFactory());
		return connectionFactoryRegistry;
	}

	@Bean
	public FacebookConnectionFactory facebookConnectionFactory() {
		FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory("264566624941155",
				"7945e6d869dde73d4d48b1f0884d9878");
		facebookConnectionFactory.setScope("email, public_profile");
		return facebookConnectionFactory;
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new InMemoryUsersConnectionRepository(connectionFactoryLocator());

	}
}