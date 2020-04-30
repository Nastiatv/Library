package by.runa.lib.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("by.runa.lib.service")
@EntityScan("by.runa.lib.entities")
public class ServiceConfiguration {

}
