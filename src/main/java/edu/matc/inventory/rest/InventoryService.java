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

    /**
     * Returns a specific armor type by id.
     * <p>
     * HTTP status codes:
     * 200 if a specific armor type id was found,
     * 404 if a specific armor type id is NOT found.
     *
     * <p>
     * Exception handling: if a DB error occurs, return 500.
     *
     * @param id armor type id.
     * @return 200 with the armor type, 404 if the armor type is not found.
     */
    @GET
    @Path("/armor-types/{id}")
    @Produces("application/json")
    public Response getSpecificArmorType(@PathParam("id") int id) {
        try {
            GenericDao<ArmorType> armorTypeDao = new GenericDao<>(ArmorType.class);
            ArmorType type = armorTypeDao.getById(id);

            if (type == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Armor type with id " + id + " does not exist\"}")
                        .build();
            }

            return Response.ok(type).build();

        } catch(Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while fetching armor types id\"}").build();
        }
    }

    /**
     * Returns all legendary effects and optionally filter by a specific star.
     * <p>
     * HTTP status codes:
     * 200 if legendary effects are found,
     * 204 if no legendary effects are found.
     *
     * <p>
     *  Exception handling: if a DB error occurs, return 500.
     *
     * @return 200 with a list of legendary effects, 204 if no legendary effects are found.
     */
    @GET
    @Path("/legendary-effects")
    @Produces("application/json")
    public Response getLegendaryEffects(@QueryParam("star") Integer star) {
        try {
            GenericDao<LegendaryEffect> legendaryEffectDao = new GenericDao<>(LegendaryEffect.class);
            List<LegendaryEffect> legendaryEffects;

            // GET all legendary effects by star tier
            // ELSE should return all legendary effects
            if (star != null) {
                legendaryEffects = legendaryEffectDao.findByPropertyEqual("star", star);
            } else {
                legendaryEffects = legendaryEffectDao.getAll();
            }

            if (legendaryEffects == null || legendaryEffects.isEmpty()) {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            }

            return Response.ok(legendaryEffects).build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while fetching legendary Effects\"}").build();
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
