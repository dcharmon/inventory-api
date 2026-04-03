package edu.matc.inventory.rest;

import edu.matc.inventory.entity.*;
import edu.matc.inventory.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class InventoryService {

    private GenericDao<UserArmorPiece> userArmorPieceDao = new GenericDao<>(UserArmorPiece.class);

    @GET
    @Path("/users/{userId}/inventory/armor")
    @Produces("application/json")
    public Response getUserArmorPieces(@PathParam("userId") int userId) {
        // TODO - filter by userId
        return Response.ok().build();
    }

    @POST
    @Path("/users/{userId}/inventory/armor")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addArmorPiece(@PathParam("userId") int userId, UserArmorPiece armorPiece) {
        // TODO
        return Response.ok().build();
    }

    @DELETE
    @Path("/users/{userId}/inventory/armor/{id}")
    @Produces("application/json")
    public Response deleteArmorPiece(@PathParam("userId") int userId, @PathParam("id") int id) {
        // TODO
        return Response.ok().build();
    }
}
