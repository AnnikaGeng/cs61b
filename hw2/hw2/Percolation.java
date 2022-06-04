package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sites2;
    private int topSite;
    private int bottomSite;
    private int count = 0;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("please enter a integer greater than zero");
        }
        this.N = N;
        grid = new boolean[N][N];
        topSite = N * N;
        bottomSite = N * N + 1;

        sites = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            sites.union(topSite, helper(0, i));
        }

        for (int i = 0; i < N; i++) {
            sites.union(bottomSite, helper(N - 1, i));
        }

        sites2 = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            sites2.union(topSite, helper(0, i));
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int helper(int row, int col) {
        return row * grid.length + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validRange(row, col);

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            count += 1;
            unionNeighbor(row, col, row + 1, col);
            unionNeighbor(row, col, row - 1, col);
            unionNeighbor(row, col, row, col + 1);
            unionNeighbor(row, col, row, col - 1);
        }
    }

    private void unionNeighbor(int row, int col, int newRow, int newCol) {
        if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length) {
            return;
        }

        if (grid[newRow][newCol]) {
            sites.union(helper(row, col), helper(newRow, newCol));
            sites2.union(helper(row, col), helper(newRow, newCol));
        }
    }

    private void validRange(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            throw new IndexOutOfBoundsException();
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validRange(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validRange(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return sites2.connected(topSite, helper(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (count == 0) {
            return false;
        }
        return sites.connected(topSite, bottomSite);
    }

    public static void main(String[] args) {
    }
}
