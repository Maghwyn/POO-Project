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
}

class Peasant extends Character{
    public Peasant(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
    }
}