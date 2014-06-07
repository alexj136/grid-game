package SwingUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;

import GameLogic.Grid;

public class GameUI extends JFrame {

    private JPanel panel;
    private Grid grid;

    public GameUI(Grid grid) {
        this.grid = grid;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.panel = new JPanel(
            new GridLayout(
                this.grid.numRows(),
                this.grid.numCols()
            )
        );
    }

    public static void main(String[] args) {
        GameUI ui = new GameUI();
    }
}
