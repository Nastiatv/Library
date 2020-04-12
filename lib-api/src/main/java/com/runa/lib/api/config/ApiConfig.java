package com.runa.lib.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.runa.lib.config.EntityConfig;

@Configuration
@Import(value = { EntityConfig.class })
public class ApiConfig {

}
