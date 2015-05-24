package Monsters;

/**
 * Created by Kevin on 5/22/2015.
 * Rat monster
 */
public class Rat extends Monster {

    private static String glyph = "\u01BA";
    private static String description = "A rat, quite a bit larger than normal sewer rats. Has gnarled, dirty teeth.";

    public Rat(int x, int y, int[][] map){
        super(x, y, map, glyph, description, 10);
    }

}
