package com.javacodegeeks.examples.jaxrs;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.javacodegeeks.examples.jaxrs.service.StudentService;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringBootJaxrsApplication {

    @Autowired
    private Bus bus;

	@Autowired
	private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJaxrsApplication.class, args);
    }


    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setServiceBeans(List.of(studentService));
        endpoint.setAddress("/");
        endpoint.setFeatures(Arrays.asList(createOpenApiFeature()));
        endpoint.setProvider(jsonProvider());
        return endpoint.create();
    }


    @Bean
    public OpenApiFeature createOpenApiFeature() {
        final OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setPrettyPrint(true);
        openApiFeature.setTitle("Spring Boot CXF REST Application");
        openApiFeature.setContactName("The Apache CXF team");
        openApiFeature.setDescription("This sample project demonstrates how to use CXF JAX-RS services"
                + " with Spring Boot. This demo has two JAX-RS class resources being"
                + " deployed in a single JAX-RS endpoint.");
        openApiFeature.setVersion("1.0.0");
        openApiFeature.setSwaggerUiConfig(
                new SwaggerUiConfig()
                        .url("/services/openapi.json"));
        return openApiFeature;
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

}
