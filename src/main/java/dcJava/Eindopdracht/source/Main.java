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
    public static void main(String args[]) {
        Main ta = new Main();
        ta.init();
        ta.mainLoop();
    }
    public void init() {
        System.out.println("Welcome to the game.");
        playerRow = gameMap.getHeight() - 1;
        playerCol = gameMap.getWidth() / 2;
    }
    public void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("stop")) {
            describeRoom(input);
            System.out.println(">");
            input = scanner.nextLine();
            executeInput(input);
            cave.checkInCave();
        }
    }
    public void describeRoom(String input) {
        String reply = "You are ";
        if (!cave.isinCave) {
            switch (map[playerRow][playerCol]) {
                case GRASS:
                    reply += "in the grass, ew so many bugs!!";
                    break;
                case ROCK:
                    reply += "on a rock.";
                    break;
                case TREE:
                    reply += "in front of a tree, do you wanna interact? YES/NO";
                    break;
                case CHEST:
                    reply += "in front of a chest. Do you want to open it? YES/NO";
                    break;
                case CAVE:
                    reply += "in front of a massive cave! Do you want to enter? YES/NO";
                    break;
                case WATER:
                    reply += "on water";
                    if (input.equals("Boat") || input.equals("Crafter")) {
                        checks.handleBoatAndCrafter();
                    } else {
                        handleMovement(input);
                    }
                    break;
                case CRAFTER:
                    reply += "at the crafter";
                    if (input.equals("Boat") || input.equals("Crafter")) {
                        checks.handleBoatAndCrafter();
                    } else {
                        handleMovement(input);
                    }
                    break;
            }
        } else {
            if (cave.caveMap.getMap()[playerRow][playerCol] == cave.EXIT) {
                reply = reply + "in front of the exit of the cave, but do you want to exit? YES/NO";
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.WALL) {
                reply = reply + "in front of a steep wall, you may want to turn around.";
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.RESOURCE) {
                reply = reply + "looking at a valuable resource but what is it? Mine it to find out! YES/NO";
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.PIT) {
                System.out.println("You fell in the pit and died :(");
                restartGame();
            } else if (cave.caveMap.getMap()[playerRow][playerCol] == cave.HALLWAY) {
                reply = reply + "in a hallway, might wanna keep walking forward.";
            }
        }
        System.out.println(BORDER);
        System.out.println(reply);
    }
    public void executeInput(String input) {
        if (input.equalsIgnoreCase("yes")) {
            switch (map[playerRow][playerCol]) {
                case CHEST:
                    System.out.println("Chest opened!");
                    inventory.put("Pickaxe", "Pickaxe");
                    inventory.put("Axe", "Axe");
                    print.inventoryPrint(inventory);
                    break;
                case CAVE:
                    if (checks.checkTools()) {
                        cave.enterCave();
                    } else {
                        System.out.println("You forgot to bring your trusty pickaxe!");
                        incaseError();
                    }
                    break;
                case TREE:
                    if (checks.checkTools()) {
                        inventory.put("Wood", "Wood");
                        System.out.println("You violently cut down that tree, and now you have wood!");
                        incaseError();
                    } else {
                        System.out.println("You do not have the required items to use this.");
                    }
                    break;
            }
        } else if (input.equalsIgnoreCase("no")) {
            if (map[playerRow][playerCol] == CHEST) {
                System.out.println("You left the chest behind.");
                incaseError();
            } else if (map[playerRow][playerCol] == CAVE) {
                System.out.println("You turned around not looking back.");
                incaseError();
            } else if (map[playerRow][playerCol] == cave.RESOURCE) {
                incaseError();
            } else if (map[playerRow][playerCol] == TREE) {
                incaseError();
            } else if (map[playerRow][playerCol] == cave.EXIT) {
                cave.exitCave();
            }
        } else {
            handleMovement(input);
        }
    }
    public void handleMovement(String input) {
        switch (input) {
            case "n":
                playerRow = (playerRow > 0) ? playerRow - 1 : playerRow;
                if (playerRow == playerRow)
                    playerCol--;
                break;
            case "s":
                playerRow = (playerRow < gameMap.getHeight() - 1) ? playerRow + 1 : playerRow;
                break;
            case "e":
                playerCol = (playerCol < gameMap.getWidth() - 1) ? playerCol + 1 : playerCol;
                break;
            case "w":
                playerCol = (playerCol > 0) ? playerCol - 1 : playerCol;
                break;
            case "h":
                print.help();
                break;
            case "m":
                decideMap();
                break;
            case "inv":
                print.inventoryPrint(inventory);
                break;
            case "restart":
                restartGame();
                break;
            case "cavemap":
                print.printMap(cave.caveMap);
                break;
            default:
                System.out.println("What do you mean?");
                break;
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
}