package bruteForce;

public class TSPSolution {

	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		
		//Input
		String fileLocation = "burma14.tsp";
		TSPSolver solution = new DynamicSolver(fileLocation);
		
		//Output
		String outputFile = solution.solve();
		System.out.println("TSP Solution placed in " + outputFile);
		
		
		long endTime = System.nanoTime();
		System.err.println(endTime - startTime + " ns");
	}
}
