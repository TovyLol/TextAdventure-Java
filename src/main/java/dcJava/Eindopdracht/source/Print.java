package dcJava.Eindopdracht.source;

import java.util.HashMap;

public class Print {
    Main main;
    Cave cave;

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
                    if (i < gameMap.getHeight() && j < gameMap.getWidth()) {
                        System.out.print(gameMap.getMap()[i][j] + " ");
                    } else {
                        System.out.print("Out ");
                    }
                }
            }
            System.out.println();
        }
    }
    public void printCraftItems() {
        System.out.println(
                "A Boat Costs: 1 log\n" +
                "A Souvenier Costs: 1 Diamond and 1 Iron\n" +
                "A Portable Crafter Costs: 1 Log and 1 Robot Part\n" +
                "A Tool kit Costs: 1 log, 1 pickaxe and 1 gift for me!\n" +
                "A Lamp Costs: 1 drip of oil, 1 Iron and 2 Torches\n" +
                "A Torch Costs: 1 Log, 1 Axe\n"
                );

    }
}
