package Old;

import java.awt.*;

/**
 * This class creates a an area containing a room and all its paths
 */
public class Room {

    int h; //height
    int l; //length
    Room r_North; //Neighbors
    Room r_South;
    Room r_East;
    Room r_West;
    Point Center;
    Point North; //These are points to mark exits from this "room"
    Point South;
    Point East;
    Point West;

    int[][] room = new int[10][10];



    char wall = (char)(Integer.parseInt("2580", 16));

    public Room(){
        h = (int)(Math.random()*9);
        l = (int)(Math.random()*9);

        Center = new Point(3+(int)(Math.random()*(4)), 3+(int)(Math.random()*(4))); //Bounded by the inner 4x4 square of the 10x10
        North = new Point(1+(int)(Math.random()*8), 0); //These just choose a point along the wall, but not the corners
        South = new Point(1+(int)(Math.random()*8), 9);
        East = new Point(9, 1+(int)(Math.random()*8));
        West = new Point(0, 1+(int)(Math.random()*8));
    }

    void setR_North(){

    }

    void setR_South(){

    }

    void setR_East(){

    }

    void setR_West(){

    }

    void print(){
        int index = 0;
        for(int[] ai : room){
            for(int i : ai){
                switch (i){
                    case 1: //Floor
                        System.out.print("#");
                    case 2: //Wall
                        if(index == 0 || index == 9){
                            System.out.print("_");
                        }else{
                            System.out.print("|");
                        }
                    case 3: //Space
                        System.out.print(" ");
                }
            }
            System.out.println();
            index++;
        }
    }
}
