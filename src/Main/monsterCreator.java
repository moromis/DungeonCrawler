package Main;

import MonsterHolders.Level1;
import MonsterHolders.LevelTemplate;
import Monsters.Monster;

/**
 * Created by Kevin on 5/22/2015.
 * Creates an array of Monsters according to the dungeon floor
 */
public class monsterCreator {

    private static LevelTemplate mCreator;

    public monsterCreator(int x, int y, int[][] map, int level){
        switch(level) { //Switch out monster sets based on dungeon level
            case 1:
                mCreator = new Level1(x, y, map);
                break;
        }
    }

    public Monster[] makeMonsters(int num){
        Monster[] mArray = new Monster[num];

        for (int i = 0; i < num; i++) { //Make passed number of monsters and put them in the array
            mArray[i] =  mCreator.createMonster();
        }
        return mArray;
    }
}
