package puzzle.gui;

import puzzle.Board;
import puzzle.BoardFactory;
import puzzle.search.*;

import java.util.List;

public class TestGUI {
    public static void main(String[] args) {
        BoardFactory factory = new BoardFactory();
        Board goal = factory.createGoalBoard();
        Board shuffled = factory.createShuffledBoard();
        MainFrame frame = new MainFrame(shuffled);
    }
}
