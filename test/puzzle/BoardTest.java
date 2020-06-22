package puzzle;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void getSuccessorsTest() {
        byte[] initial = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        Set<Board> expected = Set.of(new Board(new byte[]{1, 0, 2, 3, 4, 5, 6, 7, 8}),
                new Board(new byte[]{3, 1, 2, 0, 4, 5, 6, 7, 8}));
        Board b = new Board(initial);
        Set<Board> successors = b.getSuccessors();
        assertEquals(expected, successors);

        initial = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
        expected = Set.of(new Board(new byte[]{1, 2, 3, 4, 5, 0, 7, 8, 6}),
                new Board(new byte[]{1, 2, 3, 4, 5, 6, 7, 0, 8}));
        b = new Board(initial);
        successors = b.getSuccessors();
        assertEquals(expected, successors);

        initial = new byte[]{1, 2, 3, 4, 0, 5, 6, 7, 8};
        expected = Set.of(new Board(new byte[]{1, 0, 3, 4, 2, 5, 6, 7, 8}),
                new Board(new byte[]{1, 2, 3, 0, 4, 5, 6, 7, 8}),
                new Board(new byte[]{1, 2, 3, 4, 5, 0, 6, 7, 8}),
                new Board(new byte[]{1, 2, 3, 4, 7, 5, 6, 0, 8}));
        b = new Board(initial);
        successors = b.getSuccessors();
        assertEquals(expected, successors);
        initial = new byte[]{1, 2, 3, 4, 0, 5, 6, 7, 8};
        expected = Set.of(new Board(new byte[]{1, 0, 3, 4, 2, 5, 6, 7, 8}),
                new Board(new byte[]{1, 2, 3, 0, 4, 5, 6, 7, 8}),
                new Board(new byte[]{1, 2, 3, 4, 5, 0, 6, 7, 8}));
        b = new Board(initial);
        successors = b.getSuccessors();
        assertNotEquals(expected, successors);
    }

    @Test
    void moveTileTest() {
        byte[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        Board initial = new Board(arr);

        // invalid index, return initial board
        Board result = initial.moveTile(-1, 0);
        assertSame(initial, result);

        // invalid index, return initial board
        result = initial.moveTile(3, 4);
        assertSame(initial, result);

        // not valid move, return initial board
        result = initial.moveTile(0, 0);
        assertSame(initial, result);

        // not valid move, return initial board
        result = initial.moveTile(2, 2);
        assertSame(initial, result);

        // not valid move (empty tile coordinates), return initial board
        result = initial.moveTile(3, 3);
        assertSame(initial, result);

        // valid moves, return new Board
        result = initial.moveTile(2, 3);
        assertNotSame(initial, result);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12}, result.getTiles());
        result = result.moveTile(3,3);
        assertArrayEquals(initial.getTiles(), result.getTiles());
        result = result.moveTile(3,0);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 13, 14, 15}, result.getTiles());
        result = result.moveTile(3,3);
        assertArrayEquals(initial.getTiles(), result.getTiles());
        result = result.moveTile(1,3);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 8, 13, 14, 15, 12}, result.getTiles());
        result = result.moveTile(3,3);
        assertArrayEquals(initial.getTiles(), result.getTiles());
    }

    @Test
    void isSolvableTest() {
        final byte[][] solvable = {
                {4, 5, 7, 1, 8, 3, 2, 0, 6},
                {4, 2, 0, 3, 7, 1, 6, 8, 5},
                {3, 6, 5, 4, 8, 2, 7, 0, 1},
                {1, 8, 6, 3, 4, 2, 5, 0, 7},
                {8, 2, 6, 4, 5, 7, 10, 11, 9, 12, 0, 13, 14, 3, 1, 15},
                {6, 4, 10, 2, 9, 8, 1, 7, 5, 11, 3, 13, 12, 14, 0, 15},
                {1, 8, 2, 7, 12, 6, 13, 3, 15, 5, 9, 10, 11, 4, 0, 14},
                {13, 4, 2, 5, 0, 10, 14, 3, 6, 1, 15, 12, 8, 7, 9, 11},
                {6, 3, 0, 4, 7, 5, 15, 11, 14, 13, 1, 2, 12, 9, 8, 10},
        };

        byte[][] notSolvable = new byte[solvable.length][solvable[0].length];
        for (int i = 0; i < solvable.length; i++) {
            notSolvable[i] = Arrays.copyOf(solvable[i], solvable[i].length);
            byte temp = notSolvable[i][0];
            notSolvable[i][0] = notSolvable[i][1];
            notSolvable[i][1] = temp;
        }

        for (byte[] tile : solvable) {
            assertTrue(new Board(tile).isSolvable());
        }
        for (byte[] tile : notSolvable) {
            assertFalse(new Board(tile).isSolvable());
        }
    }

    @Test
    void getEmptyTileIndexTest() {
        Board b = new Board(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
        assertEquals(8, b.getEmptyTileIndex());
    }
}