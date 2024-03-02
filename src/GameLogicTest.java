import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class GameLogicTest {

    GameLogic gamelogic = new GameLogic();

    @Test
    public void test4Blacks(){
        gamelogic.newCode();
        int[] code = gamelogic.getCode();
        int[] feedback = gamelogic.guess(code);
        assertEquals(feedback[0], 4);
    }
    @Test
    public void test1Black1White(){
        int[] code = {0, 1, 2, 3};
        gamelogic.setCode(code);
        int[] guess = {0, 3, 4, 5};
        int[] feedback = gamelogic.guess(guess);
        assertEquals(feedback[0], 1);
        assertEquals(feedback[1], 1);
    }
    @Test
    public void testDuplicatesInCode(){
        int[] code = {0, 1, 2, 2};
        gamelogic.setCode(code);
        int[] guess = {0, 2, 4, 5};
        int[] feedback = gamelogic.guess(guess);
        assertEquals(feedback[0], 1);
        assertEquals(feedback[1], 1);
    }


}
