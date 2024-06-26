package dcJava.Eindopdracht.source;

import java.util.Random;

public class Checks {
    Main main;
    Cave cave;
    Print print;

    public Checks(Main main, Cave cave, Print print) {
        this.main = main;
        this.cave = cave;
        this.print = print;
    }

    public void checkPicRe() {
        if (main.inventory.containsValue("Pickaxe")) {
            int randomResource = new Random().nextInt(10);
            if (randomResource < 6) {
                System.out.println("You mined up a diamond!");
                main.inventory.put("Resource", "Diamond");
                print.inventoryPrint(main.inventory);
            } else {
                System.out.println("You mined up an iron, but is it useful?");
                main.inventory.put("Resource", "Iron");
                print.inventoryPrint(main.inventory);
            }
        } else {
            System.out.println("How did you get into the cave even?!");
        }
    }

    public boolean checkBoat() {
        return main.inventory.containsKey("Boat");
    }

    public boolean checkTools() {
        return main.inventory.containsKey("Axe");
    }

    public boolean checkWood() {
        return main.inventory.containsKey("Wood");
    }

    public void handleBoatAndCrafter() {
        if (checkWood()) {
            main.inventory.put("Boat", "Boat");
            System.out.println("You got a boat.");
            main.playerRow++;
        } else {
            System.out.println("You do not have the required items to use this.");
            main.incaseError();
        }
    }
}
