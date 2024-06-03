package dcJava.Eindopdracht.source;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    final static int WATER = 1, GRASS = 2, ROCK = 3, TREE = 4, CHEST = 5, CAVE = 6, CRAFTER = 12;
    int[][] map = {
            {1, 12, 1, 1, 1, 2},
            {2, 2, 2, 2, 4, 2},
            {1, 2, 2, 2, 2, 2},
            {1, 2, 2, 2, 2, 1},
            {1, 3, 5, 2, 1, 1},
            {1, 1, 1, 1, 6, 1}
    };
    GameMap gameMap = new GameMap(map, "map");
    HashMap<String, String> inventory = new HashMap<>();
    int playerRow, playerCol;
    final static String BORDER = "====================";
    Cave cave;
    Checks checks;
    Print print;

    public Main() {
        this.cave = new Cave(this, checks);
        this.checks = new Checks(this, this.cave, this.print);
        this.print = new Print(this, this.cave);
    }

    public static void main(String[] args) {
        Main ta = new Main();
        ta.init();
        ta.mainLoop();
    }

    public void init() {
        System.out.println("Welcome to the game.");
        System.out.println("\n" +
                "████████╗███████╗██╗░░██╗████████╗  ░█████╗░██████╗░██╗░░░██╗███████╗███╗░░██╗████████╗██╗░░░██╗██████╗░███████╗\n" +
                "╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝  ██╔══██╗██╔══██╗██║░░░██║██╔════╝████╗░██║╚══██╔══╝██║░░░██║██╔══██╗██╔════╝\n" +
                "░░░██║░░░█████╗░░░╚███╔╝░░░░██║░░░  ███████║██║░░██║╚██╗░██╔╝█████╗░░██╔██╗██║░░░██║░░░██║░░░██║██████╔╝█████╗░░\n" +
                "░░░██║░░░██╔══╝░░░██╔██╗░░░░██║░░░  ██╔══██║██║░░██║░╚████╔╝░██╔══╝░░██║╚████║░░░██║░░░██║░░░██║██╔══██╗██╔══╝░░\n" +
                "░░░██║░░░███████╗██╔╝╚██╗░░░██║░░░  ██║░░██║██████╔╝░░╚██╔╝░░███████╗██║░╚███║░░░██║░░░╚██████╔╝██║░░██║███████╗\n" +
                "░░░╚═╝░░░╚══════╝╚═╝░░╚═╝░░░╚═╝░░░  ╚═╝░░╚═╝╚═════╝░░░░╚═╝░░░╚══════╝╚═╝░░╚══╝░░░╚═╝░░░░╚═════╝░╚═╝░░╚═╝╚══════╝");
        System.out.println("When youre ready put in a command");
        print.help();
        playerRow = gameMap.getHeight() - 1;
        playerCol = gameMap.getWidth() / 2;
    }

    public void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("stop")) {
            System.out.println(">");
            input = scanner.nextLine();
            executeInput(input);

        }
        scanner.close();
    }

    public void describeRoom() {
        String reply = "You are ";
        if (!cave.isinCave) {
            int currentTile = map[playerRow][playerCol];
            if (currentTile == GRASS) {
                reply += "in the grass, ew so many bugs!!";
            } else if (currentTile == ROCK) {
                reply += "on a rock.";
            } else if (currentTile == TREE) {
                reply += "in front of a tree, do you wanna interact? YES/NO";
            } else if (currentTile == CHEST) {
                reply += "in front of a chest. Do you want to open it? YES/NO";
            } else if (currentTile == CAVE) {
                reply += "in front of a massive cave! Do you want to enter? YES/NO";
            } else if (currentTile == WATER) {
                reply += "on water";
            } else if (currentTile == CRAFTER) {
                System.out.println("Welcome to the Crafter");
                System.out.println("You may craft items here!");
                System.out.println("A full list of items will be listed below!");
                print.printCraftItems();
            }
        } else {
            int caveTile = cave.caveMap.getMap()[playerRow][playerCol];
            if (caveTile == cave.EXIT) {
                reply = reply + "in front of the exit of the cave, but do you want to exit? YES/NO";
            } else if (caveTile == cave.WALL) {
                reply = reply + "in front of a steep wall, you may want to turn around.";
            } else if (caveTile == cave.RESOURCE) {
                reply = reply + "looking at a valuable resource but what is it? Mine it to find out! YES/NO";
            } else if (caveTile == cave.PIT) {
                System.out.println("You fell in the pit and died :(");
                restartGame();
            } else if (caveTile == cave.HALLWAY) {
                reply = reply + "in a hallway, might wanna keep walking forward.";
            }
        }
        System.out.println(BORDER);
        System.out.println(reply);
    }

    public void executeInput(String input) {
        int currentTile = map[playerRow][playerCol];
        if (input.equalsIgnoreCase("yes")) {
            if (currentTile == CHEST) {
                System.out.println("Chest opened!");
                inventory.put("Pickaxe", "Pickaxe");
                inventory.put("Axe", "Axe");
                print.inventoryPrint(inventory);
            } else if (currentTile == CAVE) {
                if (checks.checkTools()) {
                    cave.enterCave();
                } else {
                    System.out.println("You forgot to bring your trusty pickaxe!");
                    incaseError();
                }
            } else if (currentTile == TREE) {
                if (checks.checkTools()) {
                    inventory.put("Wood", "Wood");
                    System.out.println("You violently cut down that tree, and now you have wood!");
                    incaseError();
                } else {
                    System.out.println("You do not have the required items to use this.");
                }
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.RESOURCE) {
                System.out.println("You mined the resource and found something valuable!");
                endGame();
            }else {
                System.out.println("This does not comply to your current situation");
            }
        } else if (input.equalsIgnoreCase("no")) {
            if (currentTile == CHEST) {
                System.out.println("You left the chest behind.");
                incaseError();
            } else if (currentTile == CAVE) {
                System.out.println("You turned around not looking back.");
                incaseError();
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.RESOURCE) {
                incaseError();
            } else if (currentTile == TREE) {
                incaseError();
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.EXIT) {
                cave.exitCave();
            }
        } else if  (currentTile == CRAFTER) {
            if (input.equalsIgnoreCase("boat")) {
                handleBoatAndCrafter();
            } else {
                System.out.println("You do not have the required items GET OUT!");
                handleMovement(input);
            }
        }else {
            handleMovement(input);
        }
    }

    public void handleMovement(String input) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (input) {
            case "n":
                if (playerRow > 0) {
                    newRow--;
                }
                break;
            case "s":
                if (playerRow < gameMap.getHeight() - 1) {
                    newRow++;
                }
                break;
            case "e":
                if (playerCol < gameMap.getWidth() - 1) {
                    newCol++;
                }
                break;
            case "w":
                if (playerCol > 0) {
                    newCol--;
                }
                break;
            case "h":
                print.help();
                return;
            case "m":
                decideMap();
                return;
            case "inv":
                print.inventoryPrint(inventory);
                return;
            case "restart":
                restartGame();
                return;
            case "cavemap":
                print.printMap(cave.caveMap);
                return;
            default:
                System.out.println("What do you mean?");
                return;
        }

        if (map[newRow][newCol] == WATER && !inventory.containsKey("Boat")) {
            System.out.println("You cannot move onto the water without a boat!");
        } else {
            playerRow = newRow;
            playerCol = newCol;
            describeRoom();
        }
    }

    public void handleBoatAndCrafter() {
        if (checks.checkWood()) {
            inventory.put("Boat", "Boat");
            System.out.println("You got a boat.");
            playerRow++;
        } else {
            System.out.println("You do not have the required items to use this.");
            incaseError();
        }
    }

    public void incaseError() {
        if (playerRow > 0) {
            playerRow--;
            System.out.println("You went north.");
            decideMap();
        } else {
            playerCol--;
            System.out.println("You went west.");
            decideMap();
        }
    }

    public void restartGame() {
        inventory.clear();
        playerRow = gameMap.getHeight() - 1;
        playerCol = gameMap.getWidth() / 2;
        cave.isinCave = false;
        System.out.println("Restarting game...");
    }

    public void decideMap() {
        if (cave.isinCave) {
            cave.caveMap.print(playerRow, playerCol);
        } else {
            gameMap.print(playerRow, playerCol);
        }
    }

    public void endGame() {
        System.out.println("Congratulations! You've found a valuable resource and won the game!");
        System.exit(0);
    }
}
