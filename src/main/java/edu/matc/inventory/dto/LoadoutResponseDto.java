package edu.matc.inventory.dto;

import edu.matc.inventory.entity.Loadout;
import edu.matc.inventory.entity.UserArmorPiece;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoadoutResponseDto {
    private int id;
    private String name;
    private String notes;
    private LocalDateTime createdAt;
    private List<UserArmorPieceDto> armorPieces;

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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<UserArmorPieceDto> getArmorPieces() { return armorPieces; }
}