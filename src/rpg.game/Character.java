package rpg.game;

import java.util.ArrayList;
import java.util.List;

public class Character {
    String name;
    int attackDamages;
    int healthPoints;
    int initiative;
    int indexID;

    List<Character> list = new ArrayList<>();

    public Character() {

    }

    public Character(String name, int attackDamages, int healthPoints, int initiative){
        this.name = name;
        this.attackDamages = attackDamages;
        this.healthPoints = healthPoints;
        this.initiative = initiative;
        attributeRandomIndex();
    }

    private void attributeRandomIndex() {
        //        int verify = list.indexOf(characterID);
//        while(verify != -1) {
//            characterID = (int) Math.floor(Math.random() * 100);
//            verify = list.indexOf(characterID);
//        }
        this.indexID = (int) Math.floor(Math.random() * 100);
    }

    public int getAttackDamages() {
        return attackDamages;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getInitiative() {
        return initiative;
    }

    public void takeDamages(int damages){
        this.healthPoints -= damages;
        System.out.println(name + " took " + damages + " damages");
    }

    public String toString() {
        return "{ " + indexID + ": Peasant{" + "name = '" + name + '\'' +
                ", AD = " + attackDamages +
                ", HP = " + healthPoints +
                ", Speed = " + initiative +
                "}}";
    }

    public void add_character(Character new_character){
        list.add(new_character);
    }

    void display_list(){
        for (Object character : list){
            System.out.println(character);
        }
    }

    void display_info(int index) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == index) {
                System.out.println(character);
            }
        }
    }

    public Character choseWhoWillFight(int index) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == index) {
                return character;
            }
        }
        return null;
    }

    void removeCharacter(int characterID) {
        list.remove(characterID);
    }

    int doesCharacterExist(int characterID) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == characterID) {
                return list.indexOf(character);
            }
        }
        return -1;
    }

}

class Peasant extends Character {
    public Peasant(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
    }
}

class Warrior extends Character {
    public Warrior(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
    }

    void Bouclier() {
        //SO basically, it's a barrier where if the number of the shield is hight enought then it means
        //The attack is nullified, might want to check that one once the attack is launched or something.
        //Rather than doing it manually inside the attack.
    }
}

class Mage extends Character {
    int magicAttack;
    public Mage(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
    }

    void Magic() {
        //Somehow have to add some magic damage to it.
    }
}

class Thief extends Character {
    public Thief(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
    }

    void miss() {
        //For this much probability, cancel an attack.
        //Return a miss or nothing, this will return the attack to null basically.
    }

    void critical() {
        //For this much probability, multiply the damage by 2.
        //Return a buffed AD for this turn only.
        //You might want to add a CD on each crit.
    }
}