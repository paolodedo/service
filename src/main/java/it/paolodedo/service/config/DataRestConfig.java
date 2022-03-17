package it.paolodedo.service.config;

import it.paolodedo.service.domain.Device;
import it.paolodedo.service.domain.Group;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(
		RepositoryRestConfiguration config, CorsRegistry cors) {

		config.exposeIdsFor(Device.class, Group.class);
	}

}