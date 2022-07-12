package exorpg.RPG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exorpg.utils.DBManager;

public class Arme extends BasicItem implements Equipable {
    public int degats = 0;
    public float critique = 0;
    public int id = 0;

    public Arme(String nom) {
        super(nom);
    }

    public Arme(String nom, int degats, float critique) {
        super(nom);
        this.degats = degats;
        this.critique = critique;
    }

    public Arme(int id) {
        super("");
        try {
            ResultSet result = DBManager.execute("select * from armes where id_arme=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.degats = result.getInt("degats");
                this.critique = result.getFloat("critique");
                this.poids = result.getInt("poids");
                this.icon = result.getString("icon");
                this.id = id;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
    }

    public boolean get(int id) {
        try {
            ResultSet result = DBManager.execute("select * from armes where id_arme=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.degats = result.getInt("degats");
                this.critique = result.getFloat("critique");
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

    // public boolean save() {
    // String sql;
    // if (this.id != 0) {
    // sql = "update armes" +
    // " set nom= '" + this.nom + "'," +
    // " degats= " + this.degats + ", " +
    // " critique= " + this.critique + ", " +
    // " poids= " + this.poids + ", " +
    // " icon= '" + this.icon + "'" +
    // " where id_arme= " + id;
    // } else {
    // sql = "insert into armes(nom, degats, critique,poids, icon) " +
    // "values(" +
    // "'" + this.nom + "'" +
    // "'" + this.degats + "'" +
    // "'" + this.critique + "'" +
    // "'" + this.poids + "'" +
    // "'" + this.icon + "')";

    // }
    // return (DBManager.executeUpdate(sql) > 0);
    // }

    public boolean save() {
        String sql;
        if (this.id != 0) {
            sql = "update armes" +
                    " set nom=?, degats=?, critique=?, poids=?, icon=?" +
                    " where id_arme= ?";

        } else {

            sql = "insert into armes(nom, degats, critique,poids, icon) " +
                    "values(?,?,?,?,?)";
        }

        try {
            PreparedStatement pstmt = DBManager.connection.prepareStatement(sql);
            pstmt.setString(1, this.nom);
            pstmt.setInt(2, this.degats);
            pstmt.setFloat(3, this.critique);
            pstmt.setInt(4, this.poids);
            pstmt.setString(5, this.icon);
            if (id != 0) {
                pstmt.setInt(6, this.id);
            }
            return pstmt.execute();

        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return false;
        }

    }

    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public float getCritique() {
        return critique;
    }

    public void setCritique(float critique) {
        this.critique = critique;
    }

    @Override
    public boolean equip(Personnage target) throws PersonnageException {
        if (target.getEquipedWeapon() != null)
            target.ajouterItem(target.getEquipedWeapon());
        if (target.retirerItem(this)) {
            target.setEquipedWeapon(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Personnage target) throws PersonnageException {
        if (target.getEquipedWeapon() == this) {
            target.ajouterItem(this);
            target.setEquipedWeapon(null);
            return true;
        }
        return false;
    }

}
