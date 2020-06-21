package puzzle.gui;

import puzzle.Board;
import puzzle.search.*;

import java.util.List;

public class TestGUI {
    public static void main(String[] args) {
        Board goal = new Board(4);
        Board b = new Board(4, new byte[]{8, 2, 6, 4, 5, 7, 10, 11, 9, 12, 0, 13, 14, 3, 1, 15});
        MainFrame frame = new MainFrame(b);

        System.out.println("Initial board:");
        System.out.println(b);
        SearchAlgorithm solver = new AStar(b, goal);

        List<Board> solution = solver.solve();
        System.out.println("solution length: " + solution.size());
        System.out.println("nodes explored: " + solver.getExploredNodes());
        for (Board s : solution) {
            frame.updateBoard(s);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
