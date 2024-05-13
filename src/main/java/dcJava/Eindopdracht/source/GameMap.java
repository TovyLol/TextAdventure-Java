package dcJava.Eindopdracht.source;

public class GameMap {
    int[][] map;
    String name;
    public GameMap(int[][] map, String name) {
        this.map = map;
        this.name = name;
    }

    public int[][] getMap(){
        return map;
    }

    public int getHeight(){
        return map.length;
    }

    public int getWidth(){
        return map[0].length;
    }

    public String getName(){
        return this.name;
    }
    public void print(int playerRow, int playerCol) {
        System.out.println(getName() + " Map:");
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("X ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }

    }
}
