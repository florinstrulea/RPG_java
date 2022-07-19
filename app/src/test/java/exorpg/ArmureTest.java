package exorpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import exorpg.RPG.Armure;
import exorpg.RPG.BasicItem;
import exorpg.RPG.Personnage;
import exorpg.utils.DBManager;

public class ArmureTest {
    protected ArrayList<BasicItem> inventaire = new ArrayList<BasicItem>();

    @Test
    public void testEquip() {
        DBManager.init();
        Personnage perso1 = new Personnage(2);
        perso1.setInventaire(inventaire);
        Armure armure1 = new Armure(2);
        inventaire.add(armure1);
        armure1.equip(perso1);
        assertTrue((perso1.getArmor().getNom()).equals(armure1.getNom()));
        DBManager.close();
    }

    public void testUnequip() {
        DBManager.init();
        Personnage perso1 = new Personnage(2);
        perso1.setInventaire(inventaire);
        Armure armure1 = new Armure(2);
        inventaire.add(armure1);
        armure1.equip(perso1);
        armure1.unequip(perso1);
        assertTrue(perso1.getArmor().getNom() == null);
        DBManager.close();

    }
}