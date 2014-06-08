package SwingUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import GameLogic.Grid;
import GameLogic.Cell;
import GameLogic.FloorTile;
import GameLogic.EmptySpace;
import GameLogic.InvalidMoveException;

public class GameUI extends JFrame {

    private JPanel panel;
    private Grid grid;

    public GameUI(Grid grid) {
        this.grid = grid;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new GameUIKeyListener(this));
        this.setFocusable(true);
    }

    public void updateGridDisplay() {
        this.panel = new JPanel(
            new GridLayout(
                this.grid.numRows(),
                this.grid.numCols()
            )
        );

        for(int rw = 0; rw < this.grid.numRows(); rw++) {
            for(int cl = 0; cl < this.grid.numCols(); cl++) {
                Cell toAdd = this.grid.cellAt(rw, cl);
                if(rw == this.grid.endRow && cl == this.grid.endCol) {
                    this.panel.add(new JLabel(
                        new ImageIcon("SwingUI/purple.gif")));
                }
                else if(rw == this.grid.curRow() && cl == this.grid.curCol()) {
                    this.panel.add(new JLabel(
                        new ImageIcon("SwingUI/green.gif")));
                }
                else if(toAdd instanceof FloorTile) {
                    this.panel.add(new JLabel(
                        new ImageIcon("SwingUI/" +
                            (((FloorTile) toAdd).visited() ?
                            "blue" : "red") + ".gif")));
                }
                else /* if(toAdd instanceof EmptySpace) */ {
                    this.panel.add(new JLabel(
                        new ImageIcon("SwingUI/black.gif")));
                }
            }
        }
        this.add(this.panel);
        this.pack();
        this.setVisible(true);
    }

    private class GameUIKeyListener implements KeyListener {

        private GameUI owner;

        public GameUIKeyListener(GameUI owner) {
            this.owner = owner;
        }

        public void keyPressed(KeyEvent e) { /* Do nothing */ }
        public void keyReleased(KeyEvent e) { /* Do nothing */ }

        public void keyTyped(KeyEvent e) {
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
            catch(InvalidMoveException ime) {
                System.out.println("WARNING: Invalid move");
            }
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
        System.out.print(g);
        for(boolean b : g.availableMoves()) System.out.print(b + " ");
        GameUI ui = new GameUI(g);
        ui.updateGridDisplay();
    }
}
