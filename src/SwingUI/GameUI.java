package SwingUI;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;

import GameLogic.Grid;
import GameLogic.Cell;
import GameLogic.FloorTile;
import GameLogic.EmptySpace;
import GameLogic.InvalidMoveException;

public class GameUI extends JFrame {

    private JPanel panel;
    private JLabel[][] labels;
    private Grid grid;

    public GameUI(Grid grid) {
        this.grid = grid;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new JPanel(
            new GridLayout(
                this.grid.numRows(),
                this.grid.numCols()
            )
        );

        this.labels = new JLabel[this.grid.numRows()][this.grid.numCols()];

        for(int rw = 0; rw < this.grid.numRows(); rw++) {
            for(int cl = 0; cl < this.grid.numCols(); cl++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                this.panel.add(label);
                this.labels[rw][cl] = label;
            }
        }

        this.add(this.panel);
        this.panel.addKeyListener(new GameUIKeyListener(this));
        this.panel.setFocusable(true);
        this.setVisible(true);
        this.updateGridDisplay();
    }

    public void updateGridDisplay() {
        for(int rw = 0; rw < this.grid.numRows(); rw++) {
            for(int cl = 0; cl < this.grid.numCols(); cl++) {
                Cell current = this.grid.cellAt(rw, cl);
                if(rw == this.grid.curRow() && cl == this.grid.curCol()) {
                    this.labels[rw][cl].setBackground(Color.GREEN);
                }
                else if(rw == this.grid.endRow && cl == this.grid.endCol) {
                    this.labels[rw][cl].setBackground(Color.MAGENTA);
                }
                else if(current instanceof FloorTile) {
                    this.labels[rw][cl].setBackground(
                            ((FloorTile) current).visited() ?
                            Color.BLUE : Color.RED);
                }
                else {
                    assert(current instanceof EmptySpace);
                    this.labels[rw][cl].setBackground(Color.BLACK);
                }
            }
        }
    }

    private class GameUIKeyListener implements KeyListener {

        private GameUI owner;

        public GameUIKeyListener(GameUI owner) {
            this.owner = owner;
        }

        public void keyReleased(KeyEvent e) { /* Do nothing */ }
        public void keyTyped(KeyEvent e) { /* Do nothing */ }

        public void keyPressed(KeyEvent e) {
            try {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    owner.grid.doMove(Grid.UP);
                }
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    owner.grid.doMove(Grid.RIGHT);
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    owner.grid.doMove(Grid.DOWN);
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    owner.grid.doMove(Grid.LEFT);
                }
            }
            catch(InvalidMoveException ime) { /* Do nothing */ }
            owner.updateGridDisplay();
        }
    }

    /**
     * A little test of Grid and UI objects.
     * TODO remove this method from final build
     */
    public static void main(String[] args) {
        boolean[][] arr = new boolean[][] {
            {true, true, true},
            {true, true, true},
            {true, true, true}
        };
        Grid g = new Grid(arr, 0, 0, 2, 2);
        GameUI ui = new GameUI(g);
    }
}
