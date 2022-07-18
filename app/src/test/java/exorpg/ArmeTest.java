package exorpg;

import java.sql.Savepoint;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import exorpg.RPG.Arme;
import exorpg.utils.DBManager;

public class ArmeTest {
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
    public static void tearDown() {
        DBManager.close();
    }

    @Test
    public void saveTest() {
        Arme arme1 = new Arme();

        arme1.setNom("I kill you");
        arme1.setCritique(0.3f);
        arme1.setNom("");
        arme1.setDegats(10);
        arme1.setPoids(1);

        assertTrue(arme1.save());
    }

    @Test
    public void updateTest() {
        Arme arme1 = new Arme();

        arme1.setNom("I kill you !");
        arme1.setCritique(0.2f);
        arme1.setIcon("");
        arme1.setDegats(10);
        arme1.setPoids(1);

        arme1.save();

        Arme arme2 = new Arme(arme1.getId());
        arme2.setDegats(5);
        arme2.save();

        arme1.get();

        assertTrue(arme1.getDegats() == arme2.getDegats());
    }

}
