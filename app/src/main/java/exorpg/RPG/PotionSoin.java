package exorpg.RPG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exorpg.utils.DBManager;

public class PotionSoin extends BasicItem implements Consommable {
    protected int pvRendu = 0;
    protected int type = 0;

    public int id;

    public PotionSoin(String nom) {
        super(nom);
    }

    public PotionSoin(int id) {
        super("");
        try {
            ResultSet result = DBManager.execute("select * from armes where id_arme=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.pvRendu = result.getInt("defense");
                this.poids = result.getInt("poids");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
    }

    public int getPvRendu() {
        return pvRendu;
    }

    public void setPvRendu(int pvRendu) {
        this.pvRendu = pvRendu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void consommer(Personnage cible) {
        cible.setPv(cible.getPv() + pvRendu);
    }

    @Override
    public boolean get() {
        try {
            ResultSet result = DBManager.execute("select * from potions where id_potion=" + this.id);
            if (result.next()) {
                this.type = result.getInt("type");
                this.nom = result.getString("nom");
                this.pvRendu = result.getInt("valeur");
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
            ResultSet result = DBManager.execute("select * from potions where id_potion=" + id);
            if (result.next()) {
                this.type = result.getInt("type");
                this.nom = result.getString("nom");
                this.pvRendu = result.getInt("valeur");
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
    public boolean save(int id) {
        String sql;
        if (this.id != 0) {
            sql = "update armures" +
                    " set type=?, nom=?, valeur=?, poids=?, icon=?" +
                    " where id_potion= ?";

        } else {

            sql = "insert into armures(type,nom, valeur, poids, icon) " +
                    "values(?,?,?,?,?)";
        }

        try {
            PreparedStatement pstmt = DBManager.connection.prepareStatement(sql);
            pstmt.setInt(1, this.type);
            pstmt.setString(2, this.nom);
            pstmt.setInt(3, this.pvRendu);

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

}
