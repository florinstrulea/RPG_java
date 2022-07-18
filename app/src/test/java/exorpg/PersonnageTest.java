package exorpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import exorpg.RPG.Arme;
import exorpg.RPG.Armure;
import exorpg.RPG.BasicItem;
import exorpg.RPG.Personnage;

public class PersonnageTest {
    protected static Arme poings = new Arme("Poings", 10, 0.01f);
    protected static Armure aucune = new Armure("Aucune", 0);

    protected Armure armor = aucune;
    protected Arme equipedWeapon = poings;

    protected ArrayList<BasicItem> inventaire = new ArrayList<BasicItem>();

    @Test
    public void getHit() {

        Personnage perso = new Personnage("John", 100, 30);
        perso.setArmor(armor);
        perso.setEquipedWeapon(equipedWeapon);

        perso.prendreCoup(10);

        assertTrue(perso.getPv() == 90);
    }

    @Test
    public void attack() {
        Personnage perso = new Personnage("John", 100, 30);
        Personnage villain = new Personnage("Tom", 100, 30);
        perso.setArmor(armor);
        perso.setEquipedWeapon(equipedWeapon);
        villain.setArmor(armor);
        villain.setEquipedWeapon(equipedWeapon);

        perso.attaquer(villain);
        assertTrue(villain.getPv() == 60);
    }

    @Test
    public void equipTest() {

    }

}
