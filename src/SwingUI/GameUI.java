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
import java.awt.Dimension;

import GameLogic.Grid;
import GameLogic.Cell;
import GameLogic.FloorTile;
import GameLogic.EndFloorTile;
import GameLogic.EmptySpace;
import GameLogic.InvalidMoveException;
import GameLogic.Coord;

import Levels.Level;

public class GameUI extends JFrame {

    public static final Color VISITED = new Color(0, 43, 54); // Dark grey/green
    public static final Color UNVISITED = new Color(38, 139, 210); // Blue
    public static final Color CURRENT = new Color(133, 153, 0); // Green
    public static final Color END = new Color(211, 54, 130); // Magenta
    public static final Color EMPTY = new Color(238, 232, 213); // Off-white

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
        this.setSize(new Dimension(500, 500));
        this.updateGridDisplay();
    }

    public void updateGridDisplay() {
        for(int rw = 0; rw < this.grid.numRows(); rw++) {
            for(int cl = 0; cl < this.grid.numCols(); cl++) {
                Cell current = this.grid.cellAt(rw, cl);

                // The current cell, which always has colour precedence
                if(rw == this.grid.curCoord().row
                        && cl == this.grid.curCoord().col) {

                    this.labels[rw][cl].setBackground(GameUI.CURRENT);
                }

                // A FloorTile - has visited colour if visited wheter or not
                // it's an end. If not visited, end colour has precedence over
                // visited colour.
                else if(current instanceof FloorTile) {
                    // not visited and is and end
                    if(!((FloorTile) current).visited()
                            && (current instanceof EndFloorTile)) {

                        this.labels[rw][cl].setBackground(GameUI.END);
                    }

                    // not visited and not an end
                    else if(!((FloorTile) current).visited()
                            && (!(current instanceof EndFloorTile))) {
                        this.labels[rw][cl].setBackground(GameUI.UNVISITED);
                    }

                    // visited, end or not an end
                    else {
                        assert(((FloorTile) current).visited());
                        this.labels[rw][cl].setBackground(GameUI.VISITED);
                    }
                }

                // An EmptySpace - always has empty colour.
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
        new GameUI(new Grid(Level.levels.get(1)));
    }
}
