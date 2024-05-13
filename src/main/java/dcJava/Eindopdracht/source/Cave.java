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


    public void enterCave() {
        if (!isinCave == false) {
            System.out.println("how did you even get this error");

        } else {
            isinCave = true;
            System.out.println("You went into the Cave.");
        }
    }
    public void exitCave() {

        if (!isinCave == true) {
            System.out.println("how did you even get this error");
        } else {
            isinCave = false;
            System.out.println("The sun is too bright! but hey atleast you left the cave.");
        }
    }
    public void checkInCave() {
        if (isinCave = true) {
            System.out.println("volgens programma in de cave");
        } else {
            System.out.println("volgens programma buiten de cave");
        }

    }
}
