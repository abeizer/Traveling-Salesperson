package bruteForce;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BruteTemp 
{
	
	private static BufferedReader br;
	private List<City> list;
	
	//Constructor calls the algorithm
	public BruteTemp(String filename)
	{
		brute(filename);
	}
	
	//Main algorithm body
	public void brute(String filename)
	{
		list = new LinkedList<City>();
		
		try
		{
			//read the file and store the information into a list
			br = new BufferedReader(new FileReader(filename));
			String currentLine = "";
			while( (currentLine = br.readLine()) != null)
			{
				String[] line = currentLine.split(" ");
				list.add( new City(line[0], line[1], line[2]) );
			}
			
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		
		for(City c : list)
		{
			c.printCity();
		}
		
	}//end constructor

	
	//Prints all permutations of a given String
	private static void permute(String tour, int left, int right)
	{
		if(left == right)
		{
			System.out.println(tour);
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
	
	
	//swaps two chars at the given positions in the String
	private static String swap(String tour, int foo, int bar)
	{
		//Convert String to a char array
		char[] t = tour.toCharArray();
		
		//swap
		char temp = t[foo];
		t[foo] = t[bar];
		t[bar] = temp;
		
		//Convert char array back to a String before returning
		tour = new String(t);
		return tour;
	}//end swap
	
	private class City
	{
		private String name;
		private float longitude;
		private float latitude;
		
		public City(String name, String longitude, String latitude)
		{
			this.name = name;
			this.longitude = Float.parseFloat(longitude);
			this.latitude = Float.parseFloat(latitude);
		}//end constructor
		
		//Setters and getters
		public String getName()
		{
			return this.name;
		}
		
		public float getLong()
		{
			return this.longitude;
		}
		
		public float getLat()
		{
			return this.latitude;
		}
		
		public void printCity()
		{
			System.out.println(name + " " + longitude + ", " + latitude);
		}
	}
	
	
}
