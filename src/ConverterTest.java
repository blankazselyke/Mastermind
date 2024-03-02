import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ConverterTest {

    List<ColorFiller> guess = new ArrayList<ColorFiller>();
    Board board = new Board();
    Converter c = new Converter();
    @Before
    public void setUp(){

    }

    @Test
    public void testColorToInt(){
        for(int i = 0; i < 4; ++i){
            guess.add(new ColorFiller(120, 120, 20, 255, 0, 0));
        }
        guess.get(1).setColor(Color.BLUE);
        guess.get(2).setColor(Color.GREEN);
        guess.get(3).setColor(Color.YELLOW);
        int[] guessint = {0, 1, 2, 3};
        int[] guesscolor = c.convert(guess, board.pickpegs);
        Assert.assertArrayEquals(guessint, guesscolor);
    }

    @Test
    public void testIntToColor(){
        int[] code = {0, 1, 2, 3};
        List<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        List<Color> codecolors = c.convert(code, board.pickpegs);
        assertEquals(codecolors, colors);
    }
}
