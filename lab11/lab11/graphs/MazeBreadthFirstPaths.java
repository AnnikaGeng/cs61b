package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(v);
        marked[s] = true;
        announce();
        while (!fringe.isEmpty()) {
            int delMaze = fringe.remove();
            if (delMaze == t) {
                targetFound = true;
                break;
            }

            for (int w : maze.adj(delMaze)) {
                if (!marked[w]) {
                    fringe.add(w);
                    edgeTo[w] = delMaze;
                    announce();
                    distTo[w] = distTo[delMaze] + 1;
                    marked[w] = true;
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

