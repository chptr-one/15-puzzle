package puzzle.gui;

import puzzle.Board;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {
    private final BoardPanel boardPanel;

    public MainFrame(Board board) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sliding Puzzle");
        setLocation(200, 200);
        setResizable(false);
        JPanel contentPanel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentPanel.setBorder(padding);
        setContentPane(contentPanel);

        boardPanel = new BoardPanel(board);
        add(boardPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void updateBoard(Board board) {
        boardPanel.updateBoard(board);
    }
}
