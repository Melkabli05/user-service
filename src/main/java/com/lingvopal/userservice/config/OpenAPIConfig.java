package com.lingvopal.userservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {


    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("melkabli05@gmail.com");
        contact.setName("Mohammed El-Kabli");
        contact.setUrl("https://www.linkedin.com/in/mohammed-el-kabli/");

        License mitLicense = new License().name("MIT License");

        Info info = new Info()
                .title("User Service API")
                .version("1.0")
                .contact(contact)
                .description("This is the API documentation for the User Service")
                .license(mitLicense);

        return new OpenAPI().info(info).addServersItem(devServer);
    }
}
