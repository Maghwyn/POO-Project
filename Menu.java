package com.company;

class runMenu{
    System.out.println("CHOOSE");

    System.out.println("1. Quit");
    System.out.println("2. Default");
    System.out.println("3. Custom");

    Scanner q = new Scanner(System.in);
    System.out.println("Quit");
    int quit = q.nextLine();

    Scanner d = new Scanner(System.in);
    System.out.println("Choose default");
    int defaut = d.nextLine();

    Scanner c = new Scanner(System.in);
    System.out.println("Choose Custom");
    int custom = d.nextLine();

}
