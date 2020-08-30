import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

@SuppressWarnings("serial")
public class Arena extends JPanel {
    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 500;
    private static final int ARENA_XPOS = 0;
    private static final int ARENA_YPOS = 0;

    private Color color = Color.BLACK;
    private static final int INTERVAL = 30;

    private int mode;
    private int diff;
    private Spot[][] spots;
    private LinkedList<Spot[][]> moves;
    private int numOfInvFreespots;
    private boolean practice;
    private boolean hasWon;
    private boolean hasLost;

    public Arena(int mode, int diff, boolean practice) {
        this.mode = mode;
        this.diff = diff;
        this.practice = practice;
        hasWon = false;
        hasLost = false;
        if (practice) {
            moves = new LinkedList<Spot[][]>();
        }
        switch (this.diff) {
            case 1:
                spots = new Spot[10][10];
                break;
            case 2:
                spots = new Spot[15][15];
                break;
            case 3:
                spots = new Spot[20][20];
                break;
            default:
                throw new RuntimeException("Difficulty is null");
        }
        generateMine(spots);
        switch (this.mode) {
            case 1:
                countSurround(spots, 1);
                avoidZerosDiag(spots);
                countSurround(spots, 1);
                break;
            case 2:
                countSurround(spots, 1);
                avoidZerosDiag(spots);
                countSurround(spots, 1);
                break;
            case 3:
                countSurround(spots, 2);
                avoidZerosDiag(spots);
                countSurround(spots, 2);
                break;
            default:
                throw new RuntimeException("Mode is null");
        }
        numOfInvFreespots = calculNumOfInvFreespots(spots);

        this.repaint();

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = (int) ((e.getX()) / (ARENA_WIDTH / spots.length));
                int col = (int) ((e.getY()) / (ARENA_WIDTH / spots.length));
                if (e.getX() > ARENA_WIDTH || e.getY() > ARENA_WIDTH || 
                        hasLost || spots[row][col].isVisible()) {
                    return;
                } else {
                    if (practice) {
                        updateState();
                    }

                    if (spots[row][col].getNumOfMines() == 0) {
                        spots[row][col].setVisible(true);
                        numOfInvFreespots--;
                        checkZeros(spots, row, col);
                    } else {
                        if (spots[row][col].isMine()) {
                            hasLost = true;
                        } else {
                            spots[row][col].setVisible(true);
                            numOfInvFreespots--;
                        }
                    }

                }
            }
        });

        if (practice) {
            updateState();
        }
    }

    /**
     * Creates the mines in random places in the board
     * 
     * The total number of mines is determined based on the probability of an event
     * 
     * @param The 2D array containing the spots Modifies the array in place
     */
    private void generateMine(Spot[][] places) {
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[row].length; col++) {
                places[row][col] = new Spot(row, col, (ARENA_HEIGHT - 20) / places.length, false);
                if (Math.random() < .2) {
                    places[row][col].setMine(true);
                }
            }
        }
    }

    /**
     * Checks for groups of zeros so that it can make them visible at once Makes a
     * recursive call if it is also zero
     * 
     * @param The 2D array containing the spots, the row and the column of the Spot
     *            clicked Modifies the array in place
     */
    private void checkZeros(Spot[][] places, int row, int col) {
        for (int rr = row - 1; rr <= row + 1; rr++) {
            for (int rc = col - 1; rc <= col + 1; rc++) {
                if (!isValid(rr) || !isValid(rc) || rr == row && rc == col || 
                        places[rr][rc].isVisible()) {
                    continue;
                } else {
                    if (places[rr][rc].getNumOfMines() == 0 && !places[rr][rc].isVisible()) {
                        places[rr][rc].setVisible(true);
                        numOfInvFreespots--;
                        checkZeros(places, rr, rc);
                    } else {
                        places[rr][rc].setVisible(true);
                        numOfInvFreespots--;
                    }
                }
            }
        }
    }

    /**
     * Counts the number of surrounding mines up to a certain radius
     * 
     * @param The 2D array containing the spots, the int radius Modifies the array
     *            in place
     */
    private void countSurround(Spot[][] places, int radius) {
        int numOfMines;
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[row].length; col++) {
                numOfMines = 0;
                for (int rc = col - radius; rc <= col + radius; rc++) {
                    for (int rr = row - radius; rr <= row + radius; rr++) {
                        if (!isValid(rr) || !isValid(rc)) {
                            continue;
                        } else {
                            if (places[rr][rc].isMine()) {
                                numOfMines++;
                            }
                        }
                    }
                }
                places[row][col].setNumOfMines(numOfMines);
            }
        }
    }

    private boolean isValid(int coord) {
        return (!(coord < 0 || coord >= spots.length));
    }

    /**
     * Handles the specific case when a group of zeros is adjacent to another group
     * of zeros just diagonally.
     * 
     * In the case that it happens, it adds a new mine
     * 
     * @param The 2D array containing the spots Modifies the array in place
     */
    private void avoidZerosDiag(Spot[][] places) {
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[row].length; col++) {
                if (places[row][col].getNumOfMines() != 0) {
                    continue;
                } else {
                    // Case up right
                    if (isValid(row - 1) && isValid(col + 1)) {
                        if (places[row - 1][col].getNumOfMines() != 0 && 
                                places[row][col + 1].getNumOfMines() != 0
                                && places[row - 1][col + 1].getNumOfMines() == 0) {
                            places[row - 1][col + 1].setMine(true);
                            continue;
                        }
                    }
                    // Case up left
                    if (isValid(row - 1) && isValid(col - 1)) {
                        if (places[row - 1][col].getNumOfMines() != 0 && 
                                places[row][col - 1].getNumOfMines() != 0
                                && places[row - 1][col - 1].getNumOfMines() == 0) {
                            places[row - 1][col - 1].setMine(true);
                            continue;
                        }
                    }
                    // Case down left
                    if (isValid(row + 1) && isValid(col - 1)) {
                        if (places[row + 1][col].getNumOfMines() != 0 && 
                                places[row][col - 1].getNumOfMines() != 0
                                && places[row + 1][col - 1].getNumOfMines() == 0) {
                            places[row + 1][col - 1].setMine(true);
                            continue;
                        }
                    }
                    // Case down right
                    if (isValid(row + 1) && isValid(col + 1)) {
                        if (places[row + 1][col].getNumOfMines() != 0 && 
                                places[row][col + 1].getNumOfMines() != 0
                                && places[row + 1][col + 1].getNumOfMines() == 0) {
                            places[row + 1][col + 1].setMine(true);
                            continue;
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the number of non mine spots that haven't been uncovered
     * 
     * 
     * @param The 2D array containing the spots
     * @return Number of free invisible spots
     */
    private int calculNumOfInvFreespots(Spot[][] places) {
        int counter = 0;
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[row].length; col++) {
                if (!places[row][col].isMine() && !places[row][col].isVisible()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Makes the appropriate modifications to load the previous state of the game
     * 
     * Modifies the 2D array in place
     */
    public void goback() {
        hasWon = false;
        hasLost = false;
        if (!moves.isEmpty()) {
            Spot[][] last = moves.removeLast();
            for (int row = 0; row < spots.length; row++) {
                for (int col = 0; col < spots[row].length; col++) {
                    spots[row][col] = last[row][col];
                }
            }
        } else {
            return;
        }
    }

    void tick() {
        repaint();
        if (practice) {
            numOfInvFreespots = calculNumOfInvFreespots(spots);
        }
        if (numOfInvFreespots == 0) {
            hasWon = true;
        }
    }

    /**
     * Saves the current state of the game to the linked list moves
     * 
     * Modifies the list in place
     */
    public void updateState() {
        final Spot[][] state = new Spot[spots.length][spots.length];
        for (int r = 0; r < spots.length; r++) {
            for (int c = 0; c < spots[r].length; c++) {
                state[r][c] = new Spot(spots[r][c].getRow(), spots[r][c].getCol(), 
                        spots[r][c].getSize(),
                        spots[r][c].isMine());
                state[r][c].setSur1(spots[r][c].getSur1());
                state[r][c].setSur2(spots[r][c].getSur2());
                state[r][c].setColor(spots[r][c].getColor());
                state[r][c].setNumOfMines(spots[r][c].getNumOfMines());
                state[r][c].setVisible(spots[r][c].isVisible());
            }
        }
        moves.add(state);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.draw(g);
        if (!hasLost && !hasWon) {
            for (int row = 0; row < spots.length; row++) {
                for (int col = 0; col < spots[row].length; col++) {
                    spots[row][col].draw(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ARENA_WIDTH, ARENA_HEIGHT);
    }

    public void draw(Graphics g) {
        Font countFont = new Font("SansSerif", Font.PLAIN, 20);
        g.setFont(countFont);
        g.drawString("Number of tiles left = " + numOfInvFreespots, 0, ARENA_HEIGHT);
        g.setColor(this.color);
        g.drawRect(ARENA_XPOS, ARENA_YPOS, this.getWidth(), this.getHeight());
        if (hasLost) {
            Font numFont = new Font("SansSerif", Font.PLAIN, ARENA_WIDTH / 6);
            g.setFont(numFont);
            g.drawString("You LOSE!", 0, ARENA_WIDTH / 2);
            g.drawString("Please reset", 0, ARENA_WIDTH / 2 + ARENA_WIDTH / 6);
            if (practice) {
                g.drawString("or go back", 0, ARENA_WIDTH / 2 + 2 * ARENA_WIDTH / 6);
            }
        } else if (hasWon) {
            Font numFont = new Font("SansSerif", Font.PLAIN, ARENA_WIDTH / 6);
            g.setFont(numFont);
            g.drawString("You WIN!", 0, ARENA_WIDTH / 2);
            g.drawString("Please reset", 0, ARENA_WIDTH / 2 + ARENA_WIDTH / 6);
            if (practice) {
                g.drawString("or go back", 0, ARENA_WIDTH / 2 + 2 * ARENA_WIDTH / 6);
            }
        }
    }

}
