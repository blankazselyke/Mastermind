import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Row represents a row on the board. It stores ColorFillers and LogicPegs.
 * </p>
 */
public class Row implements Serializable {
    List<ColorFiller> colorpegs = new ArrayList<>();
    List<LogicPeg> logicpegs = new ArrayList<>();

}
