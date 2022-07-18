package exorpg.RPG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exorpg.utils.DBManager;

public class Arme extends BasicItem implements Equipable {
    public int degats = 0;
    public float critique = 0;

    public Arme() {
        super("");
    }

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

    public boolean get() {
        try {
            ResultSet resultat = DBManager.execute("SELECT * FROM armes WHERE id_arme = " + this.id);
            if (resultat.next()) {
                this.nom = resultat.getString("nom");
                this.degats = resultat.getInt("degats");
                this.critique = resultat.getFloat("critique");
                this.poids = resultat.getInt("poids");
                this.icon = resultat.getString("icon");
                return true;
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    public boolean save() {
        String sql;
        if (this.id != 0)
            sql = "UPDATE armes " +
                    "SET nom = ?, degats = ?, critique = ?, poids = ?, icon = ?" +
                    "WHERE id_arme = ?";
        else
            sql = "INSERT INTO armes (nom, degats, critique, poids, icon)" +
                    "VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBManager.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.degats);
            stmt.setFloat(3, this.critique);
            stmt.setInt(4, this.poids);
            stmt.setString(5, this.icon);
            if (id != 0)
                stmt.setInt(6, this.id);

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (this.id == 0 && keys.next()) {
                this.id = keys.getInt(1);
                return true;
            } else if (this.id != 0)
                return true;
            else
                return false;

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

    @Override
    public boolean save(int id) {
        // TODO Auto-generated method stub
        return false;
    }

}
