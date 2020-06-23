package puzzle.search;

import puzzle.common.Board;
import puzzle.search.heuristic.LinearConflict;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

public class SearchNode implements Comparable<SearchNode> {
    static private final ToIntFunction<Board> HEURISTIC = new LinearConflict();

    private final Board board;
    private final SearchNode parent;
    private final int cost;
    private final int heuristic;

    public SearchNode(Board board, SearchNode parent, int cost) {
        this.board = board;
        this.parent = parent;
        this.cost = cost;
        heuristic = HEURISTIC.applyAsInt(board);
    }

    public int getHeuristic() {
        return heuristic;
    }

    public SearchNode getParent() {
        return parent;
    }

    public int getCost() {
        return cost;
    }

    public Board getValue() {
        return board;
    }

    public Set<SearchNode> getSuccessors() {
        Set<SearchNode> result = new HashSet<>();
        for (Board b : board.getSuccessors()) {
            if (parent != null && b.equals(parent.board)) {
                continue;
            }
            result.add(new SearchNode(b, this, cost + 1));
        }
        return result;
    }

    @Override
    public int compareTo(SearchNode o) {
        return Integer.compare(cost + heuristic, o.getCost() + o.getHeuristic());
    }
}
