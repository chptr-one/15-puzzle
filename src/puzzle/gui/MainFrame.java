package puzzle.gui;

import puzzle.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {
    private final Game game;

    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final StatisticsPanel statisticsPanel;

    public MainFrame(Game game) {
        this.game = game;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sliding Puzzle");
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel contentPanel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentPanel.setBorder(padding);
        setContentPane(contentPanel);
        setLayout(new FlowLayout());

        boardPanel = new BoardPanel(game.getBoard());
        add(boardPanel);

        controlPanel = new ControlPanel(game);
        statisticsPanel = new StatisticsPanel();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(controlPanel);
        rightPanel.add(statisticsPanel);
        add(rightPanel);

        pack();
        setVisible(true);
    }

    public void updateBoard() {
        boardPanel.setBoard(game.getBoard());
        pack();

        Dimension d = new Dimension();
        boardPanel.getSize(d);
        System.out.println(d.getSize());
    }
}
