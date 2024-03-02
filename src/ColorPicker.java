import java.awt.*;
/**
 * <p>
 * ColorPicker represents a peg in the game from which the player can choose color. ColorPicker inherits from Peg. When picked, the width of its outline gets thicker.
 * </p>
 */
public class ColorPicker extends Peg{
    /**
     * It is true if the ColorPicker is picked.
     */
    private boolean picked = false;

    boolean getPicked(){
        return picked;
    }

    void setPicked(boolean b){
        picked = b;
    }

    public ColorPicker(int xpos, int ypos, int radius, int rcolor, int gcolor, int bcolor) {
        super(xpos, ypos, radius, rcolor, gcolor, bcolor);
    }
    /**
     * <p>
     * When a ColorPicker is picked, it returns its color.
     * </p>
     * @return The ColorPicker's color.
     */
    public Color pick() {
        return this.getColor();
    }
}
