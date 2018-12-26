/**
 * Alex Isaac
 * A01 Percolation
 * 2/4/17
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	//fields
	int[] grid1;
	int n;
	WeightedQuickUnionUF UF;
	int top;
	int bottom;
	
	public Percolation(int n)
	{
		if(n <= 0)
			throw new ArrayIndexOutOfBoundsException();
		else
		{
			//create union class
			UF = new WeightedQuickUnionUF(n*n+2);
			//get n locally
			this.n = n;
			
			//create the grid
			grid1 = new int[n*n];
			for(int i = 0; i < grid1.length; i++)
				grid1[i] = 1;
			
			//set top and bottom
			top = n*n;
			bottom = n*n + 1;
			
			//union virtual top and bottom
			for(int x = 0; x < n-1; x++)
			{
				//top
				UF.union(top, xyToInt(x, 0));
				//bottom
				UF.union(bottom, xyToInt(x, n-1));
			}
		}
	}
	
	public void open(int i, int j)
	{
		//check if its out of bounds
		if(i < 0 || j < 0 || i > n || j > n)
			throw new ArrayIndexOutOfBoundsException();
		
		//check if its already open
		if(grid1[xyToInt(i,j)] == 0)
			return;
		
		//set that grid location open
		grid1[xyToInt(i,j)] = 0;
		
		//check squares to left and right
		for(int x = -1; x <= 1; x++)
		{
			//if block is on edge or x == 0 (its own position), continue
			if(i + x < 0 || i + x > n - 1|| x == 0)
				continue;
			
			//if not then union the two blocks if the other is open
			else if(grid1[xyToInt(x+i,j)] == 0)
				UF.union(xyToInt(i,j), xyToInt(i+x, j));
		}
		
		//check squares above and below
		for(int y = -1; y <= 1; y++)
		{
			//if block is on top or bottom or y==0 (its own position), continue
			if(j + y < 0 || j + y > n - 1|| y == 0)
				continue;
			//if not then union the squares
			else if(grid1[xyToInt(i,y+j)] == 0)
				UF.union(xyToInt(i,j), xyToInt(i,j+y));
		}
	}
	
	public boolean isOpen(int i, int j)
	{
		//check if its out of bounds
		if(i < 0 || j < 0 || i > n || j > n)
			throw new ArrayIndexOutOfBoundsException();
		
		//check if its closed
		if(grid1[xyToInt(i,j)] == 1)
			return false;
		return true;
	}
	
	public boolean isFull(int i, int j)
	{
		//check if its out of bounds
		if(i < 0 || j < 0 || i > n || j > n)
			throw new ArrayIndexOutOfBoundsException();
		
		//if the block is connected to the top return true
		if(UF.connected(xyToInt(i,j), top))
			return true;
		return false;
	}
	
	public boolean percolates()
	{
		//if the top is connected to the bottom
		if(UF.connected(top, bottom))
			return true;
		return false;
	}
	
	private int xyToInt(int x, int y)
	{
		return (y * n) + x;
	}
}
