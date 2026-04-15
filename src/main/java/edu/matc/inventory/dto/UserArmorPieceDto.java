package edu.matc.inventory.dto;

import edu.matc.inventory.entity.UserArmorPiece;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a user's armor piece.
 * Used in REST responses to expose armor piece details
 * without exposing the full entity.
 */
public class UserArmorPieceDto {

    private int id;
    private String armorType;
    private String weightClass;
    private String armorSlot;
    private LocalDateTime createdAt;
    private String star1Effect;
    private String star2Effect;
    private String star3Effect;
    private String star4Effect;

    /**
     * Constructs a DTO from a UserArmorPiece entity.
     *
     * @param piece the entity to convert
     */
    public UserArmorPieceDto(UserArmorPiece piece) {
        this.id = piece.getId();
        this.armorType = piece.getArmorType().getTypeName();
        this.weightClass = piece.getArmorType().getWeightClass();
        this.armorSlot = piece.getArmorSlot().getSlotName();
        this.createdAt = piece.getCreatedAt();
        this.star1Effect = piece.getStar1Effect() != null ? piece.getStar1Effect().getName() : null;
        this.star2Effect = piece.getStar2Effect() != null ? piece.getStar2Effect().getName() : null;
        this.star3Effect = piece.getStar3Effect() != null ? piece.getStar3Effect().getName() : null;
        this.star4Effect = piece.getStar4Effect() != null ? piece.getStar4Effect().getName() : null;
    }

    /**
     * Gets id.
     *
     * @return the id
     */

    public int getId() { return id; }

    /**
     * Gets armor type.
     *
     * @return the armor type
     */
    public String getArmorType() { return armorType; }

    /**
     * Gets weight class.
     *
     * @return the weight class
     */
    public String getWeightClass() { return weightClass; }

    /**
     * Gets armor slot.
     *
     * @return the armor slot
     */
    public String getArmorSlot() { return armorSlot; }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Gets star 1 effect.
     *
     * @return the star 1 effect
     */
    public String getStar1Effect() { return star1Effect; }

    /**
     * Gets star 2 effect.
     *
     * @return the star 2 effect
     */
    public String getStar2Effect() { return star2Effect; }

    /**
     * Gets star 3 effect.
     *
     * @return the star 3 effect
     */
    public String getStar3Effect() { return star3Effect; }

    /**
     * Gets star 4 effect.
     *
     * @return the star 4 effect
     */
    public String getStar4Effect() { return star4Effect; }
}