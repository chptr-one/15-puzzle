package puzzle;

import java.util.*;

public class Board {
    private final int dimension;
    private final byte[] tiles;
    private final int emptyTileIndex;

    public Board(int dimension) {
        if (dimension < 2) {
            throw new IllegalArgumentException("Board dimension must be 2 or greater.");
        }
        this.dimension = dimension;
        this.tiles = createGoal();
        emptyTileIndex = tiles.length - 1;
    }

    public Board(byte[] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Argument array is null.");
        }
        double dim = Math.sqrt(tiles.length);
        if (dim % 1 != 0) {
            throw new IllegalArgumentException("Argument array is not square.");
        }
        if (dim < 2) {
            throw new IllegalArgumentException("Board dimension must be 2 or greater.");
        }
        // find empty Tile
        int i = 0;
        while (i < tiles.length && tiles[i] != 0) {
            i++;
        }
        if (i < tiles.length) {
            this.dimension = (int) dim;
            this.tiles = tiles.clone(); // Возможно, копирование не нужно. Убедиться, что массив плиток внутри борды никогда не меняется
            emptyTileIndex = i;
        } else {
            throw new IllegalArgumentException("Empty tile not found.");
        }
    }

    private byte[] createGoal() {
        byte[] state = new byte[dimension * dimension];
        for (byte i = 0; i < state.length; i++) {
            state[i] = (byte) (i + 1);
        }
        state[state.length - 1] = 0;
        return state;
    }

    public Set<Board> getSuccessors() {
        Set<Board> successors = new HashSet<>();
        int emptyTile = getEmptyTileIndex();
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

        int emptyRow = emptyTileIndex / dimension;
        int emptyCol = emptyTileIndex % dimension;
        int directionRow = row - emptyRow;
        int directionCol = col - emptyCol;
        directionRow = directionRow == 0 ? directionRow : directionRow / Math.abs(directionRow);
        directionCol = directionCol == 0 ? directionCol : directionCol / Math.abs(directionCol);

        Board result = this;
        if (directionRow == 0 ^ directionCol == 0) {
            while (row != emptyRow || col != emptyCol) {
                result = result.swapTiles(result.getEmptyTileIndex(), toIndex(emptyRow + directionRow, emptyCol + directionCol));
                emptyRow += directionRow;
                emptyCol += directionCol;
            }
        }
        return result;
    }

    private boolean isValidCoordinates(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col < dimension;
    }

    private Board swapTiles(int i1, int i2) {
        byte[] newTiles = tiles.clone();
        byte temp = newTiles[i1];
        newTiles[i1] = newTiles[i2];
        newTiles[i2] = temp;
        return new Board(newTiles);
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

    public int getEmptyTileIndex() {
        return emptyTileIndex;
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
