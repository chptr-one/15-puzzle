package puzzle.search;

import puzzle.common.Board;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SearchAlgorithm {
    List<Board> resolve();

    long getExploredNodes();

    default List<Board> getSolution(SearchNode endNode) {
        List<Board> result = Stream.iterate(endNode, n -> n.getParent() != null, SearchNode::getParent)
                .map(SearchNode::getValue)
                .collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }
}
