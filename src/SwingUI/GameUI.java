package SwingUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;

import GameLogic.Grid;
import GameLogic.Cell;
import GameLogic.FloorTile;
import GameLogic.EmptySpace;

public class GameUI extends JFrame {

    private JPanel panel;
    private Grid grid;

    public GameUI(Grid grid) {
        this.grid = grid;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
                if(this.grid.cellAt(rw, cl) instanceof FloorTile) {
                    this.panel.add(new JLabel(new ImageIcon("SwingUI/red.gif")));
                }
                // else if ...
            }
        }
    }

    /**
     * A little test of Grid and UI objects.
     * TODO remove this method from final build
     */
    public static void main(String[] args) {
        boolean[][] arr = new boolean[][] {{true, false}, {false, true}};
        Grid g = new Grid(arr, 0, 0, 1, 1);
        System.out.print(g);
        for(boolean b : g.availableMoves()) System.out.print(b + " ");
        GameUI ui = new GameUI(g);
        ui.updateGridDisplay();
    }
}
