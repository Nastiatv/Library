package by.runa.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import by.runa.lib.config.SecurityConfiguration;

@Configuration
@Import (value = {SecurityConfiguration.class})
public class MainConfig {

}
