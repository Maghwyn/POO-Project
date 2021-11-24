package rpg.game;


public class Game {
    public static void main(String[] args) {
        Menu menu = new Menu();

        for (int index = 0; index < 10; index++) {
            menu.displayMenu();
        }


    }
}