import java.io.Serializable;
import java.util.Random;

/**
 * <p>
 * GameLogic is responsible for generating a random code and giving feedback about a guess.
 * </p>
 */
public class GameLogic implements Serializable {
    /**
     * The secret code in integers.
     */
    int[] code = new int[4];

    public int[] getCode(){
        return code;
    }

    public void setCode(int[] code) {
        this.code = code;
    }

    /**
     * Generates a new random code from numbers 0, 1, 2, 3, 4, 5.
     */
    public void newCode(){
        Random random = new Random();
        for(int i = 0; i < 4; i++){
            code[i] = random.nextInt(6);
        }
    }

    /**
     * <p>
     * Gives feedback about a given guess.
     * </p>
     * @param guess Array of the four guessed numbers.
     * @return An array that contains the number of black and white pegs.
     */
    public int[] guess(int[] guess){
        /**
         * num is the number that the guessed numbers will be changed to
         */
        int num = -1;
        /**
         * c is for indexing the correct guesses
         */
        int c = 0;
        /**
         * correct stores the correct guesses, initialized -1
         */
        int[] correct = new int[4];
        for(int i = 0; i < 4; ++i){
            correct[i] = -1;
        }
        /**
         * a copy is made of the guess and the code, because they are going to be changed
         */
        int[] guesscopy = new int[4];
        int[] codecopy = new int[4];
        for(int i = 0; i < 4; ++i){
            guesscopy[i] = guess[i];
            codecopy[i] = code[i];
        }
        /**
         * number of black pegs (same position, same value)
         */
        int black = 0;
        /**
         * for every number that has the same position and value in the guess and the code, the number of black pegs is increased and the number is added to the correct ones
         */
        for(int i = 0; i < 4; ++i){
            if(codecopy[i] == guesscopy[i]){
                black++;
                correct[c++] = codecopy[i];
            }
        }
        /**
         * for every number in the code and the guess, if it equals with a correct number, it is changed to a unique negative integer (num)
         */
        for(int i = 0; i < 4; ++i){
            for(int j = 0; j < 4; ++j){
                if(correct[i] != -1 && codecopy[j] == correct[i]){
                    codecopy[j] = num--;
                }
                if(correct[i] != -1 && guesscopy[j] == correct[i]){
                    guesscopy[j] = num--;
                }
            }

        }


        /**
         * number of white pegs (wrong position, same value)
         */
        int white = 0;
        /**
         * for every number in guesscopy that equals a number in codecopy white is increased
         */
        for(int i = 0; i < 4; ++i){
            for(int j = 0; j < 4; ++j){
                if(codecopy[i] == guesscopy[j]){
                    white++;
                    /**
                     * after a good guess, the guessed number is changed to a unique negative number (num) in the code and the guess in order to avoid duplicate feedbacks for the same number
                     */
                    int guessed = codecopy[i];
                    for(int k = 0; k < 4; ++k){
                        if(codecopy[k] == guessed){
                            codecopy[k] = num--;
                        }
                        if(guesscopy[k] == guessed){
                            guesscopy[k] = num--;
                        }
                    }
                }
            }
        }

        return new int[]{black, white};

    }
}
