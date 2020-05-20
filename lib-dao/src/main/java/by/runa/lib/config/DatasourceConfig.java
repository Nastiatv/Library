package by.runa.lib.config;

import by.runa.lib.utils.config.MapperConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { MapperConfiguration.class })
public class DatasourceConfig {

}
