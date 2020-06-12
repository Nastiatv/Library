package by.runa.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { SecurityConfiguration.class })
public class MainConfig {

}
