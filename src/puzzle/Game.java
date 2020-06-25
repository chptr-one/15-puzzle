package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.gui.MainFrame;
import puzzle.search.*;

import java.util.List;

public class Game {
    private final MainFrame mainFrame;
    private Board board;
    private BoardFactory boardFactory;
    private int solverId;

    public Game() {
        boardFactory = new BoardFactory(4);
        board = boardFactory.createGoalBoard();
        mainFrame = new MainFrame(this);
    }

    public void setDimension(int dimension) {
        boardFactory = new BoardFactory(dimension);
        board = boardFactory.createGoalBoard();
        mainFrame.setBoard();
    }

    public void setSolver(int index) {
        solverId = index;
    }

    public void shuffle() {
        board = boardFactory.createShuffledBoard();
        mainFrame.setBoard();
    }

    public void resolve() {
        SearchAlgorithm solver = new AStar(board, boardFactory.createGoalBoard());
        if (solverId == 1) {
            solver = new IDAStar(board, boardFactory.createGoalBoard());
        }

        List<Board> solution = solver.resolve();
        for (Board b : solution) {
            System.out.println(board + "\n");
        }
        mainFrame.playSolution(solution);
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}