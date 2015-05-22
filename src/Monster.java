/**
 * Created by Kevin on 5/20/2015.
 */
public class Monster {
    private String glyph;
    private int x_pos;
    private int y_pos;
    private int[][] grid;
    private boolean spawned = false;

    public Monster(int x, int y, int[][] map){
        glyph = createGlyph();
        grid = map;
        while(spawned == false) {
            x_pos = randInt(1, x-1); //Spawn it at a random x,y
            y_pos = randInt(1, y-1);
            System.out.println("X: " + x_pos + ", Y: " + y_pos);
            if(grid[y_pos][x_pos] == 0){ //Make sure it's on a floor tile
                spawned = true;
            }
        }
    }

    public void update(int current_x, int current_y){
        int store_x = 0;
        int store_y = 0;
        if(current_x < x_pos){
            store_x = x_pos-1;
        }else if(current_x > x_pos){
            store_x = x_pos+1;
        }
        if(current_y < y_pos){
            store_y = y_pos-1;
        }else if(current_y > y_pos){
            store_y = y_pos+1;
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

    private int randInt(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

    private String createGlyph(){
        int rand = (int)(Math.random()*10);
        String s = "";
        switch(rand){
            case 0: s = "\u00A7";
            break;
            case 1: s = "\u00DF";
            break;
            case 2: s = "\u00C7";
            break;
            case 3: s = "\u0152";
            break;
            case 4: s = "\u01BA";
            break;
            case 5: s = "\u023E";
            break;
            case 6: s = "\u2C64";
            break;
            default: s = "\u00DF";
        }
        return s;
    }
}
