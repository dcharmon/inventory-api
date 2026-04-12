## Fo76 Inventory and Item Management RESTful Web Service

---

### Overview 

---

Fallout 76 (FO76) is an online multiplayer survival RPG developed by Bethesda Game Studios. Players explore a post-apocalyptic open world, gathering armor and gear along the way. A big part of the game is hunting for armor pieces with the right "legendary" effects — random stat bonuses that drop from enemies — and many players end up juggling spreadsheets or notes apps just to keep track of what they have.

The FO76 Inventory API provides a RESTful service that allows players to catalog their collected armor pieces, assign legendary effects, and organize gear into named loadouts for different playstyles. The service exposes standardized endpoints for creating, retrieving, updating, and querying a player's inventory, making it easy to build companion apps, build planners, or stat calculators.

The service exposes the following resources and endpoints for managing a player's FO76 armor inventory and loadouts:

---

### Resources

---

- `armor-types` — reference data for all armor types and their base resistances
- `legendary-effects` — reference data for all legendary effects by star tier
- `inventory/armor` — a user's collected armor pieces with legendary effects applied
- `loadouts` — named gear sets that a user has organized for different playstyles

### Service Calls

---

GET    /armor-types                          - list all armor types

GET    /armor-types/{id}                     - get a specific armor type with resistances

GET    /legendary-effects                    - list all legendary effects

GET    /legendary-effects?star={1-4}         - filter by star tier

GET    /users/{userId}/inventory/armor       - get a user's armor pieces

POST   /users/{userId}/inventory/armor       - add an armor piece

DELETE /users/{userId}/inventory/armor/{id}  - remove an armor piece

GET    /users/{userId}/loadouts              - get a user's loadouts

POST   /users/{userId}/loadouts              - create a loadout

DELETE /users/{userId}/loadouts/{id}         - delete a loadout

### Resource Models

**Armor Type**
```json
{
  "id": 1,
  "typeName": "Arctic Marine Armor",
  "weightClass": "Sturdy",
  "baseResistances": [
    { "slotGroup": "ARM",   "damage": 39, "energy": 33, "radiation": 21, "poison": 14, "fire": 21, "cryo": 39 },
    { "slotGroup": "LEG",   "damage": 39, "energy": 33, "radiation": 21, "poison": 14, "fire": 21, "cryo": 39 },
    { "slotGroup": "TORSO", "damage": 71, "energy": 63, "radiation": 39, "poison": 24, "fire": 39, "cryo": 71 }
  ]
}
```

**Legendary Effect**
```json
{
  "legendaryEffectId": 6,
  "name": "Chameleon",
  "description": "Become invisible while sneaking and not moving",
  "star": 1,
  "armorCategory": "ANY"
}
```

**User Armor Piece**
```json
{
  "id": 7,
  "armorType": "Arctic Marine Armor",
  "weightClass": "Sturdy",
  "armorSlot": "Left Arm",
  "createdAt": "2026-04-01T16:24:12",
  "star1Effect": "Assassin's",
  "star2Effect": "HazMat",
  "star3Effect": "Healthy",
  "star4Effect": null
}
```

**Loadout**
```json
{
  "id": 1,
  "name": "Cryo Resist",
  "notes": "Resists cryo damage.",
  "createdAt": "2026-04-01T17:06:49"
}
```
Note: Armor pieces associated with a loadout are not currently included in the response due to circular reference handling. This is planned for a future implementation.

### Request and Response Formats

All endpoints accept and return **JSON** (`application/json`).

| Method | Request Body | Response Body |
|--------|-------------|---------------|
| GET    | None        | JSON          |
| POST   | JSON        | JSON          |
| DELETE | None        | None          |

HTTP status codes used:
- `200 OK` — successful GET with results
- `201 Created` — successful POST
- `204 No Content` — successful DELETE, or GET with no results found
- `404 Not Found` — user or resource not found
- `500 Internal Server Error` — unexpected server or database error