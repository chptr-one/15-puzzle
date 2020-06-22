package puzzle.gui;

import puzzle.Board;
import puzzle.search.*;

import java.util.List;

public class TestGUI {
    public static void main(String[] args) {
        Board goal = new Board(4);
        //Board b = new Board(new byte[]{8, 2, 6, 4, 5, 7, 10, 11, 9, 12, 0, 13, 14, 3, 1, 15});
        Board b = new Board(4);
        MainFrame frame = new MainFrame(b);
    }
}
