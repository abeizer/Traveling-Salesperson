package bruteForce;

public class TSPSolution {

	public static void main(String[] args) {
		
		
		//Input
		String fileLocation = "burma14.tsp";
		TSPSolver solution = new BruteForceSolver(fileLocation);
		
		//Output
		String outputFile = solution.solve();
		System.out.println("TSP Solution placed in " + outputFile);
			
		//Input
		solution = new DynamicSolver(fileLocation);
		
		//Output
		outputFile = solution.solve();
		System.out.println("TSP Solution placed in " + outputFile);

	}
}
