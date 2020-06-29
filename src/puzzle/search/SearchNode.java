package puzzle.search;

import puzzle.common.Board;

import java.util.*;
import java.util.function.ToIntFunction;

class SearchNode implements Comparable<SearchNode> {
    private final Board board;
    private final SearchNode parent;
    private final int cost;
    private final int heuristic;
    private final ToIntFunction<Board> heuristicFunction;

    SearchNode(Board board, SearchNode parent, int cost, ToIntFunction<Board> heuristicFunction) {
        this.board = board;
        this.parent = parent;
        this.heuristicFunction = heuristicFunction;
        this.cost = cost;
        this.heuristic = heuristicFunction.applyAsInt(board);
    }

    int getHeuristic() {
        return heuristic;
    }

    SearchNode getParent() {
        return parent;
    }

    int getCost() {
        return cost;
    }

    Board getValue() {
        return board;
    }

    List<SearchNode> getSuccessors() {
        List<SearchNode> result = new ArrayList<>(3);
        for (Board b : board.getSuccessors()) {
            if (parent != null && b.equals(parent.board)) {
                continue;
            }
            result.add(new SearchNode(b, this, cost + 1, heuristicFunction));
        }
        return result;
    }

    @Override
    public int compareTo(SearchNode o) {
        return Integer.compare(cost + heuristic, o.getCost() + o.getHeuristic());
    }
}
