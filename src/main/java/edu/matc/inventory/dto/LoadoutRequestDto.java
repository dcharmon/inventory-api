package edu.matc.inventory.dto;


/**
 * Data Transfer Object for creating or updating a Loadout.
 * Used in REST requests to pass loadout data from the client
 * to the server.
 */
public class LoadoutRequestDto {

    /**
     * The name of the loadout.
     */
    private String name;

    /**
     * Notes describing the loadout.
     */
    private String notes;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() { return notes; }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) { this.notes = notes; }
}