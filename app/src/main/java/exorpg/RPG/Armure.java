package exorpg.RPG;

import java.sql.PreparedStatement;
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
    public boolean get() {
        try {
            ResultSet result = DBManager.execute("select * from armures where id_armure=" + this.id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.defense = result.getInt("defense");
                this.poids = result.getInt("poids");
                this.icon = result.getString("icon");
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
        return false;
    }

    @Override
    public boolean get(int id) {
        try {
            ResultSet result = DBManager.execute("select * from armures where id_armure=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.defense = result.getInt("defense");
                this.poids = result.getInt("poids");
                this.icon = result.getString("icon");
                this.id = id;
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
        return false;
    }

    @Override
    public boolean save() {
        String sql;
        if (this.id != 0) {
            sql = "update armures" +
                    " set nom=?, defense=?, poids=?, icon=?" +
                    " where id_armure= ?";

        } else {

            sql = "insert into armures(nom, defense, poids, icon) " +
                    "values(?,?,?,?)";
        }

        try {
            PreparedStatement pstmt = DBManager.connection.prepareStatement(sql);
            pstmt.setString(1, this.nom);
            pstmt.setInt(2, this.defense);

            pstmt.setInt(3, this.poids);
            pstmt.setString(4, this.icon);
            if (id != 0) {
                pstmt.setInt(5, this.id);
            }
            return pstmt.execute();

        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return false;
        }
    }

}
