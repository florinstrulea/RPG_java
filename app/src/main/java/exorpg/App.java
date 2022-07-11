/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package exorpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.common.base.Optional;
import java.sql.Statement;

import exorpg.RPG.*;
import exorpg.utils.Vector2;

import exorpg.utils.DBManager;

public class App {

    static BasicItem[] availableItems = new BasicItem[5];
    static Armure[] availableArmors = new Armure[10];
    static Arme[] availableWeapons = new Arme[10];
    static Personnage[] monstres = new Personnage[10];
    static Scanner scan;
    static Statement statement = null;
    static String sql = null;

    public static void main(String[] args) {
        DBManager.init();

        scan = new Scanner(System.in);
        createItems();
        generateDungeon();

        System.out.println(availableArmors);
        Personnage paul = new Personnage("Jean-Paul");

        paul.setPv(100);
        paul.setForce(10);

        System.out.println("Vous incarnez le héro suivant " + paul);
        System.out.println("Il a " + paul.getForce() + " Force");

        Arme epee = new Arme("Epee rouillée", 10, 0.2f);
        Armure carton = new Armure("Cartons sctochés", 10);

        paul.setArmor(carton);
        try {
            paul.setEquipedWeapon(epee);
        } catch (PersonnageException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }

        paul.ajouterItem(availableItems[0]);
        paul.ajouterItem(availableItems[0]);
        paul.ajouterItem(availableItems[0]);

        for (Personnage personnage : monstres) {
            System.out.println("Vous combattez " + personnage);
            combattre(paul, personnage);
        }
        scan.close();
        DBManager.close();
    }

    public static void combattre(Personnage p1, Personnage p2) {
        int i = 0;
        while (p1.getPv() > 0 && p2.getPv() > 0) {
            if (i % 2 == 0) {
                System.out.println("Choisissez la prochaine action : ");
                System.out.println("1 : Attaquer");
                System.out.println("2 : Utliser une potion");
                int decision = scan.nextInt();
                switch (decision) {
                    case 1:
                        p1.attaquer(p2);
                        break;
                    case 2:
                        int j = 0;
                        int nbPotions = 0;
                        for (BasicItem item : p1.getInventaire()) {
                            if (item instanceof PotionSoin) {
                                System.out.println(j + " : " + item.getNom());
                                nbPotions++;
                            }
                            j++;
                        }
                        // TODO gérer ça mieux
                        if (nbPotions == 0)
                            p1.attaquer(p2);
                        else {
                            System.out.println("Quelle potion souhaitez vous utiliser ?");
                            int potionAUtiliser = scan.nextInt();
                            while (!(p1.getInventaire().get(potionAUtiliser) instanceof Consommable))
                                potionAUtiliser = scan.nextInt();

                            PotionSoin potion = (PotionSoin) (p1.getInventaire().get(potionAUtiliser));
                            potion.consommer(p1);
                            p1.retirerItem(potion);
                        }
                        break;
                    default:
                        p1.attaquer(p2);
                }
            } else
                p2.attaquer(p1);
            i++;
        }

        System.out.println("Le vainqueur est : " + ((p1.getPv() > 0) ? p1 : p2));
    }

    public static BasicItem[] initInventaire() {
        BasicItem[] monInventaire = new BasicItem[5];
        return monInventaire;
    }

    public static void generateDungeon() {
        String[] noms = new String[] { "Gobelin", "Orc", "Troll", "Elfe", "Fantôme" };
        String[] adj = new String[] { "peureux", "prétentieux", "stupide", "passionné", "pessimiste", "idéaliste",
                "gigantesque", "courageux", "âgé", "jaune", "violet", "vert", "endurant", "prévoyant", "vigilant",
                "volontaire", "communiste", "de droite", "en marche", "écolo" };

        for (int i = 0; i < monstres.length; i++) {
            monstres[i] = new Personnage(
                    noms[(int) (Math.random() * noms.length)] + " " + adj[(int) (Math.random() * adj.length)],
                    (int) (i + 1 * Math.random() * 30), (int) (i + 1 * Math.random()));
            System.out.println("Monstre : " + monstres[i]);
            // try{

            // Optional<Arme> test = null;
            // monstres[i].setEquipedWeapon(test);
            // }
            // catch(PersonnageException e){
            // System.out.println(e.getMessage());
            // e.printStackTrace();
            // }
            monstres[i].setArmor(availableArmors[(int) (Math.random() * i)]);
        }
    }

    public static void createItems() {
        for (int i = 0; i < availableItems.length; i++) {
            availableItems[i] = new PotionSoin("Potion " + (i + 1) * 5 + "PV");
            ((PotionSoin) availableItems[i]).setPvRendu((i + 1) * 5);

            // sql = "insert into potions (type, nom, valeur, poids) values"
            // + "(0,'" + availableItems[i].getNom() + "',"
            // + ((PotionSoin) availableItems[i]).getPvRendu() + ","
            // + availableItems[i].getPoids() + ");";
            // DBManager.executeUpdate(sql);

        }
        String[] types = new String[] { "Papier", "Bois", "Cuivre", "Fer", "Or", "Diamand", "Flammes", "Glace", "Ether",
                "Divine" };
        for (int i = 0; i < availableArmors.length; i++) {
            availableArmors[i] = new Armure("Armure de " + types[i]);
            availableArmors[i].setDefense((int) (Math.random() * 5 * (i + 1)));
            // sql = "insert into armures (nom, defense, poids) values" + "('" +
            // availableArmors[i].getNom() + "',"
            // + availableArmors[i].getDefense() + "," + availableArmors[i].getPoids() +
            // ")";
            // DBManager.executeUpdate(sql);
        }
        for (int i = 0; i < availableWeapons.length; i++) {
            availableWeapons[i] = new Arme("Epée de " + types[i]);
            availableWeapons[i].setDegats((int) (Math.random() * 5 * (i + 1)));
            availableWeapons[i].setCritique((float) Math.random() * 5 * (i + 1) / 100);
            sql = "insert into armes (nom, degats, critique, poids) values" + "('" + availableWeapons[i].getNom() + "',"
                    + availableWeapons[i].getDegats() + "," + availableWeapons[i].getCritique() + ","
                    + availableWeapons[i].getPoids() + ")";
            DBManager.executeUpdate(sql);
        }
    }
}
