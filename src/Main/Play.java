package Main;

import Monsters.Monster;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Created by Kevin on 5/17/2015.
 * Main engine, guts of the game
 */
public class Play extends JPanel {

    /**
     ***FONT
     */
    private final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 18);
    private static int fontWidth;
    private static int fontHeight;
    private static Font mainFont = null;
    private static int fontYOffset;

    /**
     ***HEIGHT AND WIDTH VARIABLES***
     */
    //Dungeon
    private static int dungeonWidth; //Width of our dungeon
    private static int dungeonHeight; //Height of our dungeon

    //Player
    private static int current_x;
    private static int current_y;

    //Screen
    private static int width; //Width of the screen
    private static int height; //height of the screen

    private static int DrawX; //X and Y to display the piece of our dungeon on
    private static int DrawY; //in terms of the window
    private static int minDrawX;
    private static int minDrawY;
    private static int maxDrawX; //Viewing window variables
    private static int maxDrawY;
    private static int centerDrawX;
    private static int centerDrawY;

    private static int vision_range;

    /**
     ***ARRAYS
     */
    private static char[][] map; //2D array to store our dungeon
    private static int[][] CollisionMap; //2D array to check for collisions\

    /**
     ***MONSTERS
     */
    private static Monster[] monsters; //array to store monsters in
    private static monsterCreator mc;
    private static int mobNum; //Maxiumum number of mobs in the maze at any given time

    /**
     ***ENGINE STATE
     */
    private static JFrame frame; // Jframe
    private static Boolean isDead = false;

    /**
     ***LEVEL VARIABLES
     */
    private static int dungeon_level = 1;

    public Play(){
        setMainFont(DEFAULT_FONT);
    }

    public static void main(String[] args) throws InterruptedException{
        width = 900; //Set the width and height of the dungeon
        height = 800;

        makeFrame(width, height); //Instantiate the game window
        frame.add(new Play()); //Create an instance of this class inside the window

        start(); //Set our variables and create the dungeon
        drawBG(); //Draw the BG
        drawPlayer(); //Draw the Player
        drawMonsters();
    }

    /**
     * Creates a maze
     */
    private static void start(){
        dungeonWidth = 100; //Set width and height for the dungeon
        dungeonHeight = 100;

        current_x = dungeonWidth /2; //Set our starting position
        current_y = dungeonHeight /2;

        DrawX = width/2 - fontWidth*vision_range; //Set our print width and height
        DrawY = fontHeight - vision_range/2;
        centerDrawX = width/2;
        centerDrawY = fontHeight*fontHeight;
        minDrawX = centerDrawX - vision_range*fontWidth;
        minDrawY = centerDrawY - (vision_range/2)*fontHeight;
        maxDrawX = centerDrawX + (vision_range-1)*fontWidth;
        maxDrawY = centerDrawY + (vision_range/2 - 1)*fontHeight;

        vision_range = 15;

        DungeonBuilder3_0 dm = new DungeonBuilder3_0(dungeonHeight, dungeonWidth); //create our dungeon
        map = dm.getDungeonMap();
        CollisionMap = dm.getCollisionMap();

        mobNum = (dungeonWidth/10 + dungeonHeight/10)/2;
        createMonsters(); //Create monsters in the maze

        frame.getContentPane().setBackground(Color.BLACK); //Set the background color
        frame.addKeyListener(new KeyHandle()); //Create our input handler

        print(map, dungeonHeight, dungeonWidth);
    }

    /**
     * Create monsters
     */
    private static void createMonsters(){
        mc = new monsterCreator(current_x, current_y, CollisionMap, dungeon_level);
        monsters = mc.makeMonsters(mobNum);
    }

    /**
     * Draw monsters
     */
    private static void drawMonsters(){
        Graphics g = frame.getGraphics();
        g.setFont(mainFont);

        g.setColor(Color.WHITE); //Draw the monsters
        for (Monster monster : monsters) {

            //System.out.println("PLAYER: " + current_x + ", " + current_y);
            //System.out.println("MONSTER " + monster.toString() + ": " + monster.getX() + ", " + monster.getY());

            monster.update(current_x, current_y); //Update the monster's position relative to the player

            if (checkVisible(monster.getX(), monster.getY())) {
                g.setColor(Color.BLACK); //Cover up the character we're drawing over
                g.fillRect(centerDrawX - fontWidth * (current_x - monster.getX()), centerDrawY - fontHeight * (current_y - monster.getY()) - fontHeight, fontWidth, fontHeight);

                g.setColor(Color.WHITE); //Draw our player character
                g.drawString(monster.getMonster(), centerDrawX - fontWidth * (current_x - monster.getX()), centerDrawY - fontHeight * (current_y - monster.getY()));
            }
        }
    }

    /**
     * Draws the background,
     * goes through the map array and prints it to our window line by line as per an old television.
     */
    private static void drawBG(){
        int dx = DrawX;
        int dy = DrawY;
        String currentLine = "";
        Graphics g = frame.getGraphics();
        g.setFont(mainFont);

        g.clearRect(0, 0, width, height); //Clears the whole screen then redraws the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        for (int i = current_y - vision_range/2; i < current_y + vision_range/2; i++) { //Draw the background within
            for (int j = current_x - vision_range; j < current_x + vision_range; j++) { //vision_range from the player
                if(i>0 && j>0 && i< dungeonHeight && j< dungeonWidth) {
                    currentLine += map[i][j]; //Add to the line we're currently drawing
                }else{
                    currentLine += " "; //If the char is outside of the array, just add a space
                }
            }
            g.drawString(currentLine, dx - (fontWidth*vision_range), fontHeight*(dy - vision_range/2)); //Draw the string we just stored.
                                                                                                        // These calculations keep the drawing centered
                                                                                                        // in the screen based on the vision_range var
            currentLine = "";
            dy++;
        }
    }

    /**
     * Draws the player, in the middle of the screen.
     */
    private static void drawPlayer(){
        Graphics g = frame.getGraphics();
        g.setFont(mainFont);

        g.setColor(Color.BLACK); //Cover up the character we're drawing over
        g.fillRect((width / 2), (fontHeight) * (fontHeight) - fontHeight, fontWidth, fontHeight);

        g.setColor(Color.WHITE); //Draw our player character
        g.drawString("@", (width / 2), (fontHeight) * (fontHeight) - 1);
    }

    /**
     * Checks if the given object is visible within our viewing window (Not the player though)
     */
    private static boolean checkVisible(int x, int y){
        return (x >= current_x - vision_range && x < current_x + vision_range && y >= current_y - vision_range/2 && y < current_y + vision_range/2);
    }

    public static void move(int val, char dir){
        if(dir == 'x') {
            if (CollisionMap[current_y][current_x + val] == 0) { //Make sure we can go that way
                current_x += val;
                drawBG();
                drawPlayer();
                drawMonsters();
            }
        }else{
            if(CollisionMap[current_y + val][current_x] == 0) { //Make sure we can go that way
                current_y += val;
                drawBG();
                drawPlayer();
                drawMonsters();
            }
        }
    }

    public static void unlock(int val, char dir){
        if(dir == 'x') {
            if (CollisionMap[current_y][current_x + val] == 2) { //Check if there's a door
                map[current_y][current_x + val] = '*';
                CollisionMap[current_y][current_x + val] = 0;
                drawBG();
                drawPlayer();
                drawMonsters();
            }
        }else{
            if(CollisionMap[current_y + val][current_x] == 2) { //Check if there's a door
                map[current_y + val][current_x] = '*';
                CollisionMap[current_y + val][current_x] = 0;
                drawBG();
                drawPlayer();
                drawMonsters();
            }
        }
    }

    private static void print(char[][] m, int y, int x){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.printf("%2c", m[i][j]);
            }
            System.out.println();
        }
    }

    private static void printCollisionMap(int[][] m, int y, int x){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.printf("%2d", m[i][j]);
            }
            System.out.println();
        }
    }

    private static void makeFrame(int w, int h){
        frame = new JFrame("Dungeon Crawler");
        frame.setSize(w, h);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setFont(mainFont);
        g.setColor(Color.BLACK);
    }

    /**
     * Sets the main font of the console, which is used to determine the size of
     * characters
     *
     * @param font - Font to set as main
     */
    private void setMainFont(Font font) {
        mainFont = font;

        FontRenderContext fontRenderContext = new FontRenderContext(mainFont.getTransform(), false, false);
        Rectangle2D charBounds = mainFont.getStringBounds("X", fontRenderContext);
        fontWidth = (int) charBounds.getWidth();
        fontHeight = (int) charBounds.getHeight();
        fontYOffset = -(int) charBounds.getMinY();
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public int getDungeonWidth(){
        return dungeonWidth;
    }

    public int getDungeonHeight(){
        return dungeonHeight;
    }
}
