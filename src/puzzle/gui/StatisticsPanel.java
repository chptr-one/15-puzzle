package puzzle.gui;

import javax.swing.*;

public class StatisticsPanel extends JPanel {
    private JLabel moves;
    public StatisticsPanel() {
        add(new JLabel("Number of moves: "));

        moves = new JLabel("0");
        add(moves);
    }
}
