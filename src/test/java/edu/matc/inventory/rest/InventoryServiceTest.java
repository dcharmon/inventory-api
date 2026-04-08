package edu.matc.inventory.rest;

import edu.matc.inventory.testutils.Database;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(InventoryService.class)
                .register(org.glassfish.jersey.jackson.JacksonFeature.class)
                .register(ObjectMapperContextResolver.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    // getUserArmorPieces tests
    @Test
    void getUserArmorPieces_userNotFound_returns404() {
        Response response = target("/inventories/users/99/inventory/armor")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, response.getStatus());
    }

    @Test
    void getUserArmorPieces_userHasNoArmor_returns204() {
        // user id 1 exists in the DB but has no armor pieces
        Response response = target("/inventories/users/1/inventory/armor")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(204, response.getStatus());
    }

    @Test
    void getUserArmorPieces_success_returns200() {
        // user id 2 has armor pieces in the DB
        Response response = target("/inventories/users/2/inventory/armor")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
    }

    // addArmorPiece tests
    @Test
    void addArmorPiece_userNotFound_returns404() {
        String json = "{\"armorType\":{\"id\":1},\"armorSlot\":{\"id\":1}}";
        Response response = target("/inventories/users/99/inventory/armor")
                .request()
                .post(Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(404, response.getStatus());
    }

    @Test
    void addArmorPiece_success_returns201() {
        String json = "{\"armorType\":{\"id\":1},\"armorSlot\":{\"id\":1}}";
        Response response = target("/inventories/users/2/inventory/armor")
                .request()
                .post(Entity.entity(json, MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));
        assertEquals(201, response.getStatus());
    }


    // deleteArmorPiece tests
    @Test
    void deleteArmorPiece_notFound_returns404() {
        // armor piece id 99 doesn't exist
        Response response = target("/inventories/users/2/inventory/armor/99")
                .request()
                .delete();
        assertEquals(404, response.getStatus());
    }

    @Test
    void deleteArmorPiece_wrongUser_returns404() {
        // armor piece id 7 belongs to user 2, not user 1
        Response response = target("/inventories/users/1/inventory/armor/7")
                .request()
                .delete();
        assertEquals(404, response.getStatus());
    }

    @Test
    void deleteArmorPiece_success_returns204() {
        // armor piece id 7 belongs to user 2
        Response response = target("/inventories/users/2/inventory/armor/7")
                .request()
                .delete();
        assertEquals(204, response.getStatus());
    }
}