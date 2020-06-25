package puzzle.search;

import puzzle.common.Board;

import java.util.List;

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
    public List<Board> resolve() {
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

        return getSolution(endNode);
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