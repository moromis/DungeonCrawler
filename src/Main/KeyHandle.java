package Main;

import Main.Play;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Kevin on 5/19/2015.
 */
public class KeyHandle extends KeyAdapter {

    //STATE
    boolean u = false;



    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == e.VK_LEFT){
            if(u == true){
                Play.unlock(-1, 'x');
                u = false;
            }
            Play.move(-1, 'x');
        }
        if(key == e.VK_RIGHT){
            if(u == true){
                Play.unlock(1, 'x');
                u = false;
            }
            Play.move(1, 'x');
        }
        if(key == e.VK_DOWN){
            if(u == true){
                Play.unlock(1, 'y');
                u = false;
            }
            Play.move(1, 'y');
        }
        if(key == e.VK_UP){
            if(u == true){
                Play.unlock(-1, 'y');
                u = false;
            }
            Play.move(-1, 'y');
        }
        if(key == e.VK_U){ //U for unlock
            u = true;
        }
    }
}
