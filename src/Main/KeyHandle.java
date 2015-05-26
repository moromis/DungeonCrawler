package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Kevin on 5/19/2015.
 */
public class KeyHandle extends KeyAdapter {

    //STATE
    boolean u = false; //unlock boolean

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == e.VK_LEFT){
            if(u){
                Play.unlock(-1, 'x');
                u = false;
            }
            Play.move(-1, 'x');
        }
        if(key == e.VK_RIGHT){
            if(u){
                Play.unlock(1, 'x');
                u = false;
            }
            Play.move(1, 'x');
        }
        if(key == e.VK_DOWN){
            if(u){
                Play.unlock(1, 'y');
                u = false;
            }
            Play.move(1, 'y');
        }
        if(key == e.VK_UP){
            if(u){
                Play.unlock(-1, 'y');
                u = false;
            }
            Play.move(-1, 'y');
        }
        if(key == e.VK_U){ //U for unlock
            u = true;
        }
        if(key == e.VK_ESCAPE){ //Quit the game
            System.exit(0);
        }
    }
}
