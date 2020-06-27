package puzzle;

import puzzle.common.*;
import puzzle.search.*;
import puzzle.search.heuristic.*;
import puzzle.gui.MainFrame;

import java.util.*;
import java.util.function.ToIntFunction;

public class Game {
    static public final List<Map.Entry<String, ToIntFunction<Board>>> HEURISTICS;
    static public final List<Map.Entry<String, SearchAlgorithm>> ALGORITHMS;
    static private int algorithmId;
    static private int heuristicId;

    static {
        HEURISTICS = List.of(
                new AbstractMap.SimpleEntry<>("Manhattan Distance", new ManhattanDistance()),
                new AbstractMap.SimpleEntry<>("Linear Conflict", new LinearConflict())
        );
        heuristicId = 1;

        ALGORITHMS = List.of(
                new AbstractMap.SimpleEntry<>("A* search", new AStar(HEURISTICS.get(heuristicId).getValue())),
                new AbstractMap.SimpleEntry<>("IDA* search", new IDAStar(HEURISTICS.get(heuristicId).getValue()))
        );
        algorithmId = 1;
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

    public int getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(int index) {
        algorithmId = index;
    }

    public int getHeuristicId() {
        return heuristicId;
    }

    public void setHeuristicId(int index) {
        heuristicId = index;
    }

    public void shuffle() {
        board = boardFactory.createShuffledBoard();
        mainFrame.setBoard();
    }

    public void resolve() {
        board = mainFrame.getBoard();
        ToIntFunction<Board> heuristicFunction = HEURISTICS.get(heuristicId).getValue();
        SearchAlgorithm solver = ALGORITHMS.get(algorithmId).getValue();
        solver.setHeuristic(heuristicFunction);

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
        new Game();
    }
}