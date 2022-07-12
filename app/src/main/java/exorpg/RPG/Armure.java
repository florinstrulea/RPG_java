package exorpg.RPG;

import java.sql.ResultSet;
import java.sql.SQLException;

import exorpg.utils.DBManager;

public class Armure extends BasicItem implements Equipable {
    protected int defense = 1;
    public int id;

    public Armure(String nom) {
        super(nom);
    }

    public Armure(String nom, int defense) {
        super(nom);
        this.defense = defense;
    }

    public Armure(int id) {
        super("");
        try {
            ResultSet result = DBManager.execute("select * from armures where id_arme=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.defense = result.getInt("defense");
                this.poids = result.getInt("poids");
                this.id = id;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public boolean equip(Personnage target) {
        if (target.getArmor() != null)
            target.ajouterItem(target.getArmor());
        if (target.retirerItem(this)) {
            target.setArmor(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Personnage target) {
        if (target.getArmor() == this) {
            target.ajouterItem(this);
            target.setArmor(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean get(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean save(int id) {
        // TODO Auto-generated method stub
        return false;
    }
}
