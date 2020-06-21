package puzzle.search;

import puzzle.Board;
import puzzle.search.heuristic.LinearConflict;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IDAStar implements SearchAlgorithm{
    private final Board start;
    private final Board goal;
    private long exploredNodes;

    public IDAStar(Board start, Board goal) {
        this.start = start;
        this.goal = goal;
        exploredNodes = 0;
    }

    @Override
    public List<Board> solve() {
        SearchNode startNode = new SearchNode(start, null, 0);
        SearchNode endNode = null;
        int threshold = startNode.getHeuristic();

        System.out.print("Threshold ");
        while (endNode == null) {
            System.out.print(threshold + " ");
            endNode = depthFirstSearch(startNode, threshold, goal);
            threshold += 2;
        }
        System.out.println();

        List<Board> result = Stream.iterate(endNode, n -> n.getParent() != null, SearchNode::getParent)
                .map(SearchNode::getValue)
                .collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    @Override
    public long getExploredNodes() {
        return exploredNodes;
    }

    private SearchNode depthFirstSearch(SearchNode current, int threshold, Board goal) {
        if (current.getValue().equals(goal)) {
            return current;
        }

        exploredNodes++;
        for (SearchNode successor : current.getSuccessors()) {
            int cost = successor.getCost() + successor.getHeuristic();
            if (cost <= threshold) {
                SearchNode result = depthFirstSearch(successor, threshold, goal);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}