/**
 * Minesweeper 
 * Martin Ricardo Del Rio Grageda
 * April 2020
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    private int mode = 1;
    private int diff = 1;
    private boolean practice = false;
    private Arena a;
    private Instruction i;
    final JFrame frame = new JFrame("MINESWEEPER");
    boolean playing = false;

    final static JPanel BOTTOM_PANEL = new JPanel();
    final static JPanel TOP_PANEL = new JPanel();
    final static JPanel GAME_PANEL = new JPanel();
    final static JPanel PANEL_MODE = new JPanel();
    final static JPanel PANEL_DIFF = new JPanel();
    
    final static JButton RESET = new JButton("Reset");
    final static JButton READY = new JButton("READY");
    final static JButton BACK = new JButton("BACK");

    public void run() {
        frame.setLocation(600, 20);
        BOTTOM_PANEL.setLayout(new GridLayout(3, 1));
        TOP_PANEL.setLayout(new GridLayout(2, 1));
        frame.add(GAME_PANEL, BorderLayout.CENTER);
        // Mode buttons
        JButton m1 = new JButton("PRACTICE");
        m1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = 1;
                practice = true;
            }
        });
        JButton m2 = new JButton("SURROUNDING");
        m2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = 2;
                practice = false;
            }
        });
        JButton m3 = new JButton("2-SURROUNDING");
        m3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = 3;
                practice = false;
            }
        });

        BACK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                a.goback();
            }
        });

        PANEL_MODE.add(m1);
        PANEL_MODE.add(m2);
        PANEL_MODE.add(m3);

        // Difficulty buttons
        JButton d1 = new JButton("EASY");
        d1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diff = 1;
            }
        });
        JButton d2 = new JButton("MEDIUM");
        d2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diff = 2;
            }
        });
        JButton d3 = new JButton("HARD");
        d3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diff = 3;
            }
        });

        PANEL_DIFF.add(d1);
        PANEL_DIFF.add(d2);
        PANEL_DIFF.add(d3);

        READY.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow();
            }
        });

        RESET.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructionsWindow();
            }
        });
        instructionsWindow();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Note here that when we add an action listener to the RESET button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.

        // Put the frame on the screen
    }

    public void instructionsWindow() {
        i = new Instruction();
        if (a != null) {
            GAME_PANEL.remove(a);
        }
        GAME_PANEL.add(i);
        TOP_PANEL.add(PANEL_DIFF);
        TOP_PANEL.remove(BACK);
        BOTTOM_PANEL.add(PANEL_MODE);
        BOTTOM_PANEL.remove(RESET);
        BOTTOM_PANEL.add(READY);
        frame.add(TOP_PANEL, BorderLayout.NORTH);
        frame.add(BOTTOM_PANEL, BorderLayout.SOUTH);
        frame.add(GAME_PANEL, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();
    }

    public void gameWindow() {
        a = new Arena(mode, diff, practice);
        GAME_PANEL.remove(i);
        GAME_PANEL.add(a);
        TOP_PANEL.remove(PANEL_DIFF);
        if (practice) {
            TOP_PANEL.add(BACK);
        } else {
            TOP_PANEL.remove(BACK);
        }
        BOTTOM_PANEL.remove(PANEL_MODE);
        BOTTOM_PANEL.add(RESET);
        BOTTOM_PANEL.remove(READY);
        frame.add(TOP_PANEL, BorderLayout.NORTH);
        frame.add(BOTTOM_PANEL, BorderLayout.SOUTH);
        frame.add(GAME_PANEL, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}