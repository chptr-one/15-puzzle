package puzzle.search;

import puzzle.common.Board;

import java.util.List;
import java.util.function.ToIntFunction;

public class IDAStar extends AbstractSearchAlgorithm {

    public IDAStar(ToIntFunction<Board> heuristicFunction) {
        super(heuristicFunction);
    }

    @Override
    public List<Board> resolve(Board start, Board goal) {
        SearchNode startNode = new SearchNode(start, null, 0, heuristicFunction);
        SearchNode endNode = null;
        int threshold = startNode.getHeuristic();

        System.out.print("Threshold ");
        while (endNode == null) {
            System.out.print(threshold + " ");
            endNode = depthFirstSearch(startNode, threshold, goal);
            threshold += 2;
        }
        System.out.println();

        return getSolution(endNode);
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