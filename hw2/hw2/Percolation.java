package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] grid;
    private int[][] arr;
    private WeightedQuickUnionUF uf;
    private int count = 0;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("please enter a integer greater than zero");
        }
        size = N;
        grid = new boolean[N][N];
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
                arr[i][j] = helper(i, j);
            }
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        for (int j = 0; j < N; j++) {
            uf.union(arr[0][j], N * N + 1);
            uf.union(arr[N - 1][j], N * N);
        }
    }

    private int helper(int row, int col) {
        return row * grid.length + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            count += 1;
            if (isOpen(row - 1, col)) {
                uf.union(helper(row - 1, col), arr[row][col]);
            }
            if (isOpen(row + 1, col)) {
                uf.union(helper(row + 1, col), arr[row][col]);
            }
            if (isOpen(row, col - 1)) {
                uf.union(helper(row, col - 1), arr[row][col]);
            }
            if (isOpen(row, col + 1)) {
                uf.union(helper(row, col + 1), arr[row][col]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return false;
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        return uf.connected(arr[row][col], size * size + 1);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(size * size + 1, size * size);
    }
}
