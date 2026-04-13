package edu.matc.inventory.rest;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.servers.Server;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
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
                        .description("API for managing Fallout 76 player inventories"))
                .addServersItem(new Server().url("http://localhost:8080/InventoryAPI_war"))
                .addServersItem(new Server().url("http://inventory-api-fo76-env.eba-kvcd43xn.us-east-2.elasticbeanstalk.com"))
                .tags(Arrays.asList(
                        new Tag().name("Armor Types").description("Reference data for FO76 armor types and their base resistances"),
                        new Tag().name("Legendary Effects").description("Reference data for FO76 legendary effects by star tier"),
                        new Tag().name("User Armor").description("Manage a user's collected armor pieces"),
                        new Tag().name("Loadouts").description("Manage a user's named gear loadouts")
                ));

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