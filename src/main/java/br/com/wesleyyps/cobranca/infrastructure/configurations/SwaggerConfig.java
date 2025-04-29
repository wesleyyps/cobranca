package br.com.wesleyyps.cobranca.infrastructure.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private String apiVersion = "0.*.*";

    private String apiTitle = "API de Cobranças";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("cobranca")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        try {
            InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("properties-from-pom.properties");
            Properties properties = new Properties();
            properties.load(is);

            this.apiTitle = properties.getProperty("project.name");
            this.apiVersion = properties.getProperty("project.version");
        } catch(Exception e) {
            this.logger.error("Error loading properties from pom.xml", e);
        }

        return new OpenAPI()
            .info(
                new Info()
                    .title(this.apiTitle)
                    .version(apiVersion)
            )
            .addServersItem(
                new Server()
                    .url("/")
                    .description(this.apiTitle + " " + this.apiVersion)
                )
            .schemaRequirement(
                "bearerAuth", 
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
            .addSecurityItem(
                new SecurityRequirement()
                    .addList("bearerAuth")
            );
    }
}
