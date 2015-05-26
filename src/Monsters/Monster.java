package Monsters;

import java.awt.*;

/**
 * Created by Kevin on 5/20/2015.
 * Base Class for Monsters
 */
public class Monster {
    private String glyph;
    private String description;
    private Color color;

    private int x; //The monster's current position
    private int y;
    private int pursue_range; //The range within which the monster will begin to pursue the player


    private int[][] grid;
    private boolean spawned = false;

    private int health;

    /**
     * Constructor
     * @param room_width - width of the dungeon
     * @param room_height - height of the dungeon
     * @param map - collision map for the dungeon
     * @param g - glyph to represent the monster
     * @param desc - description of the monster
     * @param pr - pursuit range
     */
    public Monster(int room_width, int room_height, int[][] map, String g, String desc, int pr, Color c, int h){
        glyph = g;
        grid = map;
        pursue_range = pr;
        description = desc;
        color = c;
        health = h;
        while(!spawned) {
            x = randInt(1, room_width-1); //Spawn it at a random x,y
            y = randInt(1, room_height-1);
            //System.out.println("X: " + x + ", Y: " + y);
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
        int diff_x = Math.abs(player_x - y);
        int diff_y = Math.abs(player_y - y);
        int store_x = x;
        int store_y = y;
        if(diff_x <= pursue_range && diff_y <= pursue_range) {
            if (x < player_x) {
               // if (grid[y][x + 1] == 0) {
                    store_x++;
                //}
            } else if (x > player_x) {
               // if (grid[y][x - 1] == 0) {
                    store_x--;
               // }
            }

            if (y < player_y) {
               // if (grid[y + 1][x] == 0) {
                    store_y++;
               // }
            } else if (y > player_y) {
                //if (grid[y - 1][x] == 0) {
                    store_y--;
                //}
            }

            if (store_x > x || store_x < x) {
                x = store_x;
            } else if (store_y > y || store_y < y) {
                y = store_y;
            }
        }
    }

    /**
     * Getters
     */
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

    public Color getColor(){
        return color;
    }

    public int getHealth(){
        return health;
    }

    /**
     * Random number generator within a range
     * @param Min - min number
     * @param Max - max number
     * @return a random int
     */
    private int randInt(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
