package puzzle.search;

import puzzle.common.Board;

import java.util.*;

public class AStar implements SearchAlgorithm {
    private long exploredNodes;

    public AStar() {
        exploredNodes = 0;
    }

    @Override
    public List<Board> resolve(Board start, Board goal) {
        PriorityQueue<SearchNode> minPQ = new PriorityQueue<>();
        minPQ.add(new SearchNode(start, null, 0));

        while (!minPQ.isEmpty() && !minPQ.peek().getValue().equals(goal)) {
            SearchNode minNode = minPQ.poll();
            assert minNode != null;
            for (SearchNode node : minNode.getSuccessors()) {
                minPQ.add(node);
                exploredNodes++;
            }
        }
        SearchNode endNode = minPQ.poll();
        return getSolution(endNode);
    }

    @Override
    public long getExploredNodes() {
        return exploredNodes;
    }
}
