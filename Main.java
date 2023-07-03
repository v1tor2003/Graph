import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import files.FileManipulation;
import graph.Graph;
import graph.Kruskal;

class Main {
  public static void main(String[] args) {
    // Create an empty list to store the graphs
    List<Graph<Integer>> graphs = new ArrayList<>();
    // Define an array of degrees representing the number of vertices in each graph
    int[] degrees = new int[] {500};
    // Record the start time
    Instant start = Instant.now();
    // Generate the set of adjacent files with the specified degrees and random boundary
    FileManipulation.createAdjacentFilesSet(degrees, 10);
    // Record the end time
    Instant end = Instant.now();
    // Print the time taken to generate the set of files
    System.out.println("Time to generate the set of files: " + Duration.between(start, end));
    // Read the adjacent files from the generated set and create graphs from them
    for(File f : FileManipulation.readFromAdjacentFilesSet("files\\adjSet.txt"))
      graphs.add(new Graph<>("files\\" + f.toString(), true));

    // Create a StringBuilder to store the MST results
    StringBuilder sb = new StringBuilder();
    // Iterate over each graph in the list
    int i = 0;
    for(Graph<Integer> graph : graphs){
      // Record the start time
      start = Instant.now();
      // Compute the minimum spanning tree (MST) of the graph using Kruskal's algorithm
      sb.append(Kruskal.MST(graph)).append("\n");
      // Record the end time
      end = Instant.now();
      // Checks if i is still in the degrees index boudanry
      if(i < degrees.length)
        // Print the time taken to run Kruskal's MST for the current graph
        System.out.println(String.format("Time to run Kruskal's MST for a %d vertice graph: %s", degrees[i++], Duration.between(start, end).toString()));
    }
    // Save the MST results to a file
    FileManipulation.saveAnswer(sb.toString());
  }
}