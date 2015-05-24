package MonsterHolders;

import Monsters.Monster;
import Monsters.Rat;

/**
 * Created by Kevin on 5/22/2015.
 * Level 1 Monster holder
 */
public class Level1 implements LevelTemplate {

    private int width;
    private int height;
    private int[][] collisionMap;
    Monster m;

    public Level1(int x, int y, int[][] map){
        width = x;
        height = y;
        collisionMap = map;
    }

    /**
     * Returns a monster from level 1
     * @return randomly chosen monster from set of monsters to appear on this floor.
     */
    public Monster createMonster(){
        int random = randInt(0, 10);

        switch (random){
            case 1:
                m = new Rat(width, height, collisionMap);
                break;
            default:
                m = new Rat(width, height, collisionMap);
                break;
        }
        return m;
    }

    public int randInt(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
