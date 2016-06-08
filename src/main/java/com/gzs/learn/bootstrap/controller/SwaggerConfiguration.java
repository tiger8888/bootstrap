package com.gzs.learn.bootstrap.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Controller
public class SwaggerConfiguration {
    @Bean
    public Docket testApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("user-api").apiInfo(apiInfo()).select()
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("User api").description("user api").termsOfServiceUrl("http://springfox.io")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
    }
}
