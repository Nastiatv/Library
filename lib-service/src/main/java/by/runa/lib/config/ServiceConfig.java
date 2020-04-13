package by.runa.lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import by.runa.lib.utils.config.MailConfiguration;

@Configuration
@Import(value = { DatasourceConfig.class, MailConfiguration.class, WebConfig.class })
public class ServiceConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
