package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.gui.MainFrame;
import puzzle.search.*;

import java.util.List;

public class Game {
    private final MainFrame mainFrame;
    private Board board;
    private Board goalBoard;
    private BoardFactory boardFactory;
    private int solverId;

    public Game() {
        boardFactory = new BoardFactory(4);
        board = boardFactory.createGoalBoard();
        goalBoard = boardFactory.createGoalBoard();
        mainFrame = new MainFrame(this);
    }

    public void setDimension(int dimension) {
        boardFactory = new BoardFactory(dimension);
        board = boardFactory.createGoalBoard();
        goalBoard = boardFactory.createGoalBoard();
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
        board = mainFrame.getBoard();
        SearchAlgorithm solver = new AStar(board, goalBoard);
        if (solverId == 1) {
            solver = new IDAStar(board, goalBoard);
        }

        List<Board> solution = solver.resolve();
        System.out.println("Initial board: ");
        System.out.println(board + "\n");
        for (Board b : solution) {
            System.out.println(b + "\n");
        }
        System.out.println("Solved in " + solution.size() + " steps.");
        System.out.println(solver.getExploredNodes() + " nodes explored." + "\n");
        mainFrame.playSolution(solution);
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}