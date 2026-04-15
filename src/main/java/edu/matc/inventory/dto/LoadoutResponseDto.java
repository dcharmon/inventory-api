package edu.matc.inventory.dto;

import edu.matc.inventory.entity.Loadout;
import edu.matc.inventory.entity.UserArmorPiece;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Data Transfer Object for returning Loadout data to the client.
 * Used in REST responses to represent a loadout and its associated
 * armor pieces.
 */
public class LoadoutResponseDto {

    private int id;
    private String name;
    private String notes;
    private LocalDateTime createdAt;

    /**
     * List of armor pieces associated with this loadout.
     */
    private List<UserArmorPieceDto> armorPieces;

    /**
     * Constructs a LoadoutResponseDto from a Loadout entity.
     *
     * @param loadout the Loadout entity to convert
     */
    public LoadoutResponseDto(Loadout loadout) {
        this.id = loadout.getId();
        this.name = loadout.getName();
        this.notes = loadout.getNotes();
        this.createdAt = loadout.getCreatedAt();
        this.armorPieces = new ArrayList<>();
        if (loadout.getArmorPieces() != null) {
            for (UserArmorPiece piece : loadout.getArmorPieces()) {
                this.armorPieces.add(new UserArmorPieceDto(piece));
            }
        }
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return id; }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() { return notes; }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Gets armor pieces.
     *
     * @return the armor pieces
     */
    public List<UserArmorPieceDto> getArmorPieces() { return armorPieces; }
}