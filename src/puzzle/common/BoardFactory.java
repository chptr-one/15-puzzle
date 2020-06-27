package puzzle.common;

import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory {
    private final int dimension;

    public BoardFactory() {
        dimension = 4;
    }

    public BoardFactory(int dimension) {
        if (dimension < 2) {
            throw  new IllegalArgumentException("Dimension must be 2 or greater.");
        }
        this.dimension = dimension;
    }

    public Board createGoalBoard() {
        byte[] tiles = new byte[dimension * dimension];
        for (byte i = 0; i < tiles.length; i++) {
            tiles[i] = (byte) (i + 1);
        }
        tiles[tiles.length - 1] = 0;
        return new Board(tiles, dimension, tiles.length - 1);
    }

    public Board createShuffledBoard() {
        int[] ints = ThreadLocalRandom.current()
                .ints(0, dimension * dimension)
                .distinct()
                .limit(dimension * dimension)
                .toArray();
        byte[] tiles = new byte[ints.length];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (byte) ints[i];
        }
        int emptyTileIndex = Board.findEmptyTile(tiles);

        // Any unsolvable board turns into a solvable one if you swap two adjacent nonzero tiles
        if (!Board.isSolvable(tiles)) {
            if (emptyTileIndex >= 2) {
                byte temp = tiles[0];
                tiles[0] = tiles[1];
                tiles[1] = temp;
            } else {
                byte temp = tiles[tiles.length - 1];
                tiles[tiles.length - 1] = tiles[tiles.length - 2];
                tiles[tiles.length - 2] = temp;
            }
        }

        return new Board(tiles, dimension, emptyTileIndex);
    }
}
