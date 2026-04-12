package edu.matc.inventory.rest;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class InventoryApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(InventoryService.class);
        classes.add(ObjectMapperContextResolver.class);
        classes.add(OpenApiResource.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Fo76 Inventory API")
                        .version("1.0")
                        .description("API for managing Fallout 76 player inventories"));

        SwaggerConfiguration config = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true)
                .resourcePackages(Collections.singleton("edu.matc.inventory.rest"));

        try {
            new io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder()
                    .openApiConfiguration(config)
                    .buildContext(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new HashSet<>();
    }
}