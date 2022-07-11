package exorpg.RPG;

public class Arme extends BasicItem implements Equipable{
    int degats = 0;
    float critique = 0;

    public Arme(String nom){
        super(nom);
    }
    public Arme(String nom, int degats, float critique){
        super(nom);
        this.degats = degats;
        this.critique = critique;
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
    public boolean equip(Personnage target) throws PersonnageException{
        if(target.getEquipedWeapon() != null)
            target.ajouterItem(target.getEquipedWeapon());
        if(target.retirerItem(this)){
            target.setEquipedWeapon(this);
            return true;
        }
        return false;
    }
    @Override
    public boolean unequip(Personnage target) throws PersonnageException{
        if(target.getEquipedWeapon() == this){
            target.ajouterItem(this);
            target.setEquipedWeapon(null);
            return true;
        }
        return false;
    }
    
}
