package Monsters;

/**
 * Created by Kevin on 5/20/2015.
 */
public class Monster {
    private String glyph;
    private String description;

    private int x_pos;
    private int y_pos;
    private int pursue_range;


    private int[][] grid;
    private boolean spawned = false;

    public Monster(int x, int y, int[][] map, String g, String desc, int pr){
        glyph = g;
        grid = map;
        pursue_range = pr;
        description = desc;
        while(!spawned) {
            x_pos = randInt(1, x-1); //Spawn it at a random x,y
            y_pos = randInt(1, y-1);
            System.out.println("X: " + x_pos + ", Y: " + y_pos);
            if(grid[y_pos][x_pos] == 0){ //Make sure it's on a floor tile
                spawned = true;
            }
        }
    }

    /**
     * Follow the player. This works by moving first in the x direction till it is in
     * line with the player and then going up or down till it has reached the player.
     *
     * Only pursues if the player is within pursue_range
     * @param current_x - player's x pos
     * @param current_y - player's y pos
     */
    public void update(int current_x, int current_y){
        int store_x = x_pos;
        int store_y = y_pos;
        if(current_x < x_pos){
                store_x = x_pos - 1;
        }else if(current_x > x_pos){
                store_x = x_pos + 1;
        }
        if(current_y < y_pos){
                store_y = y_pos - 1;

        }else if(current_y > y_pos){
                store_y = y_pos + 1;
        }

        if(store_x > store_y){
            x_pos = store_x;
        }else if(store_x < store_y){
            y_pos = store_y;
        }
    }

    public String getMonster(){
        return glyph;
    }

    public int getX(){
        return x_pos;
    }

    public int getY(){
        return y_pos;
    }

    public String getDesc(){
        return description;
    }

    private int randInt(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
