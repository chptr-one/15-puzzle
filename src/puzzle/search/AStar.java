package puzzle.search;

import puzzle.common.Board;

import java.util.List;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

public class AStar extends AbstractSearchAlgorithm {

    public AStar(ToIntFunction<Board> heuristicFunction) {
        super(heuristicFunction);
    }

    @Override
    public List<Board> resolve(Board start, Board goal) {
        PriorityQueue<SearchNode> minPQ = new PriorityQueue<>();
        minPQ.add(new SearchNode(start, null, 0, heuristicFunction));

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
}
