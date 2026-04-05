package edu.matc.inventory.rest;

import edu.matc.inventory.entity.*;
import edu.matc.inventory.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/inventories")
public class InventoryService {

    private GenericDao<UserArmorPiece> userArmorPieceDao = new GenericDao<>(UserArmorPiece.class);

    /**
     * Returns all armor types.
     * <p>
     * HTTP status codes:
     * 200 if armor types are found,
     * 204 if no armor types are found.
     *
     * <p>
     * Exception handling: if a DB error occurs, return 500.
     *
     * @return 200 with a list of armor types, 204 if no armor types are found
     */
    @GET
    @Path("/armor-types")
    @Produces("application/json")
    public Response getAllArmorTypes() {
        try {
            GenericDao<ArmorType> armorTypeDao = new GenericDao<>(ArmorType.class);
            List<ArmorType> type = armorTypeDao.getAll();

            // Should return 204 if no armor types are found
            if (type == null || type.isEmpty()) {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            }

            return Response.ok(type).build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while fetching armor types\"}").build();
        }
    }

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
