package puzzle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final int dimension;
    private final byte[] tiles;
    private final int emptyTileIndex;

    /*
    Creates resolved Board of given dimension with empty tile in right-bottom corner
    */
    public Board(int dimension) {
        if (dimension < 2) {
            throw new IllegalArgumentException("Dimension must be 2 or greater.");
        }
        this.dimension = dimension;
        this.tiles = createSolvedTiles();
        this.emptyTileIndex = tiles.length - 1;
    }

    /*
    Creates new Board from given array.
    Array must be square with dimension 2x2 or greater,
    and can contain only unique numbers from 0 to dimension * dimension - 1 in any order.
    */
    public Board(byte[] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        double dim = Math.sqrt(tiles.length);
        if (dim % 1 != 0) {
            throw new IllegalArgumentException("Array is not square.");
        }
        if (dim < 2) {
            throw new IllegalArgumentException("Dimension must be 2 or greater.");
        }

        // checking the numbers in tiles for compliance with the restriction
        Set<Integer> correct = IntStream.range(0, tiles.length).boxed().collect(Collectors.toSet());
        Set<Integer> actual = IntStream.range(0, tiles.length).map(i -> tiles[i]).boxed().collect(Collectors.toSet());
        if (!correct.equals(actual)) {
            throw new IllegalArgumentException("Array must contains unique numbers from 0 to array.length() - 1.");
        }
        this.dimension = (int) dim;
        this.tiles = tiles.clone();

        // find an empty Tile
        int i = 0;
        while (i < tiles.length && tiles[i] != 0) {
            i++;
        }
        emptyTileIndex = i;
    }

    /*
    Creates new Board from given array without any arguments check.
    Used by swapTiles() method.
    */
    private Board(byte[] tiles, int dimension, int emptyTileIndex) {
        this.tiles = tiles;
        this.dimension = dimension;
        this.emptyTileIndex = emptyTileIndex;
    }

    private byte[] createSolvedTiles() {
        byte[] tiles = new byte[dimension * dimension];
        for (byte i = 0; i < tiles.length; i++) {
            tiles[i] = (byte) (i + 1);
        }
        tiles[tiles.length - 1] = 0;
        return tiles;
    }

    /*
    Returns the set of all boards that can be reached from the current board by making a valid move.
    A move -- is to swap exactly one tile with an adjacent empty tile.
    There is always at least one possible move, so we return at least one board.
    */
    public Set<Board> getSuccessors() {
        Set<Board> successors = new HashSet<>();

        int emptyTileRow = emptyTileIndex / dimension;
        int emptyTileCol = emptyTileIndex % dimension;
        if (emptyTileRow > 0) {
            successors.add(swapTiles(emptyTileIndex, toIndex(emptyTileRow - 1, emptyTileCol)));
        }
        if (emptyTileRow < dimension - 1) {
            successors.add(swapTiles(emptyTileIndex, toIndex(emptyTileRow + 1, emptyTileCol)));
        }
        if (emptyTileCol > 0) {
            successors.add(swapTiles(emptyTileIndex, toIndex(emptyTileRow, emptyTileCol - 1)));
        }
        if (emptyTileCol < dimension - 1) {
            successors.add(swapTiles(emptyTileIndex, toIndex(emptyTileRow, emptyTileCol + 1)));
        }
        return successors;
    }

    /*
    If possible, tries to make a move with a tile with the given coordinates.
    Can move multiple tiles in a row or column.
    Returns a new board if successful, or the current board otherwise.
    */
    public Board moveTile(int row, int col) {
        if (!isValidCoordinates(row, col)) {
            return this;
        }

        int emptyTileRow = emptyTileIndex / dimension;
        int emptyTileCol = emptyTileIndex % dimension;
        int directionRow = row - emptyTileRow;
        int directionCol = col - emptyTileCol;
        directionRow = directionRow == 0 ? directionRow : directionRow / Math.abs(directionRow);
        directionCol = directionCol == 0 ? directionCol : directionCol / Math.abs(directionCol);

        Board result = this;
        if (directionRow == 0 ^ directionCol == 0) {
            while (row != emptyTileRow || col != emptyTileCol) {
                result = result.swapTiles(result.getEmptyTileIndex(), toIndex(emptyTileRow + directionRow, emptyTileCol + directionCol));
                emptyTileRow += directionRow;
                emptyTileCol += directionCol;
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
        return new Board(newTiles, dimension, i2);
    }

    /*
     https://en.wikipedia.org/wiki/15_puzzle#Solvability
    */
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

    /*
    Returns the square matrix representation.
    Align numbers in columns so that there is exactly one whitespace between the largest numbers.
    */
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
        sb.deleteCharAt(sb.length() - 1); // remove last '\n'
        return sb.toString();
    }
}
