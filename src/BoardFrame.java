import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.List;

/**
 * BoardFrame is responsible for controlling the game and the display.
 */
public class BoardFrame extends JFrame implements Serializable {
    /**
     * The board in the game.
     */
    private Board board = new Board();
    /**
     * The actually chosen Color.
     */
    private Color color = Color.red;
    /**
     * Stores if the game has reached its end.
     */
    boolean end = false;

    /**
     * The GameLogic to this game, storing the code.
     */
    private GameLogic game = new GameLogic();

    private static JPanel wingame = new JPanel();
    private static JPanel losegame = new JPanel();

    private static JPanel gamestate = new JPanel(new CardLayout());
    public void setColor(Color c){
        color = c;
    }

    /**
     * Puts together the Panel, adds the appropriate Labels, JMenus etc. Generates a new code and sets end false.
     */
    public BoardFrame(){
        super("Mastermind");
        setLayout(new BorderLayout());
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(new PickListener());
        JPanel jp = new JPanel();
        JButton guess = new JButton("Guess");
        guess.addActionListener(new GuessListener());
        jp.add(guess);
        add(jp, BorderLayout.NORTH);

        JLabel win = new JLabel("Congratulations, you won! :)");
        wingame.add(win);

        JLabel lost = new JLabel("You lost! :(");
        losegame.add(lost);

        gamestate.add(wingame);
        gamestate.add(losegame);

        add(gamestate, BorderLayout.SOUTH);
        gamestate.setVisible(false);

        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(new ResetListener());
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new SaveListener());
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new LoadListener());
        JMenuItem editcolors = new JMenuItem("Edit colors");
        editcolors.addActionListener(new EditListener());
        JMenuItem def = new JMenuItem("Default colors");
        def.addActionListener(new DefaultListener());
        file.add(save);
        file.add(load);
        edit.add(reset);
        edit.add(def);
        edit.add(editcolors);
        bar.add(file);
        bar.add(edit);

        this.setJMenuBar(bar);
        end = false;
        game.newCode();
    }

    /**
     * Draws the board based on the parameters of the drawn objects.
     * @param g
     */
    void drawBoard(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(194, 122, 79));
        g2d.fillRect(100, 100, 250, 410);
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(100, 100, 250, 410);

        for(Peg pickpeg : board.pickpegs){
            g2d.setStroke(new BasicStroke(pickpeg.getW()));
            g2d.setColor(pickpeg.getColor());
            g2d.fillOval(pickpeg.getX(), pickpeg.getY(), pickpeg.getR(), pickpeg.getR());
            g2d.setColor(Color.black);
            g2d.drawOval(pickpeg.getX(), pickpeg.getY(), pickpeg.getR(), pickpeg.getR());
        }

        for(Row r : board.rows){
            for(Peg colorpeg : r.colorpegs){
                g2d.setStroke(new BasicStroke(colorpeg.getW()));
                g2d.setColor(colorpeg.getColor());
                g2d.fillOval(colorpeg.getX(), colorpeg.getY(), colorpeg.getR(), colorpeg.getR());
                g2d.setColor(Color.black);
                g2d.drawOval(colorpeg.getX(), colorpeg.getY(), colorpeg.getR(), colorpeg.getR());
            }
            for(Peg logicpeg : r.logicpegs){
                g2d.setStroke(new BasicStroke(logicpeg.getW()));
                g2d.setColor(logicpeg.getColor());
                g2d.fillOval(logicpeg.getX(), logicpeg.getY(), logicpeg.getR(), logicpeg.getR());
                g2d.setColor(Color.black);
                g2d.drawOval(logicpeg.getX(), logicpeg.getY(), logicpeg.getR(), logicpeg.getR());
            }
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g);
    }

    public void refresh(){
        this.repaint();
    }

    public Board getBoard(){
        return board;
    }

    public GameLogic getGame(){
        return game;
    }

    /**
     * Resets the game. It deletes the colors from the rows adn hides the solution if there was one. Sets the actual row to 0, the end to false, the gamestate to not visible and generates a new code.
     */
    public void reset(){
        for(int i = 0; i < 12; ++i){
            Row r = board.rows.get(i);
            for(Peg p : r.colorpegs){
                p.setColor(new Color(194, 122, 79));
            }
            for(Peg p : r.logicpegs){
                p.setColor(new Color(194, 122, 79));
            }
        }
        if(board.rows.size() == 13){
            board.rows.remove(12);
        }
        repaint();
        board.actualrow = 0;
        end = false;
        game.newCode();
        gamestate.setVisible(false);
    }

    /**
     * Sets the ColorPickers to the default colors and the picked ColorPicker to the first one.
     */
    public void defaultcolors(){
        board.pickpegs.get(0).setColor(new Color(255, 0, 0));
        board.pickpegs.get(1).setColor(new Color(0, 0, 255));
        board.pickpegs.get(2).setColor(new Color(0, 255, 0));
        board.pickpegs.get(3).setColor(new Color(255, 255, 0));
        board.pickpegs.get(4).setColor(new Color(255, 0, 255));
        board.pickpegs.get(5).setColor(new Color(0, 255, 255));
        for (ColorPicker p : board.pickpegs) {
            p.setPicked(false);
            p.setW(1);
        }
        board.pickpegs.get(0).setPicked(true);
        board.pickpegs.get(0).setW(3);
        color = Color.red;
        repaint();
    }

    /**
     * Saves the board.
     * @param filename
     * @throws IOException
     */
    public void saveboard(String filename) throws IOException {
        FileOutputStream f = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(f);
        out.writeObject(board);
        out.close();
    }

    /**
     * Saves the game that stores the code.
     * @param filename
     * @throws IOException
     */
    public void savecode(String filename) throws IOException {
        FileOutputStream f = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(f);
        out.writeObject(game);
        out.close();
    }

    /**
     * Loads the board.
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadboard(String filename) throws IOException, ClassNotFoundException{
        FileInputStream f = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(f);
        board = (Board)in.readObject();
        in.close();
    }

    /**
     * Loads the game that stores the code.
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadcode(String filename) throws IOException, ClassNotFoundException{
        FileInputStream f = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(f);
        GameLogic loadgame = (GameLogic)in.readObject();
        game.code = loadgame.code;
        in.close();
    }

    /**
     * Shows the winning label.
     */
    public static void win(){
        gamestate.setVisible(true);
        CardLayout c1 = (CardLayout)(gamestate.getLayout());
        c1.first(gamestate);
    }

    /**
     * Shows the losing label and the solution.
     */
    public void lose(){
        gamestate.setVisible(true);
        CardLayout c1 = (CardLayout)(gamestate.getLayout());
        c1.last(gamestate);
        Row solution = new Row();
        Converter converter = new Converter();
        List<Color> codecolors = converter.convert(game.code, board.pickpegs);
        for(int i = 0; i < 4; ++i){
            Color actual = codecolors.get(i);
            solution.colorpegs.add(new ColorFiller(120 + i * 30, 120 + 12 * 30, 20, actual.getRed(), actual.getGreen(), actual.getBlue()));
        }
        board.rows.add(solution);
        repaint();
    }

    /**
     * Implements the guessing process. Gets the actual row and if every ColorFiller's color has been set, it converts the ColorFillers to numbers, gets the feedback from GameLogic, checks if the player
     * has won or lost the game and sets the LogicPegs based on the feedback.
     */
    public void makeGuess(){
        Row actual = board.rows.get(board.actualrow);
        boolean empty = false;
        for(ColorFiller peg : actual.colorpegs){
            if(peg.getColor().equals(new Color(194, 122, 79))){
                empty = true;
            }
        }
        if(!empty && !end){
            Converter c = new Converter();
            int[] guess = c.convert(actual.colorpegs, board.pickpegs);
            int[] logic = game.guess(guess);
            int black = logic[0];
            if(black == 4){
                win();
                end = true;
            }
            int white = logic[1];
            int counter = 0;
            for(int i = 0; i < black; i++){
                actual.logicpegs.get(i).setColor(Color.black);
                counter++;
            }
            for(int i = counter; i < counter + white; i++){
                actual.logicpegs.get(i).setColor(Color.white);
            }
            if(board.actualrow < 11){
                board.actualrow++;
            }
            else{
                lose();
                end = true;
            }
            repaint();
        }
    }

    /**
     * When the mouse is clicked, it checks for every clickable object if the mouse's coordinates are within its area and implements the appropriate behaviour.
     */
    class PickListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!end){
                for(ColorPicker peg : board.pickpegs){
                    if (peg.inArea(e.getX(), e.getY())) {
                        color = peg.pick();
                        for (ColorPicker p : board.pickpegs) {
                            p.setPicked(false);
                            p.setW(1);
                        }
                        peg.setPicked(true);
                        peg.setW(3);
                        repaint();
                        break;
                    }
                }

                for(int i = 0; i < 4; i++){
                    Row actual = board.rows.get(board.actualrow);
                    for(ColorFiller peg : actual.colorpegs){
                        if (peg.inArea(e.getX(), e.getY())) {
                            peg.pick(color);
                            repaint();
                            break;
                        }
                    }
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    class GuessListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            makeGuess();
        }
    }

    class ResetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

    /**
     * Saves the board and the code.
     */
    class SaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                saveboard("board.txt");
                savecode("code.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Loads the board and the code and sets the color to the picked ColorPicker's color.
     */
    class LoadListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                loadboard("board.txt");
                loadcode("code.txt");
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            for(ColorPicker cp : board.pickpegs){
                if(cp.getPicked()){
                    color = cp.getColor();
                }
            }
            repaint();
        }
    }

    class DefaultListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            defaultcolors();
        }
    }

    /**
     * Implements the color editing.
     */
    public class ColorChooser extends JPanel implements ChangeListener {
        private JColorChooser cc;

        /**
         * Gets the ColorPicker, the color of which is going to be changed, and sets it to the default color.
         */
        public ColorChooser(){
            ColorPicker picked = board.pickpegs.get(0);
            for(ColorPicker p : board.pickpegs) {
                if (p.getPicked()) {
                    picked = p;
                }
            }
            cc = new JColorChooser(picked.getColor());
            cc.getSelectionModel().addChangeListener(this);
            add(cc, BorderLayout.CENTER);
        }

        /**
         * Gets the ColorPicker, the color of which is going to be changed, and sets the new color.
         * @param e  a ChangeEvent object
         */
        public void stateChanged(ChangeEvent e) {
            ColorPicker picked = board.pickpegs.get(0);
            for(ColorPicker p : board.pickpegs) {
                if (p.getPicked()) {
                    picked = p;
                }
            }
            picked.setColor(cc.getColor());
            setColor(cc.getColor());
            refresh();
        }

        /**
         * Responsible for the display.
         */
        private void createGUI(){
            JFrame frame = new JFrame("Color chooser");
            JComponent newContentPane = new ColorChooser();
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);

            frame.pack();
            frame.setVisible(true);
        }
    }

    class EditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ColorChooser c = new ColorChooser();
            c.createGUI();
        }
    }

}
