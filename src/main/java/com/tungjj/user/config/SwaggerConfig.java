package com.tungjj.user.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Authentication for users"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class SwaggerConfig {
    // @Bean
    // public Docket api() {
    // return new Docket(DocumentationType.SWAGGER_2)
    // .select()
    // .apis(RequestHandlerSelectors.any())
    // .paths(PathSelectors.any())
    // .build();
    // }
}
