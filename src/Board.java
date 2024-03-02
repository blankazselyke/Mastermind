import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Board represents the board in the game. It stores the ColorPickers from which the player can choose colors, Rows and the number of the actual row.
 */
public class Board implements Serializable {
    List<ColorPicker> pickpegs = new ArrayList<>();
    List<Row> rows = new ArrayList<>();
    int actualrow = 0;

    {
        /**
         * Creating and initializing the ColorPickers.
         */
        pickpegs.add(new ColorPicker(300, 120, 20, 255, 0, 0));
        pickpegs.add(new ColorPicker(300, 150, 20, 0, 0, 255));
        pickpegs.add(new ColorPicker(300, 180, 20, 0, 255, 0));
        pickpegs.add(new ColorPicker(300, 210, 20, 255, 255, 0));
        pickpegs.add(new ColorPicker(300, 240, 20, 255, 0, 255));
        pickpegs.add(new ColorPicker(300, 270, 20, 0, 255, 255));

        /**
         * Creating and initializing the Rows.
         */
        for(int i = 0; i < 12; ++i){
            Row r = new Row();
            for(int j = 0; j < 4; ++j){
                r.colorpegs.add(new ColorFiller(120 + j * 30, 120 + i * 30, 20, 194, 122, 79));
            }
            r.logicpegs.add(new LogicPeg(250, 120 + i * 30,  8, 194, 122, 79));
            r.logicpegs.add(new LogicPeg(270, 120 + i * 30,  8, 194, 122, 79));
            r.logicpegs.add(new LogicPeg(250, 130 + i * 30,  8, 194, 122, 79));
            r.logicpegs.add(new LogicPeg(270, 130 + i * 30,  8, 194, 122, 79));
            rows.add(r);
        }

        /**
         * Setting default picked ColorPicker to the first one.
         */
        pickpegs.get(0).setPicked(true);
        pickpegs.get(0).setW(3);

    }

}
