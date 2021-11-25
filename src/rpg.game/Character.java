package rpg.game;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class Character {
    String className;
    String name;
    int attackDamages;
    int magicDamages;
    int shield;
    float criticalChance;
    float agility;
    int healthPoints;
    int initiative;
    int indexID;

    List<Character> list = new ArrayList<>();

    public Character() {

    }

    public Character(String name, int attackDamages, int healthPoints, int initiative) {
        this.className     = "Peasant";
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
        attributeRandomIndex();
    }

    public Character(String name, int attackDamages, int specialAbilities, int healthPoints, int initiative){
        if(attackDamages > 5) {
            this.className    = "Warrior";
            this.shield       = specialAbilities;
        } else {
            this.className    = "Mage";
            this.magicDamages = specialAbilities;
        }
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
        attributeRandomIndex();
    }

    public Character(String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        this.className      = "Thief";
        this.name           = name;
        this.attackDamages  = attackDamages;
        this.criticalChance = criticalChance;
        this.healthPoints   = healthPoints;
        this.agility        = agility;
        this.initiative     = initiative;
        attributeRandomIndex();
    }

    private void attributeRandomIndex() {
        this.indexID = (int) Math.floor(Math.random() * 100);
    }

    public String getCharacterName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public int getAttackDamages() {
        return attackDamages;
    }

    public int getShield() {
        return shield;
    }

    public int getEnhancedDamages() {
        int enhancedDamages = attackDamages + magicDamages;
        magicDamages = magicDamages/2;
        if(magicDamages == 0) magicDamages = 1;
        return enhancedDamages;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getInitiative() {
        return initiative;
    }

    public void takeDamages(int damages){
        this.healthPoints -= damages;
    }

    public String toString() {
        return "CharacterID : " + indexID + ", Archetype : Peasant{" + "name = '" + name + '\'' +
                ", AD = " + attackDamages +
                ", HP = " + healthPoints +
                ", Speed = " + initiative +
                "}}";
    }

    public void add_character(Character new_character){
        list.add(new_character);
    }

    void display_list(){
        System.out.println("\nDisplaying the characters list..");
        for (Object character : list){
            System.out.println(character);
        }
    }

    public Character getFighter(int index) {
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
    public Warrior(String name, int attackDamages, int shield, int healthPoints, int initiative) {
        super(name, attackDamages, shield, healthPoints, initiative);
    }
}

class Mage extends Character {
    public Mage(String name, int attackDamages, int magicDamages, int healthPoints, int initiative) {
        super(name, attackDamages, magicDamages, healthPoints, initiative);
    }
}

class Thief extends Character {
    public Thief(String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        super(name, attackDamages, criticalChance, healthPoints, agility, initiative);
    }
}