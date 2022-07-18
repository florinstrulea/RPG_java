package exorpg;

import java.sql.SQLException;

import exorpg.RPG.Armure;
import exorpg.RPG.Personnage;

public class ArmureTest {

    Personnage perso1 = new Personnage(2);
    Armure armure1 = new Armure(2);

    public void testEquip() {
        armure1.equip(perso1);
    }
}
