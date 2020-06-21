package puzzle;

import java.util.*;

public class Board {
    private final int dimension;
    private final byte[] tiles;

    public Board(int dimension) {
        this.dimension = dimension;
        tiles = createGoalBoard();
    }

    public Board(int dimension, byte[] tiles) {
        this.dimension = dimension;
        this.tiles = tiles.clone();
    }

    public Set<Board> getSuccessors() {
        Set<Board> successors = new HashSet<>();
        int emptyTile = findEmptyTile();
        int rowEmpty = emptyTile / dimension;
        int colEmpty = emptyTile % dimension;
        if (rowEmpty > 0) {
            successors.add(swapTiles(emptyTile, toIndex(rowEmpty - 1, colEmpty)));
        }
        if (rowEmpty < dimension - 1) {
            successors.add(swapTiles(emptyTile, toIndex(rowEmpty + 1, colEmpty)));
        }
        if (colEmpty > 0) {
            successors.add(swapTiles(emptyTile, toIndex(rowEmpty, colEmpty - 1)));
        }
        if (colEmpty < dimension - 1) {
            successors.add(swapTiles(emptyTile, toIndex(rowEmpty, colEmpty + 1)));
        }
        return successors;
    }

    public Board moveTile(int row, int col) {
        if (!isValidCoordinates(row, col)) {
            return this;
        }

        int emptyTile = findEmptyTile();
        int emptyRow = emptyTile / dimension;
        int emptyCol = emptyTile % dimension;

        int directionRow = row - emptyRow;
        int directionCol = col - emptyCol;
        directionRow = directionRow == 0 ? directionRow : directionRow / Math.abs(directionRow);
        directionCol = directionCol == 0 ? directionCol : directionCol / Math.abs(directionCol);

        Board result = this;
        if (directionRow == 0 ^ directionCol == 0) {
            while (row != emptyRow || col != emptyCol) {
                result = result.swapTiles(emptyTile, toIndex(emptyRow + directionRow, emptyCol + directionCol));
                emptyRow += directionRow;
                emptyCol += directionCol;
                emptyTile = toIndex(emptyRow, emptyCol);
            }
        }
        return result;
    }

    private boolean isValidCoordinates(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col < dimension;
    }

    private Board swapTiles(int i1, int i2) {
        Board result = new Board(dimension, tiles);
        byte temp = result.tiles[i1];
        result.tiles[i1] = result.tiles[i2];
        result.tiles[i2] = temp;
        return result;
    }

    public byte[] createGoalBoard() {
        byte[] state = new byte[dimension * dimension];
        for (byte i = 0; i < state.length; i++) {
            state[i] = (byte) (i + 1);
        }
        state[state.length - 1] = 0;
        return state;
    }

    public boolean isSolvable() {
        int parity = 0;
        int row = 0;
        int blankRow = 0;

        for (int i = 0; i < tiles.length; i++) {
            if (i % dimension == 0) {
                row++;
            }
            if (tiles[i] == 0) {
                blankRow = row;
                continue;
            }
            for (int j = i + 1; j < tiles.length; j++) {
                if (tiles[i] > tiles[j] && tiles[j] != 0) {
                    parity++;
                }
            }
        }
        // I need to find a better way
        if (dimension % 2 == 0) {
            if (blankRow % 2 == 0) {
                return parity % 2 == 0;
            } else {
                return parity % 2 != 0;
            }
        } else {
            return parity % 2 == 0;
        }
    }

    public int getDimension() {
        return dimension;
    }

    public byte[] getTiles() {
        return tiles.clone();
    }

    public int toIndex(int row, int col) {
        return row * dimension + col;
    }

    private int findEmptyTile() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                return i;
            }
        }
        return -1; // No empty tile found. WTF?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (dimension != board.dimension) return false;
        return Arrays.equals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }

    @Override
    public String toString() {
        int width = String.valueOf(tiles.length - 1).length() + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            sb.append(String.format("%" + width + "s", tiles[i]));
            if ((i + 1) % dimension == 0) {
                sb.append('\n');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
