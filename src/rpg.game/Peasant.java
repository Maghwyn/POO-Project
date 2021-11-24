package rpg.game;

public class Peasant {
    String name;
    int attackDamages;
    int healthPoints;
    int initiative;

    public Peasant(String name, int attackDamages, int healthPoints, int initiative){
        this.name = name;
        this.attackDamages = attackDamages;
        this.healthPoints = healthPoints;
        this.initiative = initiative;
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

    public String toString() {
        return "Peasant{" + "name='" + name + '\'' +
                ", attackDamages=" + attackDamages +
                ", healthPoints=" + healthPoints +
                ", initiative=" + initiative +
                '}';
    }

    public void takeDamages(int damages){
        this.healthPoints -= damages;
        System.out.println(name + " took " + damages + " damages");
    }
}
