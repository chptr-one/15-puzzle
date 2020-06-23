package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.gui.MainFrame;

public class Game {
    private Board board;
    private BoardFactory boardFactory;
    private MainFrame mainFrame;

    public Game() {
        boardFactory = new BoardFactory();
        board = boardFactory.createGoalBoard();
        mainFrame = new MainFrame(this);
    }

    public void setDimension(int dimension) {
        boardFactory = new BoardFactory(dimension);
        board = boardFactory.createGoalBoard();
        mainFrame.updateBoard();
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}