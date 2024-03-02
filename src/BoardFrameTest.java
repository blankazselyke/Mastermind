import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static junit.framework.TestCase.*;


public class BoardFrameTest {
    BoardFrame boardFrame = new BoardFrame();

    @Before
    public void setUp(){
        for(int i = 0; i < 4; i++){
            boardFrame.getBoard().rows.get(0).colorpegs.get(i).setColor(Color.RED);
        }
        for(int i = 0; i < 6; i++){
            boardFrame.getBoard().pickpegs.get(i).setColor(Color.ORANGE);
        }
    }

    @Test
    public void testReset(){
        boardFrame.reset();
        boolean red = false;
        for(int i = 0; i < 4; i++){
            if(boardFrame.getBoard().rows.get(0).colorpegs.get(i).getColor().equals(Color.RED)){
                red = true;
            }
        }
        assertFalse(red);
    }

    @Test
    public void testDefault(){
        boardFrame.defaultcolors();
        assertEquals(boardFrame.getBoard().pickpegs.get(0).getColor(), Color.RED);
        assertEquals(boardFrame.getBoard().pickpegs.get(1).getColor(), Color.BLUE);
        assertEquals(boardFrame.getBoard().pickpegs.get(2).getColor(), Color.GREEN);
        assertEquals(boardFrame.getBoard().pickpegs.get(3).getColor(), Color.YELLOW);
        assertEquals(boardFrame.getBoard().pickpegs.get(4).getColor(), Color.MAGENTA);
        assertEquals(boardFrame.getBoard().pickpegs.get(5).getColor(), Color.CYAN);
    }

    @Test
    public void testSaveBoard() throws IOException, ClassNotFoundException {
        boardFrame.saveboard("boardtest.txt");
        BoardFrame control = boardFrame;
        boardFrame.reset();
        boardFrame.defaultcolors();
        boardFrame.loadboard("boardtest.txt");
        assertEquals(control, boardFrame);
    }

    @Test
    public void testSaveCode() throws IOException, ClassNotFoundException {
        boardFrame.getGame().newCode();
        boardFrame.savecode("codetest.txt");
        GameLogic control = boardFrame.getGame();
        boardFrame.loadcode("codetest.txt");
        assertEquals(control, boardFrame.getGame());
    }

    @Test
    public void testGuess(){
        boardFrame.getGame().setCode(new int[]{0, 0, 0, 0});
        boardFrame.makeGuess();
        boolean black = true;
        for(int i = 0; i < 4; i++){
            if (boardFrame.getBoard().rows.get(0).logicpegs.get(i).getColor() != Color.black) {
                black = false;
                break;
            }
        }
        assertTrue(black);
        assertTrue(boardFrame.end);

    }
}
