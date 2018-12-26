/**
 * Alex Isaac
 * A01 Percolation
 * 2/4/17
 */
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats; 
public class PercolationStats 
{
	//fields
	double[] means;
	double mean;
	double stdDev;
	int[] grid;
	int t;
	
	public static void main(String[] args)
	{
		//run the simulation
		PercolationStats p = new PercolationStats(100, 200);
	}
	
	//create a new stat class
	public PercolationStats(int n, int T)
	{
		//if n or T is less than 0 throw an exception
		if(n < 0 || T < 0)
			throw new IllegalArgumentException();
		
		//inizialize variables for use outsize of this constructor
		means = new double[T];
		this.t = T;
		
		//start the simulations, go until t is 0
		while(T > 0)
		{
			//create a new percolation
			Percolation p = new Percolation(n);
			
			//open spots until it percolates
			while(!p.percolates())
			{
				p.open(StdRandom.uniform(n), StdRandom.uniform(n));
			}
			
			//get the final grid from the percolation
			grid = p.grid1;
			
			//add the mean from that grid to our collection of means here
			//have to subtract it from one because i made 0's open and 1's closed, so its flipped
			means[T-1] = 1 - StdStats.mean(grid);
			
			//decrement t
			T--;
		}
		
		//after all the simulations are done, print out the info
		System.out.println("mean()\t= " + mean());
		System.out.println("stddev()\t= " + stddev());
		System.out.println("confidenceLow()\t= " + confidenceLow());
		System.out.println("confidenceHigh()\t= " + confidenceHigh());
	}
	
	public double mean()
	{
		//assign the average of the means array to mean
		mean = StdStats.mean(means);
		return mean;
	}
	
	public double stddev()
	{
		//assign the standard deviation of the means array to stdDev
		stdDev = StdStats.stddev(means);
		return stdDev;
	}
	
	public double confidenceLow()
	{
		//used the provided formula to calculate the confidenceLow
		return mean - (1.96*stdDev)/(Math.sqrt(t));
	}
	
	public double confidenceHigh()
	{
		//used the provided formula to calculate the confidenceHigh
		return mean + (1.96*stdDev)/(Math.sqrt(t));
	}
}
