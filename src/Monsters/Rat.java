package Monsters;

import java.awt.*;

/**
 * Created by Kevin on 5/22/2015.
 * Rat monster - has 10 health
 */
public class Rat extends Monster {

    private static String glyph = "\u01BA";
    private static String description = "A rat, quite a bit larger than normal sewer rats. Has gnarled, dirty teeth.";
    private static Color c = new Color(0xA3605D);

    public Rat(int x, int y, int[][] map){
        super(x, y, map, glyph, description, 10, c, 10);
    }

}
