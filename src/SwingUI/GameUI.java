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
import GameLogic.Coord;

public class GameUI extends JFrame {

    public static final Color VISITED = Color.BLUE;
    public static final Color CURRENT = Color.GREEN;
    public static final Color UNVISITED = Color.RED;
    public static final Color END = Color.MAGENTA;
    public static final Color EMPTY = Color.BLACK;

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

                // The current cell
                if(rw == this.grid.curCoord().row
                        && cl == this.grid.curCoord().col) {

                    this.labels[rw][cl].setBackground(GameUI.CURRENT);
                }

                // An end cell
                else if(rw == this.grid.endCoord().row
                        && cl == this.grid.endCoord().col) {

                    this.labels[rw][cl].setBackground(GameUI.END);
                }

                // A FloorTile, visited or not
                else if(current instanceof FloorTile) {
                    this.labels[rw][cl].setBackground(
                            ((FloorTile) current).visited() ?
                            GameUI.VISITED : GameUI.UNVISITED);
                }

                // An EmptySpace
                else {
                    assert(current instanceof EmptySpace);
                    this.labels[rw][cl].setBackground(GameUI.EMPTY);
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
        Grid g = new Grid(arr, new Coord(0, 0), new Coord(2, 2));
        GameUI ui = new GameUI(g);
    }
}
