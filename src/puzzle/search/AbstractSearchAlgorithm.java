package puzzle.search;

import puzzle.common.Board;

import java.util.List;
import java.util.function.ToIntFunction;

public abstract class AbstractSearchAlgorithm implements SearchAlgorithm {
    protected ToIntFunction<Board> heuristicFunction;
    protected long exploredNodes;

    AbstractSearchAlgorithm(ToIntFunction<Board> heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
        exploredNodes = 0;
    }

    @Override
    public void setHeuristic(ToIntFunction<Board> heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }

    abstract public List<Board> resolve(Board start, Board goal);

    public long getExploredNodes() {
        return exploredNodes;
    }
}
