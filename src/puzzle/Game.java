package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.gui.MainFrame;

public class Game {
    private int dimension;
    private Board board;
    private BoardFactory boardFactory;
    private final MainFrame mainFrame;

    public Game() {
        dimension = 4;
        boardFactory = new BoardFactory(dimension);
        board = boardFactory.createGoalBoard();
        mainFrame = new MainFrame(this);
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
        boardFactory = new BoardFactory(this.dimension);
        board = boardFactory.createGoalBoard();
        mainFrame.setBoard();
    }

    public void shuffle() {
        board = boardFactory.createShuffledBoard();
        mainFrame.setBoard();
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}