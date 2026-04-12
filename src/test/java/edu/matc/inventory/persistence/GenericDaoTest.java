package edu.matc.inventory.persistence;

import edu.matc.inventory.entity.LegendaryEffect;
import edu.matc.inventory.testutils.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GenericDao using the test database.
 *
 */
class GenericDaoTest {

    private GenericDao<LegendaryEffect> dao;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        dao = new GenericDao<>(LegendaryEffect.class);
    }

    // --- getById() ---

    @Test
    void getByIdSuccess() {
        LegendaryEffect effect = dao.getById(19);
        assertNotNull(effect);
        assertEquals("Unyielding", effect.getName());
        assertEquals(1, effect.getStar());
        assertEquals("STANDARD", effect.getArmorCategory());
    }

    @Test
    void getByIdNotFound() {
        LegendaryEffect effect = dao.getById(99999);
        assertNull(effect);
    }

    // --- getAll() ---

    @Test
    void getAllSuccess() {
        List<LegendaryEffect> effects = dao.getAll();
        assertNotNull(effects);
        assertEquals(79, effects.size());
    }

    // --- insert() ---

    @Test
    void insertSuccess() {
        LegendaryEffect newEffect = new LegendaryEffect();
        newEffect.setName("Test Effect");
        newEffect.setDescription("A test legendary effect");
        newEffect.setStar(1);
        newEffect.setArmorCategory("ANY");

        int id = dao.insert(newEffect);
        assertTrue(id > 0);

        LegendaryEffect inserted = dao.getById(id);
        assertNotNull(inserted);
        assertEquals("Test Effect", inserted.getName());
        assertEquals(1, inserted.getStar());
    }

    // --- update() ---

    @Test
    void updateSuccess() {
        // Bolstering (id=5)
        LegendaryEffect effect = dao.getById(5);
        assertNotNull(effect);

        effect.setDescription("Updated description");
        dao.update(effect);

        LegendaryEffect updated = dao.getById(5);
        assertEquals("Updated description", updated.getDescription());
    }

    // --- delete() ---

    @Test
    void deleteSuccess() {
        // Adrenal (id=1)
        LegendaryEffect effect = dao.getById(1);
        assertNotNull(effect);

        dao.delete(effect);

        LegendaryEffect afterDelete = dao.getById(1);
        assertNull(afterDelete);
    }

    // --- findByPropertyEqual() ---

    @Test
    void findByPropertyEqualByStar() {
        // star=1 effects = 22 total
        List<LegendaryEffect> results = dao.findByPropertyEqual("star", 1);
        assertNotNull(results);
        assertEquals(22, results.size());
        results.forEach(e -> assertEquals(1, e.getStar()));
    }

    @Test
    void findByPropertyEqualByName() {
        List<LegendaryEffect> results = dao.findByPropertyEqual("name", "Chameleon");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(6, results.get(0).getId());
    }

    @Test
    void findByPropertyEqualByArmorCategory() {
        // STANDARD effects: ids 11,19,21,28,44,51,58,60 = 8 total
        List<LegendaryEffect> results = dao.findByPropertyEqual("armorCategory", "STANDARD");
        assertNotNull(results);
        assertEquals(8, results.size());
    }

    @Test
    void findByPropertyEqualNoMatch() {
        List<LegendaryEffect> results = dao.findByPropertyEqual("name", "DoesNotExist");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}