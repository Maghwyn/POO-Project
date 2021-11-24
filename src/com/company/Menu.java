package com.company;

import java.util.Scanner;

public class Menu {
    public void displayMenu(){
        System.out.println("Chose :");
        System.out.println("1. Quit");
        System.out.println("2. Default");
        System.out.println("3. Custom");

        Scanner input = new Scanner(System.in);
        int inputChoice = Integer.parseInt(input.nextLine());
        choseOption(inputChoice);

    }
    public void choseOption(int input){
        switch (input){
            case 1 -> System.out.println("Exit program");
            case 2 -> System.out.println("Default Settings");
            case 3 -> System.out.println("Custom Settings");
            case default -> System.out.println("Error");
        }
    }
}
