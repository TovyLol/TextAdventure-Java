package dcJava.Eindopdracht.source;

public class Cave {
    Main main;
    Checks checks;

    public Cave(Main main, Checks checks) {
        this.main = main;
        this.checks = checks;
    }
    public final static int EXIT = 1;
    public final static int WALL = 2;
    public final static int RESOURCE = 3;
    public final static int PIT = 4;
    public final static int HALLWAY = 5;
    public boolean isinCave = false;

    int[][] map = {
            {2, 2, 1, 2, 2, 3, 2, 2},
            {2, 2, 5, 5, 2, 3, 3, 3},
            {2, 2, 5, 5, 5, 5, 3, 3},
            {2, 5, 5, 5, 5, 2, 5, 5},
            {2, 3, 5, 5, 2, 2, 3, 2},
            {2, 4, 5, 5, 2, 5, 2, 2},
            {2, 4, 5, 5, 5, 4, 4, 2},
            {2, 2, 2, 2, 2, 2, 2, 2}
    };
    GameMap caveMap = new GameMap(map, "map");



    public void checkInCave() {
        if (isinCave == true) {
            System.out.println("You are in the cave according to the program.");
        } else {
            System.out.println("You are outside the cave according to the program.");
        }
    }

    public void enterCave() {
        if (!isinCave) {
            isinCave = true;
            System.out.println("You went into the cave.");
        } else {
            System.out.println("You're already in the cave!");
        }
    }

    public void exitCave() {
        if (isinCave) {
            isinCave = false;
            System.out.println("You left the cave.");
        } else {
            System.out.println("You're not in the cave!");
        }
    }

}
