import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Instruction extends JPanel {
    private static final int ARENA_WIDTH = 450;
    private static final int ARENA_HEIGHT = 450;
    private static final int ARENA_XPOS = 0;
    private static final int ARENA_YPOS = 0;

    private Color color = Color.BLACK;

    public Instruction() {
        this.setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createLineBorder(this.color));
        JLabel inst1 = new JLabel("Instructions:");
        JLabel inst2 = new JLabel("Choose the difficulty level using the buttons above");
        JLabel inst3 = new JLabel("Choose the game mode using the buttons below");
        JLabel inst4 = new JLabel("Practice mode lets you go back in time");
        JLabel inst5 = new JLabel("Surrounding gives you the mines around the spot you click");
        JLabel inst6 = new JLabel("Surrounging2 gives you the mines around the spot you "
                + "click and around two levels");
        JLabel inst7 = new JLabel("When you click a spot, it will show you this number");
        JLabel inst8 = new JLabel("If you click on a mine, you lose");
        JLabel inst9 = new JLabel("If you uncover all the non mine spots, you win");
        JLabel inst10 = new JLabel("Click ready to start");
        this.add(inst1);
        this.add(inst2);
        this.add(inst3);
        this.add(inst4);
        this.add(inst5);
        this.add(inst6);
        this.add(inst7);
        this.add(inst8);
        this.add(inst9);
        this.add(inst10);
        this.setLayout(new GridLayout(11, 1));
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(ARENA_XPOS, ARENA_YPOS, ARENA_WIDTH, ARENA_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ARENA_WIDTH, ARENA_HEIGHT);
    }

}
