package puzzle.search;

import puzzle.Board;

import java.util.List;

public interface SearchAlgorithm {
    List<Board> solve();
    long getExploredNodes();
}
