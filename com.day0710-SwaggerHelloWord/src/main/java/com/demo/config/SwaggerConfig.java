package com.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

@EnableSwagger2WebMvc
@Configuration
public class SwaggerConfig {

    @Bean(name = "docket")
    public Docket getDocket(@Qualifier("apiInfo") ApiInfo apiInfo){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Izumi Sakai")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.controller"))
                .build();
    }

    @Bean(name = "apiInfo")
    public ApiInfo getApiInfo(){
        //作者
        Contact myContact = new Contact(
                "Izumi Sakai",
                "http://47.113.97.26/",
                "izumisakai@aliyun.com");
        //API信息
        ApiInfo apiInfo=new ApiInfo(
                "API文档",
                "API 文档",
                "2.0",
                "猛男队",
                myContact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
        return apiInfo;
    }
}
