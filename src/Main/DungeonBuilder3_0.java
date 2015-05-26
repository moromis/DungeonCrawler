package Main;

/**
 * Created by Kevin on 5/17/2015.
 * Builds a dungeon
 */
public class DungeonBuilder3_0 {

    private int x; //Width of the dungeon
    private int y; //Height of the dungeon
    private char[][] map; //Character representation of the map
    private int[][] CollisionMap; //Integer representation of the map, used for collisions

    public DungeonBuilder3_0(int dungeon_height, int dungeon_width) {
        y = dungeon_height;
        x = dungeon_width;

        map = new char[y][x]; //Initialize our arrays
        CollisionMap = new int[y][x];
        fillMap();
        tunnelMap(1, 1); //Go up to 10 squares in each direction to start ourselves off
        tunnelMap(2, 1);
        tunnelMap(3, 1);
        tunnelMap(4, 1);
        tunnelMap(0, 0); //Then go crazy exactly twice
        tunnelMap(0, 0);

        placeRooms(); //Scatter some rooms around

        cleanup(); //Cleanup the map so there's no loose doors in the middle of nowhere

        makeCollisionMap();
    }

    /**
     * This function gets rid of any doors surrounded by floor (no need for it to be there
     */
    private void cleanup() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(map[i][j] == '='){ //If we're on a door
                    if(i-1 > 0 && i+1 < y && j-1 > 0 && j+1 < x) { //And if we're not right next to an edge of the map
                        //Check in the four cardinal directions
                        if ((map[i - 1][j] == '+' || map[i - 1][j] == '*') && (map[i + 1][j] == '+' || map[i + 1][j] == '*') && (map[i][j + 1] == '+' || map[i][j + 1] == '*') && (map[i][j-1] == '+' || map[i][j-1] == '*')){
                            //System.out.println("Destroyed a rogue door!");
                            map[i][j] = '*'; //Make it into a floor
                        }
                    }
                }
            }
        }
    }

    /**
     * Once we're done making a spiderweb cave system we place some nice manmade looking rooms
     */
    private void placeRooms() {
        //int n = (int)(Math.random() * 5 + (x / 10) + (y*0.3)); //Number of rooms to be placed
        int n = 3;
        int l = (3 + (int) (Math.random() * ((x / 5 - 3) + 1))); //Length of room
        int h = (3 + (int) (Math.random() * ((y / 5 - 3) + 1))); //Height of room
        int rel_x;
        int rel_y;
        int direction = (int) (1 + (Math.random() * 4)); //Direction to go once room is made

        for (int i = 0; i < n; i++) { //Make n rooms
            rel_y = (1 + ((int) (Math.random() * (((y - 1) - 1) + 1)))); //random number from 1 to y-1
            rel_x = (1 + ((int) (Math.random() * (((x - 1) - 1) + 1)))); //random number from 1 to x-1
            if ((rel_x + l) < x && (rel_y + h) < y) { //Make sure we're within bounds before we build the room
                for (int j = rel_y; j < (rel_y + h); j++) {
                    for (int k = rel_x; k < (rel_x + l); k++) {
                        map[j][k] = '*';
                    }
                }
            }
            if ((rel_x + l) < x && (rel_y + h) < y) {
                switch (direction) {
                    case 1:
                        map[rel_y - 1][rel_x + (l/2)] = '=';
                        if(rel_y - 2 > 0) {
                            tunnel(rel_y - 2, rel_x + (l / 2), direction);
                        }
                        break;
                    case 2:
                        map[rel_y + (h/2)][rel_x + l] = '=';
                        if(rel_x + l + 1 < x) {
                            tunnel(rel_y + (h / 2), rel_x + l + 1, direction);
                        }
                        break;
                    case 3:
                        map[rel_y + h][rel_x + (l/2)] = '=';
                        if(rel_y + h + 1 < y) {
                            tunnel(rel_y + h + 1, rel_x + (l / 2), direction);
                        }
                        break;
                    case 4:
                        map[rel_y + (h/2)][rel_x - 1] = '=';
                        if(rel_x - 2 > 0) {
                            tunnel(rel_y + (h / 2), rel_x - 2, direction);
                        }
                        break;
                }
            }

            direction = (int) (1 + (Math.random() * 4));
            l = (3 + (int) (Math.random() * ((x / 5 - 3) + 1)));
            h = (3 + (int) (Math.random() * ((x / 5 - 3) + 1)));
        }
    }

    /**
     * From a given (rel_x, rel_y) tunnel in passed direction until we find floor
     * @param rel_y - relative y position (situated within our maximum dungeon height)
     * @param rel_x - relative x position (situated within our maximum dungeon width)
     * @param direction - Direction in which to tunnel
     */
    private void tunnel(int rel_y, int rel_x, int direction) {
        while(map[rel_y][rel_x] == '#' && rel_x < x && rel_x > 0 && rel_y < y && rel_y > 0){ //While we're on a wall
            switch (direction) {
                case 1: //North
                    if ((rel_y - 1) > 0) {//So we don't go out of bounds
                        map[rel_y][rel_x] = '+';
                        rel_y--;
                    }else{
                        if(direction == 4){
                            direction = 1;
                        }else{
                            direction++;
                        }
                    }
                    break;
                case 2: //East
                    if ((rel_x + 1) < (x - 1)) {//So we don't go out of bounds
                        map[rel_y][rel_x] = '+';
                        rel_x++;
                    }else{
                        if(direction == 4){
                            direction = 1;
                        }else{
                            direction++;
                        }
                    }
                    break;
                case 3: //South
                    if ((rel_y + 1) < y - 1) {//So we don't go out of bounds
                        map[rel_y][rel_x] = '+';
                        rel_y++;
                    }else{
                        if(direction == 4){
                            direction = 1;
                        }else{
                            direction++;
                        }
                    }
                    break;
                case 4: //West
                    if ((rel_x - 1) > 0) { //So we don't go out of bounds
                        map[rel_y][rel_x] = '+';
                        rel_x--;
                    }else{
                        if(direction == 4){
                            direction = 1;
                        }else{
                            direction++;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Fills the map with wall tile
     */
    private void fillMap(){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = '#';
            }
        }
    }

    /**
     * Fills an int[][] with ones where the player can't go, such as walls
     * 0 = floor (no collision), 1 = walls, 2 = doors
     */
    private void makeCollisionMap(){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(map[i][j] == '#'){
                    CollisionMap[i][j] = 1;
                }else if(map[i][j] == '=') {
                    CollisionMap[i][j] = 2;
                }else{
                    CollisionMap[i][j] = 0;
                }
            }
        }
    }

    /**
     * Tunnels out the cave.
     * @param dir = direction for the tunneler to head, if 0 it will be randomized
     * @param num = number of times for the tunneler to move, if 0 it will be randomized
     */
    private void tunnelMap(int dir, int num){
        int direction = dir;
        if(dir == 0){ //If no direction is specified just go in a random direction
            direction = (int)(1+(Math.random()*4));
        }
        int n;
        if(num == 0){ //If no number of iterations is specified make a random number (within reason)
            n = (int)((Math.random()*(x*y) - (y/2)));
        }else{ //otherwise make a number from 1 to 10
            n =(int)(Math.random()*10);
        }
        int my_y = y/2; //Start from the middle of the dungeon
        int my_x = x/2;
        map[my_y][my_x] = '^'; //Make a spawn point indicator

        for (int i = n; i > 0; i--) {
            switch (direction) {
                case 1: //North
                    if(my_y - 1 > 0){//So we don't go out of bounds
                        my_y--;
                        map[my_y][my_x] = '*';
                    }
                    break;
                case 2: //East
                    if(my_x + 1 < x-1){//So we don't go out of bounds
                        my_x++;
                        map[my_y][my_x] = '*';
                    }
                    break;
                case 3: //South
                    if(my_y + 1 < y-1){//So we don't go out of bounds
                        my_y++;
                        map[my_y][my_x] = '*';
                    }
                    break;
                case 4: //West
                    if(my_x - 1 > 0){ //So we don't go out of bounds
                        my_x--;
                        map[my_y][my_x] = '*';
                    }
                    break;
            }
            direction = (int)(1+(Math.random()*4));
        }
    }

    /**
     * Returns the character map for the dungeon
     */
    public char[][] getDungeonMap() {
        return map;
    }

    /**
     * Return the collision map for the dungeon
     */
    public int[][] getCollisionMap(){
        return CollisionMap;
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
