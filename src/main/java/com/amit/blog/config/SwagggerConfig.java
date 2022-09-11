package com.amit.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;







@Configuration
public class SwagggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}

	private List<SecurityReference> sf() {

		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
	}

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging Application : Backend Course",
				"This project is developed by Learn Code With Durgesh", "1.0", "Terms of Service",
				new Contact("Durgesh", "https://learncodewithdurgesh.com", "learncodewithdurgesh@gmail.com"),
				"License of APIS", "API license URL", Collections.emptyList());
	};

}