package puzzle.search.heuristic;

import puzzle.Board;

public class LinearConflict extends ManhattanDistance {
    @Override
    public int applyAsInt(Board board) {
        int heuristic = super.applyAsInt(board);
        heuristic += linearVerticalConflict(board);
        heuristic += linearHorizontalConflict(board);
        return heuristic;
    }

    private int linearVerticalConflict(Board board) {
        int dimension = board.getDimension();
        byte[] tiles = board.getTiles();
        int linearConflict = 0;

        for (int row = 0; row < dimension; row++) {
            byte max = -1;
            for (int col = 0; col < dimension; col++) {
                byte tile = tiles[board.toIndex(row, col)];
                if (tile != 0 && (tile - 1) / dimension == row) {
                    if (tile > max) {
                        max = tile;
                    } else {
                        linearConflict += 2;
                    }
                }
            }
        }
        return linearConflict;
    }

    private int linearHorizontalConflict(Board board) {
        int dimension = board.getDimension();
        byte[] tiles = board.getTiles();
        int linearConflict = 0;

        for (int col = 0; col < dimension; col++) {
            byte max = -1;
            for (int row = 0; row < dimension; row++) {
                byte tile = tiles[board.toIndex(row, col)];
                if (tile != 0 && tile % dimension == col + 1) {
                    if (tile > max) {
                        max = tile;
                    } else {
                        linearConflict += 2;
                    }
                }
            }
        }
        return linearConflict;
    }
}
