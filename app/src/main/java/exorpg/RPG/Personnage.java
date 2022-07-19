package exorpg.RPG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import exorpg.utils.DBManager;
import exorpg.utils.Model;

public class Personnage extends Model {

    protected static Arme poings = new Arme("Poings", 1, 0.01f);
    protected static Armure aucune = new Armure("Aucune", 0);

    protected String nom;
    protected int pv = 50;
    protected int force = 1;
    protected int type;
    protected int pvMax;

    protected ArrayList<BasicItem> inventaire = new ArrayList<BasicItem>();

    protected Armure armor = aucune;
    protected Arme equipedWeapon = poings;

    protected int weaponId;
    protected int armorId;

    public Personnage() {

    }

    public Personnage(int id) {
        try {
            ResultSet result = DBManager.execute("select * from personnages where id_personnage=" + id);
            if (result.next()) {
                this.nom = result.getString("nom");
                this.pv = result.getInt("pv");
                this.pvMax = result.getInt("pvMax");
                this.force = result.getInt("force");
                this.weaponId = result.getInt("id_arme");
                this.armorId = result.getInt("id_armure");
                this.id = id;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());

        }
    }

    public Personnage(String nom) {
        this.nom = nom;

    }

    public ArrayList<BasicItem> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<BasicItem> inventaire) {
        this.inventaire = inventaire;
    }

    public Personnage(String nom, int pv, int force) {
        this.nom = nom;
        this.pv = pv;
        this.force = force;
    }

    // #region GETSET
    public Armure getArmor() {
        return armor;
    }

    public void setArmor(Armure armor) {
        this.armor = armor;
    }

    public Arme getEquipedWeapon() {
        return equipedWeapon;
    }

    public void setEquipedWeapon(Arme equipedWeapon) {
        //
        // ...
        //
        // equipedWeapon.
        // if (equipedWeapon == null) {
        // throw new PersonnageException("Hey !");
        // } else
        this.equipedWeapon = equipedWeapon;
    }

    public String toString() {
        return this.nom + "(" + this.pv + ")";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPvMax() {
        return pvMax;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }

    public int getArmorId() {
        return armorId;
    }

    public void setArmorId(int armorId) {
        this.armorId = armorId;
    }

    // #endregion
    public float attaquer(Personnage autre) {
        int degats = equipedWeapon.getDegats();
        if (Math.random() < (equipedWeapon.getCritique() + this.force / 100)) {
            degats *= 2;
        }

        degats *= (1 + 0.1f * this.force);
        System.out.println(this.nom + " utilise " + equipedWeapon.getNom() + " et tente d'infliger " + degats + " a "
                + autre.getNom());

        autre.prendreCoup(degats);

        return degats;
    }

    public float prendreCoup(float degats) {
        degats *= (1 - (this.armor.getDefense() / 100.0f));
        this.pv -= degats;
        System.out.println(this.nom + " reçoit " + degats + " ! Il lui reste " + this.pv + " points de vie !");
        return degats;
    }

    public boolean ajouterItem(BasicItem item) {
        return inventaire.add(item);
    }

    public boolean retirerItem(BasicItem item) {
        return inventaire.remove(item);
    }

    public BasicItem retirerItem(int index) {
        return inventaire.remove(index);
    }

    /*
     * Version alternative en mettant la méthode equip sur Personnage
     */
    public boolean equip(Arme weapon) throws PersonnageException {
        if (this.getEquipedWeapon() != null && this.getEquipedWeapon() != poings)
            this.ajouterItem(this.getEquipedWeapon());
        if (this.retirerItem(weapon)) {
            this.setEquipedWeapon(weapon);
            return true;
        } else
            this.setEquipedWeapon(poings);
        return false;
    }

    public boolean equip(Armure armor) throws PersonnageException {
        if (this.getArmor() != null && this.getArmor() != aucune)
            this.ajouterItem(this.getArmor());
        if (this.retirerItem(armor)) {
            this.setArmor(armor);
            return true;
        } else
            this.setArmor(aucune);
        return false;
    }

    @Override
    public boolean get() {
        try {
            ResultSet result = DBManager.execute("SELECT * FROM personnages WHERE id_personnage = " + this.id);
            if (result.next()) {
                this.type = result.getInt("type");
                this.nom = result.getString("nom");
                this.pv = result.getInt("pv");
                this.pvMax = result.getInt("pvMax");
                this.force = result.getInt("force");
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

    @Override
    public boolean get(int id) {
        try {
            ResultSet result = DBManager.execute("select * from personnages where id_personnage=" + id);
            if (result.next()) {
                this.type = result.getInt("type");
                this.nom = result.getString("nom");
                this.pv = result.getInt("pv");
                this.pvMax = result.getInt("pvMax");
                this.force = result.getInt("force");
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
        if (this.id != 0)
            sql = "UPDATE personnages " +
                    "SET type = ?, nom = ?, pv=?, pvMax=?, `force`=?, id_armure=? , id_arme=?" +
                    " WHERE id_personnage = ?";
        else
            sql = "INSERT INTO personnages (type, nom, pv, pvMax, `force`, id_armure, id_arme)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBManager.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.type);
            stmt.setString(2, this.nom);
            stmt.setInt(3, this.pv);
            stmt.setInt(4, this.pvMax);
            stmt.setInt(5, this.force);
            stmt.setInt(6, this.armorId);
            stmt.setInt(7, this.weaponId);
            if (id != 0)
                stmt.setInt(8, this.id);

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

}
