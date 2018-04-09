package bruteForce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import bruteForce.BruteForceSolver.City;

public class DynamicSolver implements TSPSolver{
	
	private String fileIn;
	private String fileOut = "DynamicOutput.tsp";
	private static List<City> cities = new LinkedList<>();
	private static double minTourDistance = 1000000000;
	private static int[] minTour = null;
	private static BufferedWriter bw;
	private static double[][] matrix = null;
	
	
	//Constructor
	public DynamicSolver(String filename) 
	{	
		this.fileIn = filename;
		
		try
		{
			//instantiate bufferedwriter
			bw = new BufferedWriter(new FileWriter(fileOut));
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}//end constructor
	
	@Override
	public String solve() 
	{
		try
		{
			//First, open the file
			BufferedReader br = new BufferedReader(new FileReader(fileIn));
			String currentLine = "";	//Current line of the file
			int i = 0;					//Stores the size of array required to hold a tour
			int[] tour = null;			//This will be the first tour permutation
			
			while(!(currentLine = br.readLine().trim()).startsWith("1"))
			{
				//consume line of the file and do not process it (header content)
			}
			
			//Once city definitions begin, read until eof and then close the BufferedReader
			//Add the city to the list, and the tour's name to the current tour
			while( !((currentLine).equals("EOF")) )
			{
				//Trim the string and split into an array, delimited by any number of spaces
				String[] split = currentLine.trim().split("\\s+");
		
				cities.add(new City(Float.parseFloat(split[1]), Float.parseFloat(split[2])));
				i++;
				currentLine = br.readLine().trim();
			}
			br.close();
			
			matrix = new double[i][i];
			tour = new int[i];
			for(int j = 0; j < tour.length; j++)
			{
				tour[j] = j + 1;
			}
			
			//Create file header
			bw.write("NAME: " + fileOut);				bw.newLine();
			bw.write("TYPE: TOUR");						bw.newLine();
			bw.write("DIMENSION: " + (i));			bw.newLine();
			bw.write("TOUR_SECTION");					bw.newLine();
			bw.flush();
			
			//begin permutations
	
			permute(tour, 1, tour.length - 1);
			
			
			for(int t : minTour)
			{
				bw.write(String.valueOf(t));
				bw.newLine();
				bw.flush();
			}
			bw.write("-1");
			bw.close();
			
			printMatrix();	
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		
		
		return fileOut;
	}//end solve
	
	
	//displays the contents of the matrix
	private void printMatrix()
	{
		System.out.println("\tMatrix:");
		
		for (int k = 0; k < matrix.length; k++) 
		{
		    for (int j = 0; j < matrix[k].length; j++) 
		    {
		        System.out.print(matrix[k][j] + "\t");
		    }
		    System.out.println();
		}
	}
	
	//Finds all permutations of a given tour
	private static void permute(int[] tour, int left, int right) throws IOException
	{
		if(left == right)
		{
			double tourDistance = getTourDistance(tour); 	
			if(tourDistance < minTourDistance && tourDistance != -1)
			{		
				minTourDistance = tourDistance;
				minTour = tour.clone();
			}
			for(int i : tour)
			{
				System.out.print(i);
			}
			System.out.println("\t" + tourDistance);
		}
		else
		{
			for(int i = left; i <= right; i++)
			{
				tour = swap(tour, left, i);
				permute(tour, left + 1, right);
				tour = swap(tour, left, i);
			}
		}//end if
	}//end permute
	
	private static double getTourDistance(int[] tour)
	{	
		double distance = 0;
		double tempDistance;
		double x, y, a, b;
		
		for(int i = 0; i < tour.length - 1; i++)
		{
			//if the matrix holds a value for this distance, then use it
			//If it does not, then calculate the value and then store it in the matrix
			if((tempDistance = matrix[tour[i] - 1][tour[i + 1] - 1]) != 0)
			{
				distance += tempDistance;
			}
			else
			{
				distance += calculateCityDistance( (tour[i] - 1), (tour[i + 1] - 1) );
				
			}//end if

			//early bail-out condition if the current distance already surpasses
			//the smallest recorded distance
			if(distance > minTourDistance)
			{
				return -1;
			}
		}
		
		//at the end of the array, calculate from last city back to home
		if( (tempDistance = matrix[tour[0] - 1][tour[tour.length-1] - 1]) != 0)
		{
			distance += tempDistance;
		}
		else
		{
			distance  += calculateCityDistance( (tour[0] - 1), (tour[tour.length-1] - 1) );
		}//end if
		
		return distance;
	}//end calculateTourDistance
	
	
	//Calculates the distance between two cities
	//Then stores the value in the matrix
	private static double calculateCityDistance(int city1, int city2)
	{
		double distance = 0.0;
		
		//tour at (the current number - 1) to adjust for indexing in cities
		double x = cities.get(city1).getLongitude();
		double y = cities.get(city1).getLatitude();
		//tour at (the next number - 1) to adjust for indexing in cities
		double a = cities.get(city2).getLongitude();
		double b = cities.get(city2).getLatitude();
		
		distance = Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
		matrix[city1][city2] = distance;
		matrix[city2][city1] = distance;
		
		return distance;
	}
	
	
	//swaps two chars at the given positions in the String
	private static int[] swap(int[] tour, int foo, int bar)
	{
		int temp = tour[foo];
		tour[foo] = tour[bar];
		tour[bar] = temp;
		
		return tour;
	}//end swap

	
	static class City
	{
		private double longitude, latitude;
		
		public City(double longitude, double latitude)
		{
			this.longitude = longitude;
			this.latitude = latitude;
		}
		
		public double getLongitude()
		{
			return this.longitude;
		}
		
		public double getLatitude()
		{
			return this.latitude;
		}
	}//end inner class City
}//end outer class Dynamic
