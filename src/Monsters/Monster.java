package Monsters;

/**
 * Created by Kevin on 5/20/2015.
 * Base Class for Monsters
 */
public class Monster {
    private String glyph;
    private String description;

    private int x;
    private int y;
    private int pursue_range;


    private int[][] grid;
    private boolean spawned = false;

    public Monster(int room_width, int room_height, int[][] map, String g, String desc, int pr){
        glyph = g;
        grid = map;
        pursue_range = pr;
        description = desc;
        while(!spawned) {
            this.x = randInt(1, room_width-1); //Spawn it at a random x,y
            y = randInt(1, room_height-1);
            System.out.println("X: " + x + ", Y: " + y);
            if(grid[y][x] == 0){ //Make sure it's on a floor tile
                spawned = true;
            }
        }
    }

    /**
     * Follow the player. This works by moving first in the x direction till it is in
     * line with the player and then going up or down till it has reached the player.
     *
     * Only pursues if the player is within pursue_range
     * @param player_x - player's x pos
     * @param player_y - player's y pos
     */
    public void update(int player_x, int player_y){
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("*****UPDATING*********");
        int diff_x = Math.abs(player_x - y);
        int diff_y = Math.abs(player_y - y);
        int store_x = x;
        int store_y = y;
        if(diff_x < pursue_range && diff_y < pursue_range) {
            if (x < player_x) {
                if (grid[y][x + 1] == 0) {
                    System.out.println("moving right");
                    store_x++;
                } else {
                    System.out.println("BLOCKED");
                }
            } else if (x > player_x) {
                if (grid[y][x - 1] == 0) {
                    System.out.println("moving left");
                    store_x--;
                } else {
                    System.out.println("BLOCKED");
                }
            }

            if (y < player_y) {
                if (grid[y + 1][x] == 0) {
                    System.out.println("moving down");
                    store_y++;
                } else {
                    System.out.println("BLOCKED");
                }
            } else if (y > player_y) {
                if (grid[y - 1][x] == 0) {
                    System.out.println("moving up");
                    store_y--;
                } else {
                    System.out.println("BLOCKED");
                }
            }

            if (store_x > x || store_x < x) {
                System.out.println("Pursuing X");
                x = store_x;
            } else if (store_y > y || store_y < y) {
                System.out.println("Pursuing Y");
                y = store_y;
            }
        }
    }

    public String getMonster(){
        return glyph;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getDesc(){
        return description;
    }

    private int randInt(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
