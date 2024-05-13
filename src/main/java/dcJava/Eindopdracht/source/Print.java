package dcJava.Eindopdracht.source;

import java.util.HashMap;

public class Print {
    Main main;
    Cave cave;
    Checks checks;

    public Print(Main main, Cave cave) {
        this.main = main;
        this.cave = cave;
    }

    public void help() {
        System.out.println("Help menu");
        System.out.println("e = east.");
        System.out.println("w = west.");
        System.out.println("n = north.");
        System.out.println("s = south.");
        System.out.println("h = this help.");
        System.out.println("m = show map.");
        System.out.println("inv = show your inventory.");
    }
    public void inventoryPrint(HashMap<String, String> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing in your inventory.");
        } else {
            System.out.print("Your inventory: ");
            StringBuilder inventoryString = new StringBuilder();
            for (String item : inventory.keySet()) {
                inventoryString.append(inventory.get(item)).append(", ");
            }

            if (!inventory.isEmpty()) {
                inventoryString.setLength(inventoryString.length() - 2);
            }
            System.out.println(inventoryString.toString());
        }

    }



    public void printMap(GameMap gameMap) {
            System.out.println(gameMap.getName() + " Map:");
            for (int i = 0; i < gameMap.getHeight(); i++) {
                for (int j = 0; j < gameMap.getWidth(); j++) {
                    if (i == main.playerRow && j == main.playerCol) {
                        System.out.print("X ");
                    } else {
                        System.out.print(main.map[i][j] + " ");
                    }
                }
                System.out.println();
            }
    }

}
