package puzzle.gui;

import puzzle.Game;
import puzzle.common.Board;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final Game game;

    private final BoardPanel boardPanel;
    @SuppressWarnings("FieldCanBeLocal")
    private final ControlPanel controlPanel;

    public MainFrame(Game game) {
        this.game = game;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sliding Puzzle");
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel borderedPanel = new JPanel();
        int padding = 8;
        borderedPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        this.getContentPane().add(borderedPanel, "Center");

        boardPanel = new BoardPanel(game.getBoard());
        borderedPanel.add(boardPanel);

        controlPanel = new ControlPanel(game);
        borderedPanel.add(controlPanel);

        pack();
        setVisible(true);
    }

    public void playSolution(Iterable<Board> solution) {
        Player player = new Player(solution);
        player.start();
    }

    public void setBoard() {
        boardPanel.setBoard(game.getBoard());
        SwingUtilities.updateComponentTreeUI(this);
        pack();
    }

    public Board getBoard() {
        return boardPanel.getBoard();
    }

    class Player extends Thread {
        final Iterable<Board> boards;

        Player(Iterable<Board> boards) {
            this.boards = boards;
        }

        @Override
        public void run() {
            for (Board b : boards) {
                boardPanel.updateBoard(b);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}