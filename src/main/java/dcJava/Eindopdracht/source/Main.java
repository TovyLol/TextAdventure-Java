package dcJava.Eindopdracht.source;
import java.util.HashMap;

import java.util.Scanner;
// chest >> axe&pickaxr >> boat >> cave >> resource >> ??




/* TO DO LIST
* maak t zo dat de crafter meerdere opties heeft
* */
public class Main {
    final static int WATER = 1;
    final static int GRASS = 2;
    final static int ROCK = 3;
    final static int TREE = 4;
    final static int CHEST = 5;
    final static int CAVE = 6;
    final static int CRAFTER = 12;

    int[][] map =
            {
                    {1, 12, 1, 1, 1, 2},
                    {2, 2, 2, 2, 4, 2},
                    {1, 2, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2, 1},
                    {1, 3, 5, 2, 1, 1},
                    {1, 1, 1, 1, 6, 1}
            };
    GameMap gameMap = new GameMap(map, "map");
    HashMap<String, String> inventory = new HashMap<String, String>();
    int playerRow;
    int playerCol;

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
        Scanner scanner;
        scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("stop")) {
            describeRoom();
            System.out.println(">");
            input = scanner.nextLine();
            executeInput(input);
            cave.checkInCave();
        }
    }

    public void describeRoom() {
        String reply = "You are ";
        String options = "";
        if (!cave.isinCave) {
            switch (map[playerRow][playerCol]) {
                case GRASS:
                    reply += "in the grass, ew so many bugs!!";
                    break;
                case ROCK:
                    reply += "on a rock.";
                    break;
                case TREE:
                    reply += "infront of a tree, do you wanna interact? YES/NO";
                    break;
                case CHEST:
                    reply += "in front of a chest. Do you want to open it? YES/NO";
                    break;
                case CAVE:
                    reply += "in front of a massive cave! Do you want to enter? YES/NO";
                    break;
                case WATER:
                    reply += "on water";
                    break;
                case CRAFTER:
                    reply += "at the crafter";
                    break;

                default:
                    reply += "";
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
//movement managing
    public void executeInput(String input) {
        int prevPlayerRow = playerRow;
        int prevPlayerCol = playerCol;
        String huh = "What do you mean?";
        String chestOpenedMessage = "Chest opened!";
        String chestLeaveMessage = "You left the chest behind.";

        if (map[playerRow][playerCol] == CHEST) {
            if (input.equalsIgnoreCase("yes")) {
                System.out.println(chestOpenedMessage);
                inventory.put("Pickaxe", "Pickaxe");
                inventory.put("Axe", "Axe");
                print.inventoryPrint(inventory);
                incaseError();
            } else if (input.equalsIgnoreCase("no")) {
                System.out.println(chestLeaveMessage);
                incaseError();
            } else if (input.equalsIgnoreCase("restart")) {
                restartGame();
            } else {
                handleMovement(input);
            }
        } else if (map[playerRow][playerCol] == CAVE) {
            if (input.equalsIgnoreCase("yes")) {
                if (checks.checkTools()) {
                    cave.enterCave();
                } else {
                    System.out.println("You forgot to bring your trusty pickaxe!");
                    incaseError();
                }
            } else if (input.equalsIgnoreCase("no")) {
                System.out.println("You turned around not looking back.");
                incaseError();
            } else if (input.equalsIgnoreCase("restart")) {
                restartGame();
            } else {
                incaseError();
            }
        } else if (map[playerRow][playerCol] == cave.RESOURCE) {
            if (input.equalsIgnoreCase("yes")) {
                checks.checkPicRe();
            } else if (input.equalsIgnoreCase("no")) {
                incaseError();
            } else if (input.equalsIgnoreCase("restart")) {
                restartGame();
            } else {
                System.out.println(huh);
            }
        } else if (map[playerRow][playerCol] == TREE) {
            if (input.equalsIgnoreCase("yes")) {
                if (checks.checkTools()) {
                    inventory.put("Wood", "Wood");
                    System.out.println("You violently cut down that tree, and now you have wood!");
                    incaseError();
                } else {
                    System.out.println("You do not have the required items to use this.");
                }
            } else if (input.equalsIgnoreCase("no")) {
                incaseError();
            } else if (input.equalsIgnoreCase("restart")) {
                restartGame();
            } else {
                System.out.println(huh);
            }
        } else if (map[playerRow][playerCol] == WATER) {
            if (input.equalsIgnoreCase("m")) {
                print.printMap(gameMap);
            } else {
                handleMovement(input);
            }
            if (!checks.checkBoat()) {
                playerRow = prevPlayerRow;
                playerCol = prevPlayerCol;
                System.out.println("You need a boat to cross the water!");
                incaseError();
            } else {
                describeRoom();
            }

        } else if (map[playerRow][playerCol] == CRAFTER) {
            switch (input) {
                case "Boat":
                    if (checks.checkWood()) {
                        inventory.put("Boat", "Boat");
                        System.out.println("You got a boat.");
                        playerRow = playerRow + 1;


                    } else {
                        System.out.println("You do not have the required items to use this.");
                        incaseError();
                    }
                    break;
                case "restart":
                    restartGame();
                    break;
            }
        }else if (map[playerRow][playerCol] == cave.EXIT) {
            if (input.equalsIgnoreCase("yes")) {
                cave.exitCave();
            }else {
                incaseError();
            }
        }else {
            handleMovement(input);
        }
    }
    public void handleMovement(String input) {

        switch (input) {
            case "n":
                if (playerRow > 0) {
                    playerRow = playerRow - 1;
                } else {
                    System.out.println("You cannot go north anymore.");
                }
                break;
            case "s":
                if (playerRow < gameMap.getHeight() - 1) {
                    playerRow = playerRow + 1;
                } else {
                    System.out.println("You cannot go south anymore.");
                }
                break;
                case "e":
                if (playerCol < gameMap.getWidth() - 1) {
                    playerCol = playerCol + 1;
                } else {
                    System.out.println("You cannot go east anymore.");
                }
                break;
            case "w":
                if (playerCol > 0) {
                    playerCol = playerCol - 1;
                } else {
                    System.out.println("You cannot go west anymore.");
                }
                break;
            case "h" :
                print.help();
                break;
            case "m":
                decideMap();
                break;
            case "inv":
                print.inventoryPrint(inventory);
                break;
            case "restart":
                System.out.println("Restarting game...");
                restartGame();
            case "cavemap":
                print.printMap(cave.caveMap);

            default:
                System.out.println("What do you mean?");
                break;
        }
    }
//essential stuff
    public void incaseError() {
        if (playerRow > 0) {
            playerRow = playerRow - 1;
            System.out.println("You went north.");
            decideMap();
        } else {
            playerCol = playerCol - 1;
            System.out.println("You went west.");
            decideMap();
        }
    }
    public void restartGame() {
        inventory.clear();
        playerRow = gameMap.getHeight() - 1;
        playerCol = gameMap.getWidth() / 2;
        cave.isinCave = false;
        System.out.println("restarting game...");

    }
    public void decideMap() {
        if (cave.isinCave) {
            cave.caveMap.print(playerRow, playerCol);
        } else {
            gameMap.print(playerRow, playerCol);
        }
    }
}