package puzzle.search;

import puzzle.Board;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStar implements SearchAlgorithm {
    private final Board start;
    private final Board goal;
    private long exploredNodes;

    public AStar(Board start, Board goal) {
        this.start = start;
        this.goal = goal;
        exploredNodes = 0;
    }

    @Override
    public List<Board> solve() {
        PriorityQueue<SearchNode> minPQ = new PriorityQueue<>();
        minPQ.add(new SearchNode(start, null, 0));

        while (!minPQ.isEmpty() && !minPQ.peek().getValue().equals(goal)) {
            SearchNode minNode = minPQ.poll();
            for (SearchNode node : minNode.getSuccessors()) {
                minPQ.add(node);
                exploredNodes++;
            }
        }
        SearchNode endNode = minPQ.poll();
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
}
