package rpg.game;
import java.util.Scanner;



public class Game {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.displayMenu();

        Peasant jeanLuc = new Peasant("Jean-Luc", 2, 10, 4);
        System.out.println(jeanLuc.name + " fall from a tree.");
        jeanLuc.takeDamages(3);
        int hp = jeanLuc.getHealthPoints();
        System.out.println(jeanLuc.name + " is left with " + hp + " hp");
    }
}