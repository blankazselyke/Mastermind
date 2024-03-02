import java.awt.*;
import java.io.Serializable;

/**
 * <p>
 * Peg is an abstract class that represents a peg in the game. It is the superclass of 3 types of pegs. On the board it is drawn as a circle.
 * </p>
 */
public abstract class Peg implements Serializable {
    /**
     * x position of the cirle's center
     */
    private int x;
    /**
     * y position of the cirle's center
     */
    private int y;
    /**
     * radius of the circle
     */
    private int r;
    /**
     * width of the circle's outline
     */
    private int w = 1;
    /**
     * color of the circle
     */
    private Color color = new Color(194, 122, 79);
    /**
     * Peg's constructor.
     */
    public Peg(int xpos, int ypos, int radius, int rcolor, int gcolor, int bcolor){
        x = xpos;
        y = ypos;
        r = radius;
        color = new Color(rcolor, gcolor, bcolor);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        this.color = c;
    }
    /**
     * <p>
     * This function calculates whether a point given with its x and y coordinates is within the area of the circle.
     * For the calculation it uses the Pythagorean theorem.
     * </p>
     * @return True, if the point is within the area of the circle.
     */
    public boolean inArea(int xpos, int ypos){
        int xdistance = x - xpos;
        int ydistance = y - ypos;
        return xdistance * xdistance + ydistance * ydistance <= r*r;
    }

}
