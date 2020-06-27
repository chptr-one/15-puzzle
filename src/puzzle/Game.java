package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.gui.MainFrame;
import puzzle.search.*;

import java.util.*;

public class Game {
    //static public final Map<String, ToIntFunction<Board>> HEURISTICS;
    static public final List<Map.Entry<String, SearchAlgorithm>> ALGORITHMS;
    static private int solverId;

    static {
        ALGORITHMS = List.of(
                new AbstractMap.SimpleEntry<>("A* search", new AStar()),
                new AbstractMap.SimpleEntry<>("IDA* search", new IDAStar())
        );
        solverId = 1;
    }

    private final MainFrame mainFrame;
    private Board board;
    private Board goalBoard;
    private BoardFactory boardFactory;

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

    public void setSolverId(int index) {
        solverId = index;
    }

    public int getSolverId() {return solverId;}

    public void shuffle() {
        board = boardFactory.createShuffledBoard();
        mainFrame.setBoard();
    }

    public void resolve() {
        board = mainFrame.getBoard();
        SearchAlgorithm solver = ALGORITHMS.get(solverId).getValue();
        List<Board> solution = solver.resolve(board, goalBoard);
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