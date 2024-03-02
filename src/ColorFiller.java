import java.awt.*;

/**
 * <p>
 * ColorFiller represents a peg in the game that the player can change the color of. It is the peg used for guessing. ColorFiller inherits from Peg.
 * </p>
 */
public class ColorFiller extends Peg{
    public ColorFiller(int xpos, int ypos, int radius, int rcolor, int gcolor, int bcolor) {
        super(xpos, ypos, radius, rcolor, gcolor, bcolor);
    }
    /**
     * <p>
     * When a ColorFiller is picked, it changes its color to the given one.
     * </p>
     * @param c The given color.
     */
    public void pick(Color c) {
        this.setColor(c);
    }
}
