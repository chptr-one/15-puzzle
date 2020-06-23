package puzzle;

public class BoardFactory {
    private final int dimension;

    public BoardFactory(int dimension) {
        this.dimension = dimension;
    }

    public Board goalBoard() {
        byte[] tiles = new byte[dimension * dimension];
        for (byte i = 0; i < tiles.length; i++) {
            tiles[i] = (byte) (i + 1);
        }
        tiles[tiles.length - 1] = 0;
        return new Board(tiles, dimension, tiles.length - 1);
    }
}
