package puzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardCreationTest {
    @Test
    void boardCreationFromDimension() {
        for (int dimension = 2; dimension < 5; dimension++) {
            byte[] tiles = new byte[dimension * dimension];
            for (int i = 0; i < tiles.length - 1; i++) {
                tiles[i] = (byte) (i + 1);
            }
            tiles[tiles.length - 1] = 0;

            Board b = new Board(dimension);
            assertArrayEquals(tiles, b.getTiles());
        }
    }

    @Test
    void boardCreationFromIncorrectDimension() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(1));
        assertEquals("Dimension must be 2 or greater.", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(0));
        assertEquals("Dimension must be 2 or greater.", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(-1));
        assertEquals("Dimension must be 2 or greater.", exception.getMessage());
    }

    @Test
    void boardCreationFromArray() {
        byte[][] tiles = {
                {1, 2, 3, 0},
                {1, 2, 3, 4, 5, 6, 7, 8, 0},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0}
        };
        for (byte[] tile : tiles) {
            Board b = new Board(tile);
            assertArrayEquals(tile, b.getTiles());
        }
    }

    @Test
    void boardCreationFromIncorrectSizeArray() {
        byte[][] nonSquareTiles = {
                {1, 2},
                {1, 2, 3},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7, 8}
        };
        for (byte[] tile : nonSquareTiles) {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new Board(tile));
            assertEquals("Array is not square.", exception.getMessage());
        }

        byte[] incorrectDimensionTiles = {1};
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(incorrectDimensionTiles));
        assertEquals("Dimension must be 2 or greater.", exception.getMessage());
    }

    @Test
    void boardCreationFromArrayWithIncorrectValues() {
        byte[][] noEmptyTiles = {
                {1, 2, 3, 4},
                {0, 1, 1, 2},
                {0, 1, 2, 4},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {1, 2, 3, 4, 0, 6, 7, 8, 9},
                {1, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16},
                {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}
        };
        for (byte[] tile : noEmptyTiles) {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new Board(tile));
            assertEquals("Array must contains unique numbers from 0 to array.length() - 1.", exception.getMessage());
        }
    }

    @Test
    void boardCreationFromEmptyArray() {
        byte[] empty = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(empty));
        assertEquals("Dimension must be 2 or greater.", exception.getMessage());
    }

    @Test
    void boardCreationFormNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Board(null));
        assertEquals("Argument is null.", exception.getMessage());
    }
}