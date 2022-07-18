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

    public void saveTest() {
        Personnage perso1 = new Personnage();

        perso1.setType(1);
        perso1.setNom("Michael");
        perso1.setPv(60);
        perso1.setPvMax(60);
        perso1.setForce(5);
        perso1.setWeaponId(2);
        perso1.setArmorId(1);

        assertTrue(perso1.save());
    }

    public void updateTest() {
        Personnage perso1 = new Personnage();

        perso1.setType(1);
        perso1.setNom("Michael");
        perso1.setPv(60);
        perso1.setPvMax(60);
        perso1.setForce(5);
        perso1.setWeaponId(2);
        perso1.setArmorId(1);

        Personnage perso2 = new Personnage(perso1.getId());
        perso2.setNom("Tommy");

        perso2.save();

        perso1.get();

        assertTrue(perso1.getNom() == perso2.getNom());
    }

}
