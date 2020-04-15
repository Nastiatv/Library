package by.runa.lib.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("by.runa.lib.dao")
@EntityScan("by.runa.lib.entities")
public class DaoConfiguration {

}
