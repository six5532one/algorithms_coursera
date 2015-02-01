public class PercolationStats   {
    private double[] thresholds;
    private int dim;
    private static double Z95 = 1.96;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)   {
        dim = N;
        thresholds = new double[T];
        for (int i=0; i<T; i++) {
            Percolation perc = new Percolation(N);
            thresholds[i] = doSimulation(perc);
        }
    }

    // return indices of random, blocked site
    private int[] getBlockedSite(Percolation perc)  {
        int row, col;
        row = col = 0;
        while (true)    {
            row = StdRandom.uniform(1, dim);
            col = StdRandom.uniform(1, dim);
            if (!perc.isOpen(row, col))
                break;
        }
        int[] result = {row, col};
        return result;
    }

    // perform simulation and return percolation threshold
    private double doSimulation(Percolation perc)  { 
        int numOpened = 0;
        while (!perc.percolates())  {
            int[] siteToOpen = getBlockedSite(perc);
            perc.open(siteToOpen[0], siteToOpen[1]);
            numOpened++;
        }
        return (numOpened * 1.0)/(dim * dim);
    }
    
    // sample mean of percolation threshold
    public double mean()    {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev()  {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()    {
        double result = this.mean() - Z95*this.stddev();
        return result;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh()  {
        return this.mean() + Z95*this.stddev();
    }

    // takes two command line args N, T
    // performs T independent experiments on an N-by-N grid
    // prints out the mean, std dev, and 95% confidence interval for the percolation threshold
    public static void main(String[] args)  {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.printf("mean = %f\n", percStats.mean());
        StdOut.printf("stddev = %f\n", percStats.stddev());
        //System.out.println(percStats.confidenceLo());
        //System.out.println(percStats.confidenceHigh());
        StdOut.println(new StringBuilder("95% confidence interval = ").append(percStats.confidenceLo()).append(", ").append(percStats.confidenceHigh()).toString());
    }
}
