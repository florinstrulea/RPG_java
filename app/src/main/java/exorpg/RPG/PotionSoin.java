package exorpg.RPG;

import java.sql.ResultSet;
import java.sql.SQLException;

import exorpg.utils.DBManager;

public class PotionSoin extends BasicItem implements Consommable {
    protected int pvRendu = 0;

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

    @Override
    public void consommer(Personnage cible) {
        cible.setPv(cible.getPv() + pvRendu);
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
