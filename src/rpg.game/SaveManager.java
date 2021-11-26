package rpg.game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveManager {
    public static void saveForEach(Character allCharacter) {
        List<String> jsonContent = new ArrayList<>();
        String os_path = "JSON.txt";
        File file = new File(os_path);

        for(Character character : allCharacter.list) {
            jsonContent.add(character.convertToJSON());
        }

        try {
            if (!file.exists()) file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(jsonContent));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error : Couldn't fetch the file from the path : '"
                    + os_path + "'");
        }
    }

    public static String readFile() {
        String JSON = null;
        String os_path = "JSON.txt";
        File file = new File(os_path);
        try {
            Scanner sc = new Scanner(file);
            if(!sc.hasNextLine()) return "NotFound";
            JSON = sc.nextLine();
            if(!JSON.matches("(.*)=>(.*)")) return "Invalid";
        } catch (IOException e) {
            System.out.println("Error : Couldn't fetch the file from the path : '"
                    + os_path + "'");
        }
        return JSON;
    }

    public static void processingExtraction(String JSON, Character character) {
        character.list.clear();
        String[] extractedJSON = JSON.substring(1, JSON.length() - 1).split(", ");
        for (String item : extractedJSON) {
            String[] id_stats = item.split("=>");
            System.out.println("Importing \"" + id_stats[0] + "\" ...");
            String[] id_class = id_stats[0].split(":");
            String[] stats = id_stats[1].split(",");

            int IndexID = Integer.parseInt(id_class[0]);
            String className = id_class[1];
            String charaName = null;
            int AD = 0, HP = 0, speed = 0, AP = 0, SD = 0;
            float CD = 0, dodge = 0;


            for (String items : stats) {
                String[] name_stat = items.split(":");
                switch (name_stat[0]) {
                    case "name" -> charaName = name_stat[1];
                    case "attackDamages" -> AD = Integer.parseInt(name_stat[1]);
                    case "healthPoints" -> HP  = Integer.parseInt(name_stat[1]);
                    case "initiative" -> speed = Integer.parseInt(name_stat[1]);
                    case "magicDamages" -> AP  = Integer.parseInt(name_stat[1]);
                    case "shield" -> SD        = Integer.parseInt(name_stat[1]);
                    case "critChance" -> CD    = Float.parseFloat(name_stat[1]);
                    case "agility" -> dodge    = Float.parseFloat(name_stat[1]);
                }
            }

            switch (className) {
                case "Peasant" ->     character.add_character(new Peasant(IndexID, charaName, AD, HP, speed));
                case "Warrior" ->     character.add_character(new Warrior(IndexID, charaName, AD, SD, HP, speed));
                case "Mage" ->        character.add_character(new Mage(IndexID, charaName, AD, AP, HP, speed));
                case "Thief" ->       character.add_character(new Thief(IndexID, charaName, AD, CD, HP, dodge, speed));
                case "WarriorMage" -> character.add_character(new WarriorMage(IndexID, charaName, AD, AP, SD, HP, speed));
            }
        }
    }
}
