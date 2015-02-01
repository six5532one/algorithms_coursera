import java.util.ArrayList;

public class Percolation    {
    private int dim;
    private boolean[][] sites;
    private WeightedQuickUnionUF qu;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)   {
        if (N <= 0)
            throw new IllegalArgumentException("N must be > 0");
        dim = N;
        sites = new boolean[N][N];
        qu = new WeightedQuickUnionUF(N*N);
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++)
                sites[i][j] = false;
        }
    }

    private ArrayList<int[]> neighbors(int i, int j) {
        if ((i<1) || (i>dim) || (j<1) || (j>dim))
            throw new IndexOutOfBoundsException("i, j must be in [1, N]");
        ArrayList<int[]> result = new ArrayList<int[]>();
        // north
        if (i > 1)  {
            int[] north = {i-1, j};
            result.add(north);
        }
        // south
        if (i < dim)  {
            int[] south = {i+1, j};
            result.add(south);
        }
        // west
        if (j > 1)  {
            int[] west = {i, j-1};
            result.add(west);
        }
        // east
        if (j < dim)  {
            int[] east = {i, j+1};
            result.add(east);
        }
        return result;
    }

    private int siteId(int i, int j)    {
        if ((i<1) || (i>dim) || (j<1) || (j>dim))
            throw new IndexOutOfBoundsException("i, j must be in [1, N]");
        return dim * (i-1) + j -1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j)  {
        if ((i<1) || (i>dim) || (j<1) || (j>dim))
            throw new IndexOutOfBoundsException("i, j must be in [1, N]");
        sites[i-1][j-1] = true;
        for (int[] neighbor: neighbors(i, j)) {
            qu.union(siteId(i, j), siteId(neighbor[0], neighbor[1]));
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i<1) || (i>dim) || (j<1) || (j>dim))
            throw new IndexOutOfBoundsException("i, j must be in [1, N]");
        return sites[i-1][j-1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i<1) || (i>dim) || (j<1) || (j>dim))
            throw new IndexOutOfBoundsException("i, j must be in [1, N]");
        for (int col=1; col<=dim; col++)  {
            if (isOpen(1, col)) {
                if (qu.connected(siteId(i, j), siteId(1, col)))
                    return true;
            }
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int col=1; col<=dim; col++)  {
            if (isFull(dim, col))
                return true;
        }
        return false;
    }
}
