package puzzle.search.heuristic;

import puzzle.common.Board;

import java.util.function.ToIntFunction;

public class ManhattanDistance implements ToIntFunction<Board> {
    @Override
    public int applyAsInt(Board board) {
        int distance = 0;
        byte[] tiles = board.getTiles();
        int dimension = board.getDimension();

        int row, col;
        int expectedRow, expectedCol;

        for (int i = 0; i < tiles.length; i++) {
            int tile = tiles[i];
            if (tile != 0) {
                row = i / dimension;
                col = i % dimension;
                expectedRow = (tile - 1) / dimension;
                expectedCol = (tile - 1) % dimension;

                distance += Math.abs(row - expectedRow) + Math.abs(col - expectedCol);
            }
        }

        return distance;
    }
}