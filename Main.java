import java.io.File;
import java.util.ArrayList;
import java.util.List;

import files.FileManipulation;

//import graph.Kruskal;
import graph.Graph;

class Main {
  
  public static void main(String[] args) {
    List<Graph<Integer>> graphs = new ArrayList<>();
    for(File f : FileManipulation.readFromAdjacentFilesSet("files\\adjSet.txt"))
      graphs.add(new Graph<>("files\\" + f.toString(), true));
     
    graphs.forEach(graph -> {
      System.out.println(graph + "\n");
    });

    StringBuilder sb = new StringBuilder();
    graphs.forEach(graph -> sb.append(graph).append("\n"));

    FileManipulation.saveAnswer(sb.toString());
  }
}