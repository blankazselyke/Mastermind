import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Converter is responsible for converting the guesses on the UI to numbers so the GameLogic can process it and converting the numeric code to Colors.
 */
public class Converter {
    /**
     * Converts ColorFillers to numbers according to the ColorPickers' colors. It examines which ColorPicker's color does the given ColorFiller have and the numeric form will be the ColorPicker's index.
     * @param guess ColorFillers from the board.
     * @param colors The colors the code can be made of.
     * @return Numeric form of the guess in an array.
     */
    public int[] convert(List<ColorFiller> guess, List<ColorPicker> colors){
        int[] guessint = new int[4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                if(guess.get(i).getColor().equals(colors.get(j).getColor())){
                    guessint[i] = j;
                }
            }
        }
        return guessint;
    }

    /**
     * It converts the numeric code to Colors. For each number the appropriate color will be the Colorpicker's color at the index of the number's value.
     * @param code The numeric code.
     * @param colors The colors the code can be made of.
     * @return The appropriate Colors of the code.
     */
    public List<Color> convert(int[] code, List<ColorPicker> colors){
        List<Color> codecolors = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            codecolors.add(colors.get(code[i]).getColor());
        }
        return codecolors;
    }
}
