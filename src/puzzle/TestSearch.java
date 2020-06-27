package puzzle;

import puzzle.common.Board;
import puzzle.common.BoardFactory;
import puzzle.search.*;

import java.util.List;

@SuppressWarnings("unused")
public class TestSearch {
    public static void main(String[] args) {
        byte[][] test_3 = {
                {6, 0, 2, 5, 4, 8, 1, 3, 7},
                {3, 6, 5, 4, 8, 2, 7, 0, 1},
                {1, 8, 6, 3, 4, 2, 5, 0, 7},
                {3, 1, 5, 8, 7, 6, 4, 2, 0},
                {8, 6, 1, 3, 0, 4, 5, 7, 2},
                {4, 1, 8, 6, 3, 0, 5, 7, 2},
                {4, 5, 7, 1, 8, 3, 2, 0, 6},
                {4, 2, 0, 3, 7, 1, 6, 8, 5}
        };
        byte[][] test_4 = {
                {8, 2, 6, 4, 5, 7, 10, 11, 9, 12, 0, 13, 14, 3, 1, 15},
                {6, 1, 0, 3, 8, 13, 2, 11, 4, 9, 7, 15, 12, 5, 14, 10},
                {10, 11, 2, 14, 6, 8, 5, 3, 4, 1, 13, 7, 9, 12, 0, 15},
                {6, 4, 10, 2, 9, 8, 1, 7, 5, 11, 3, 13, 12, 14, 0, 15},
                {1, 8, 2, 7, 12, 6, 13, 3, 15, 5, 9, 10, 11, 4, 0, 14},
                {13, 4, 2, 5, 0, 10, 14, 3, 6, 1, 15, 12, 8, 7, 9, 11},
                {6, 3, 0, 4, 7, 5, 15, 11, 14, 13, 1, 2, 12, 9, 8, 10},
        };

        BoardFactory factory = new BoardFactory();
        Board goal = factory.createGoalBoard();

        for (byte[] test : test_4) {
            Board shuffled = new Board(test);
            System.out.println("Initial board:");
            System.out.println(shuffled);
            if (!shuffled.isSolvable()) {
                System.out.println("Not solvable!");
                continue;
            }

            SearchAlgorithm solver = new IDAStar();
            long startTime = System.currentTimeMillis();
            List<Board> solution = solver.resolve(new Board(test), goal);
            for (Board b : solution) {
                System.out.println(b + "\n");
            }

            System.out.println("solution length: " + solution.size());
            System.out.println("nodes explored: " + solver.getExploredNodes());
            System.out.println("time: " + (System.currentTimeMillis() - startTime) + " ms");
            System.out.println();
        }
    }
}
