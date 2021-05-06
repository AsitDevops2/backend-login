package com.raley.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
	public class SwaggerConfig {
	

	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2).apiInfo(metadata())
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build()
	                .securitySchemes(Collections.singletonList(apiKey()))
	                .securityContexts(Collections.singletonList(securityContext()))
	                .useDefaultResponseMessages(false);
	    }


	    List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope
	                = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Collections.singletonList(new SecurityReference("JWT Token", authorizationScopes));
	    }

	    private ApiInfo metadata() {
	        return new ApiInfoBuilder().title("Ralley POC API Documentation")
	                .description("API documentation for Raley REST Services.").build();
	    }

	    private ApiKey apiKey() {
	        return new ApiKey("Authorization", "Authorization", "header");
	    }

	    private SecurityContext securityContext() {
	        return SecurityContext.builder()
	                .securityReferences(defaultAuth())
	                .forPaths(PathSelectors.regex("/anyPath.*"))
	                .build();
	    }

	}

	   
