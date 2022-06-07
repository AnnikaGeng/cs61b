package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class Solver {
    private Map<WorldState, Integer> edtgCaches = new HashMap<>();

    private class SearchNode {
        private WorldState state;
        private int moves = 0;
        private SearchNode prev;

        SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }
    }

    private class NodeComparator implements Comparator<SearchNode> {
        /*
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int o1Edtg = getEdtg(o1);
            int o2Edtg = getEdtg(o2);
            int o1Priority = o1Edtg + o1.moves;
            int o2Priority = o2Edtg + o2.moves;
            return o1Priority - o2Priority;
        }

        private int getEdtg(SearchNode o) {
            if (!edtgCaches.containsKey(o.state)) {
                edtgCaches.put(o.state, o.state.estimatedDistanceToGoal());
            }
            return edtgCaches.get(o.state);
        }
         */
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int o1Edtg = getEdtg(o1);
            int o2Edtg = getEdtg(o2);
            int o1Priority = o1.moves + o1Edtg;
            int o2Priority = o2.moves + o2Edtg;
            return o1Priority - o2Priority;
        }

        private int getEdtg(SearchNode sn) {
            if (!edtgCaches.containsKey(sn.state)) {
                edtgCaches.put(sn.state, sn.state.estimatedDistanceToGoal());
            }
            return edtgCaches.get(sn.state);
        }
    }

    private List<WorldState> solution = new ArrayList<>();

    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new NodeComparator());
        SearchNode delNode = new SearchNode(initial, 0, null);
        pq.insert(delNode);
        while (!pq.isEmpty()) {
            delNode = pq.delMin();
            if (delNode.state.isGoal()) {
                break;
            }

            for (WorldState nextState : delNode.state.neighbors()) {
                SearchNode nextNode = new SearchNode(nextState, delNode.moves + 1, delNode);

                if (delNode.prev != null && nextState.equals(delNode.prev.state)) {
                    continue;
                }
                pq.insert(nextNode);
            }
        }

        Stack<WorldState> path = new Stack<>();
        for (SearchNode node = delNode; node != null; node = node.prev) {
            path.push(node.state);
        }

        while (!path.isEmpty()) {
            solution.add(path.pop());
        }


    }
    public int moves() {
        return solution.size() - 1;
    }
    public Iterable<WorldState> solution() {
        return solution;
    }
}
