import java.awt.*;

public class Spot {
    private int size, row, col, sur1, sur2, numOfMines;
    private boolean mine, visible;
    private Color color;

    public Spot(int row, int col, int size, boolean isMine) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.mine = isMine;

    }

    public void draw(Graphics g) {
        int xPos = (this.row) * this.size;
        int yPos = (this.col) * this.size;

        if ((this.col % 2 == 0 && this.row % 2 == 0) || (this.col % 2 == 1 && this.row % 2 == 1)) {
            g.setColor(new Color(0, 153, 0));
        } else {
            g.setColor(new Color(0, 204, 0));
        }
        g.fillRect(xPos, yPos, this.size, this.size);

        if (visible) {
            if (mine) {
                g.setColor(Color.RED);
                g.fillArc(xPos, yPos, this.size - 2, this.size - 2, 0, 360);
            } else {
                if (this.getNumOfMines() == 0) {
                    g.setColor(new Color(0, 255, 255));
                    g.fillRect(xPos, yPos, this.size, this.size);
                } else {
                    g.setColor(new Color(153, 76, 0));
                    g.setColor(Color.BLACK);
                    Font numFont = new Font("SansSerif", Font.PLAIN, this.size / 2);
                    g.setFont(numFont);
                    g.drawString("" + this.numOfMines, xPos + this.size / 2, 
                            yPos + this.size / 2 + 8);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getSur1() {
        return sur1;
    }

    public void setSur1(int sur1) {
        this.sur1 = sur1;
    }

    public int getSur2() {
        return sur2;
    }

    public void setSur2(int sur2) {
        this.sur2 = sur2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public void setVisible(boolean isVis) {
        this.visible = isVis;
    }

    public boolean isVisible() {
        return visible;
    }

}
