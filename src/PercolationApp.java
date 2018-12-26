import java.util.Random;
import javax.swing.JOptionPane;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import processing.core.*;
public class PercolationApp extends PApplet 
{
	int[] grid;
	Percolation p;
	int n;
	int scl;
	Random r;
	
	public static void main(String[] args)
	{
		PApplet.main("PercolationApp");
	}
	
	public void settings()
	{
		size(800, 800);
	}
	
	public void setup()
	{
		background(0);
		stroke(255);
		fill(0);
		p = new Percolation(Integer.parseInt(JOptionPane.showInputDialog("How Many?")), this);
		grid = p.grid1;
		n = p.n;
		scl = height/n;
		r = new Random();
		for(int i = 0; i < width/n; i++)
		{
			for(int j = 0; j < height/n; j++)
			{
				rect(i*scl, j*scl, width/n, height/n);
			}
		}
	}
	
	public void draw()
	{
		background(0);
		grid = p.grid1;
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(grid[xyToInt(i,j)] == 0)
				{
					if(p.isFull(i,j))
					{
						stroke(0);
						fill(27, 107, 226);
						rect(i*scl, j*scl, width/n, height/n);
					}
					else
					{
						stroke(0);
						fill(255);
						rect(i*scl, j*scl, width/n, height/n);
					}
				}
				else if(grid[xyToInt(i,j)] == 1)
				{
					stroke(255);
					fill(0);
					rect(i*scl, j*scl, width/n, height/n);
				}
			}
		}
		
		if(p.percolates() == false)
		{
			if(frameCount % 1 == 0)
			{
				p.open(StdRandom.uniform(n), StdRandom.uniform(n));
				//p.open(0, 1);
			}
		}
		else
		{
			float open = 0;
			
			JOptionPane.showMessageDialog(null, "Percolation Successfull");
			for(int i = 0; i < grid.length; i++)
			{
				if(grid[i] == 0)
					open++;
			}
			JOptionPane.showMessageDialog(null, "Percolation at: " + open/(n*n) + " or " + (1 - StdStats.mean(grid)));
			noLoop();
		}
	}
	
	private int xyToInt(int i, int j)
	{
		return j * n + i;
	}
}
