package exorpg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Savepoint;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exorpg.RPG.Personnage;
import exorpg.RPG.PotionSoin;
import exorpg.utils.DBManager;

public class PotionTest {
    Savepoint save;

    @BeforeAll
    public static void setup() {
        DBManager.init();
        DBManager.setAutocommit(false);
    }

    @BeforeEach
    public void init() {
        save = DBManager.setSavePoint();
    }

    @AfterEach
    public void done() {
        DBManager.rollback(save);
    }

    @AfterAll
    public static void teardown() {
        DBManager.close();
    }

    @Test
    public void testSave() {
        PotionSoin pot1 = new PotionSoin("Magic liquer");
        pot1.setPvRendu(20);
        pot1.setType(0);
        pot1.setPoids(1);

        assertTrue(pot1.save());
    }

    @Test
    public void testUpdate() {
        PotionSoin pot1 = new PotionSoin("Magic liquer");
        pot1.setPvRendu(20);
        pot1.setType(0);
        pot1.setPoids(1);

        pot1.save();

        PotionSoin pot2 = new PotionSoin(pot1.getId());
        pot2.setPvRendu(30);

        pot2.save();

        pot1.get();

        assertTrue(pot1.getPvRendu() == pot2.getPvRendu());

    }

    @Test
    public void testConsomer() {
        Personnage perso = new Personnage("Jimmy", 30, 2);

        PotionSoin pot = new PotionSoin("Life is life", 20);

        pot.consommer(perso);

        assertEquals(50, perso.getPv());

    }
}
