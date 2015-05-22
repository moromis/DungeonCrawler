package Old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JComponent;

/**
 * Class implementing a Swing-based text console
 *
 * Principles:
 * - provides a fixed number of rows and columns, but can be resized
 * - each cell can have its own foreground and background colour
 * - The main font determines the grid size
 *
 * @author Mike Anderson
 *
 */
public class JConsole extends JComponent implements HierarchyListener {
    private static final long serialVersionUID = 3571518592759968333L;

    private static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
    private static final Color DEFAULT_BACKGROUND = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 18);

    private Data data = new Data();

    private int fontWidth;
    private int fontHeight;
    private int fontYOffset;

    private int cursorX = 0;
    private int cursorY = 0;
    private Font mainFont = null;
    private Font currentFont = null;
    private Color currentForeground = DEFAULT_FOREGROUND;
    private Color currentBackground = DEFAULT_BACKGROUND;

    public JConsole(int columns, int rows) {
        setMainFont(DEFAULT_FONT);
        setFont(mainFont);
        init(columns, rows);
    }

    /**
     * Sets the main font of the console, which is used to determine the size of
     * characters
     *
     * @param font
     */
    public void setMainFont(Font font) {
        mainFont = font;

        FontRenderContext fontRenderContext = new FontRenderContext(
                mainFont.getTransform(), false, false);
        Rectangle2D charBounds = mainFont.getStringBounds("X",
                fontRenderContext);
        fontWidth = (int) charBounds.getWidth();
        fontHeight = (int) charBounds.getHeight();
        fontYOffset = -(int) charBounds.getMinY();

        setPreferredSize(new Dimension(data.columns * fontWidth, data.rows * fontHeight));

        repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        addHierarchyListener(this);
    }

    @Override
    public void removeNotify() {
        removeHierarchyListener(this);
        super.removeNotify();
    }

    public void setRows(int rows) {
        resize(this.data.columns, rows);
    }

    public void setFont(Font f) {
        currentFont = f;
    }

    public int getRows() {
        return data.rows;
    }

    public void setColumns(int columns) {
        resize(columns, this.data.rows);
    }

    public int getColumns() {
        return data.columns;
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    /**
     * Fires a repaint event on a specified rectangle of characters in the
     * console
     */
    public void repaintArea(int column, int row, int width, int height) {
        int fw = getFontWidth();
        int fh = getFontHeight();
        repaint(column * fw, row * fh, width * fw, height * fh);
    }

    /**
     * Initialises the console to a specified size
     */
    protected void init(int columns, int rows) {
        data.init(columns, rows);
        Arrays.fill(data.background, DEFAULT_BACKGROUND);
        Arrays.fill(data.foreground, DEFAULT_FOREGROUND);
        Arrays.fill(data.font, DEFAULT_FONT);
        Arrays.fill(data.text, ' ');

        setPreferredSize(new Dimension(columns * fontWidth, rows * fontHeight));
    }

    @Override
    public void resize(int columns, int rows) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        clearArea(0, 0, data.columns, data.rows);
    }

    public void clearScreen() {
        clear();
    }

    private void clearArea(int column, int row, int width, int height) {
        data.fillArea(' ', currentForeground, currentBackground, currentFont,
                column, row, width, height);
        repaintArea(0, 0, width, height);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Rectangle r = g.getClipBounds();

        // AffineTransform textTransform=new AffineTransform();
        // textTransform.scale(fontWidth, fontHeight);
        // g.setTransform(textTransform);

        // calculate x and y range to redraw
        int x1 = (int) (r.getMinX() / fontWidth);
        int x2 = (int) (r.getMaxX() / fontWidth) + 1;
        int y1 = (int) (r.getMinY() / fontWidth);
        int y2 = (int) (r.getMaxY() / fontWidth) + 1;

        for (int j = Math.max(0, y1); j < Math.min(y2, data.rows); j++) {
            int offset = j * data.columns;
            int start = Math.max(x1, 0);
            int end = Math.min(x2, data.columns);

            while (start < end) {
                Color nfg = data.foreground[offset + start];
                Color nbg = data.background[offset + start];
                Font nf = data.font[offset + start];

                // index of ending position
                int i = start + 1;

                // set font
                g.setFont(nf);

                // draw background
                g.setBackground(nbg);
                g.clearRect(fontWidth * start, j * fontHeight, fontWidth
                        * (i - start), fontHeight);

                // draw chars up to this point
                g.setColor(nfg);
                for (int k=start; k<i; k++) {
                    g.drawChars(data.text, offset + k, 1, k
                            * fontWidth, j * fontHeight + fontYOffset);
                }
                start = i;
            }
        }
    }

    public void setForeground(Color c) {
        currentForeground = c;
    }

    public void setBackground(Color c) {
        currentBackground = c;
    }

    public Color getForeground() {
        return currentForeground;
    }

    public Color getBackground() {
        return currentBackground;
    }

    public char getCharAt(int column, int row) {
        return data.getCharAt(column, row);
    }

    public Color getForegroundAt(int column, int row) {
        return data.getForegroundAt(column, row);
    }

    public Color getBackgroundAt(int column, int row) {
        return data.getBackgroundAt(column, row);
    }

    public Font getFontAt(int column, int row) {
        return data.getFontAt(column, row);
    }

    /**
     * Redirects System.out to this console by calling System.setOut
     */
    public void captureStdOut() {
        PrintStream ps = new PrintStream(System.out) {
            public void println(String x) {
                writeln(x);
            }
        };

        System.setOut(ps);
    }

    public void write(char c) {
        data.setDataAt(cursorX, cursorY, c, currentForeground,
                currentBackground, currentFont);
    }

    public void writeln(String line) {
        write(line);
        write('\n');
    }

    public void write(String string, Color foreGround, Color backGround) {
        Color foreTemp = currentForeground;
        Color backTemp = currentBackground;
        setForeground(foreGround);
        setBackground(backGround);
        write(string);
        setForeground(foreTemp);
        setBackground(backTemp);
    }

    public void write(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            write(c);
        }
    }

    public void fillArea(char c, Color fg, Color bg, int column, int row,
                         int width, int height) {
        data.fillArea(c, fg, bg, currentFont, column, row, width, height);
        repaintArea(column, row, width, height);
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
    }

}