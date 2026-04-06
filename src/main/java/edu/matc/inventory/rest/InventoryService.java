package edu.matc.inventory.rest;

import edu.matc.inventory.dto.UserArmorPieceDto;
import edu.matc.inventory.entity.*;
import edu.matc.inventory.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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

    /**
     * Returns all armor pieces belonging to a specific user.
     * <p>
     * HTTP status codes:
     * 200 if armor pieces are found,
     * 204 if the user has no armor pieces,
     * 404 if the user does not exist,
     * 500 if a DB error occurs.
     *
     * @param userId the user's id
     * @return 200 with a list of armor pieces, 204 if none found, 404 if user not found
     */
    @GET
    @Path("/users/{userId}/inventory/armor")
    @Produces("application/json")
    public Response getUserArmorPieces(@PathParam("userId") int userId) {
        try {
            GenericDao<AppUser> userDao = new GenericDao<>(AppUser.class);
            AppUser user = userDao.getById(userId);

            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"User with id " + userId + " does not exist\"}")
                        .build();
            }

            List<UserArmorPiece> pieces = userArmorPieceDao.findByPropertyEqual("user", user);

            if (pieces == null || pieces.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            List<UserArmorPieceDto> dtos = new ArrayList<>();
            for (UserArmorPiece piece : pieces) {
                dtos.add(new UserArmorPieceDto(piece));
            }

            return Response.ok(dtos).build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while fetching armor pieces\"}").build();
        }
    }

    /**
     * Adds a new armor piece to a user's inventory.
     * <p>
     * HTTP status codes:
     * 201 if the armor piece was created,
     * 404 if the user does not exist,
     * 500 if a DB error occurs.
     *
     * @param userId    the user's id
     * @param armorPiece the armor piece to add
     * @return 201 with the created armor piece, 404 if user not found
     */
    @POST
    @Path("/users/{userId}/inventory/armor")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addArmorPiece(@PathParam("userId") int userId, UserArmorPiece armorPiece) {
        try {
            GenericDao<AppUser> userDao = new GenericDao<>(AppUser.class);
            AppUser user = userDao.getById(userId);

            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"User with id " + userId + " does not exist\"}")
                        .build();
            }

            armorPiece.setUser(user);
            int id = userArmorPieceDao.insert(armorPiece);
            UserArmorPiece created = userArmorPieceDao.getById(id);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(new UserArmorPieceDto(created))
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while adding the armor piece\"}").build();
        }
    }

    /**
     * Deletes a specific armor piece from a user's inventory.
     * <p>
     * HTTP status codes:
     * 204 if the armor piece was deleted,
     * 404 if the user or armor piece does not exist,
     * 500 if a DB error occurs.
     *
     * @param userId the user's id
     * @param id     the armor piece id
     * @return 204 on success, 404 if not found
     */
    @DELETE
    @Path("/users/{userId}/inventory/armor/{id}")
    @Produces("application/json")
    public Response deleteArmorPiece(@PathParam("userId") int userId, @PathParam("id") int id) {
        try {
            UserArmorPiece piece = userArmorPieceDao.getById(id);

            if (piece == null || piece.getUser().getUserId() != userId) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Armor piece with id " + id + " not found for user " + userId + "\"}")
                        .build();
            }

            userArmorPieceDao.delete(piece);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while deleting the armor piece\"}").build();
        }
    }


    /**
     * Returns all loadouts belonging to a specific user.
     * <p>
     * HTTP status codes:
     * 200 if loadouts are found,
     * 204 if the user has no loadouts,
     * 404 if the user does not exist,
     * 500 if a DB error occurs.
     *
     * @param userId the user's id
     * @return 200 with a list of loadouts, 204 if none found, 404 if user not found
     */
    @GET
    @Path("/users/{userId}/loadouts")
    @Produces("application/json")
    public Response getUserLoadouts(@PathParam("userId") int userId) {
        try {
            GenericDao<AppUser> userDao = new GenericDao<>(AppUser.class);
            AppUser user = userDao.getById(userId);

            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"User with id " + userId + " does not exist\"}")
                        .build();
            }

            GenericDao<Loadout> loadoutDao = new GenericDao<>(Loadout.class);
            List<Loadout> loadouts = loadoutDao.findByPropertyEqual("user", user);

            if (loadouts == null || loadouts.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            return Response.ok(loadouts).build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while fetching user loadouts\"}").build();
        }
    }

    /**
     * Adds a new loadout for a user.
     * <p>
     * HTTP status codes:
     * 201 if the loadout was created,
     * 404 if the user does not exist,
     * 500 if a DB error occurs.
     *
     * @param userId the user's id
     * @param loadout the loadout to add
     * @return 201 with the created loadout, 404 if user not found
     */
    @POST
    @Path("/users/{userId}/loadouts")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addUserLoadout(@PathParam("userId") int userId, Loadout loadout) {
        try {
            GenericDao<AppUser> userDao = new GenericDao<>(AppUser.class);
            AppUser user = userDao.getById(userId);

            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"User with id " + userId + " does not exist\"}")
                        .build();
            }

            loadout.setUser(user);
            GenericDao<Loadout> loadoutDao = new GenericDao<>(Loadout.class);
            int id = loadoutDao.insert(loadout);
            Loadout created = loadoutDao.getById(id);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(created)
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while creating the loadout\"}").build();
        }
    }

    /**
     * Deletes a specific loadout from a user's account.
     * <p>
     * HTTP status codes:
     * 204 if the loadout was deleted,
     * 404 if the user or loadout does not exist or does not belong to the user,
     * 500 if a DB error occurs.
     *
     * @param userId the user's id
     * @param id     the loadout id
     * @return 204 on success, 404 if not found
     */
    @DELETE
    @Path("/users/{userId}/loadouts/{id}")
    @Produces("application/json")
    public Response deleteUserLoadout(@PathParam("userId") int userId, @PathParam("id") int id) {
        try {
            GenericDao<Loadout> loadoutDao = new GenericDao<>(Loadout.class);
            Loadout loadout = loadoutDao.getById(id);

            if (loadout == null || loadout.getUser() == null || loadout.getUser().getUserId() != userId) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Loadout with id " + id + " not found for user " + userId + "\"}")
                        .build();
            }

            loadoutDao.delete(loadout);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"message\": \"An error occurred while deleting the loadout\"}").build();
        }
    }
}