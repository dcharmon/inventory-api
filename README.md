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
- `inventory/pa-frames` — a user's power armor frames and equipped pieces
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
  "armorTypeId": 1,
  "typeName": "Covert Scout Armor",
  "weightClass": "Light",
  "baseResistances": {
    "ARM":   { "damage": 26, "energy": 26, "radiation": 11, "poison": 14, "fire": 9,  "cryo": 9  },
    "LEG":   { "damage": 26, "energy": 26, "radiation": 11, "poison": 14, "fire": 9,  "cryo": 9  },
    "TORSO": { "damage": 52, "energy": 52, "radiation": 22, "poison": 26, "fire": 16, "cryo": 16 }
  }
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
  "userArmorPieceId": 1,
  "armorTypeId": 5,
  "armorSlotId": 2,
  "star1Effect": { "legendaryEffectId": 6, "name": "Chameleon" },
  "star2Effect": null,
  "star3Effect": null,
  "star4Effect": null,
  "createdAt": "2026-04-01T22:33:29Z"
}
```

**Loadout**
```json
{
  "loadoutId": 1,
  "name": "Stealth Build",
  "notes": "Full covert scout set for sneaking",
  "armorPieces": [ ],
  "createdAt": "2026-04-01T22:33:29Z"
}
```

### Request and Response Formats

All endpoints accept and return **JSON** (`application/json`).

| Method | Request Body | Response Body |
|--------|-------------|---------------|
| GET    | None        | JSON          |
| POST   | JSON        | JSON          |
| DELETE | None        | JSON (confirmation message) |

HTTP status codes used:
- `200 OK` — successful GET
- `201 Created` — successful POST
- `204 No Content` — successful DELETE
- `400 Bad Request` — invalid input
- `404 Not Found` — resource not found