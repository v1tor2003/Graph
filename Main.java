import java.util.List;

class Main{
  public static void main(String[] args) {
    String filePath = "Adj.txt";
    Graph<String> graph = new Graph<>();
    //graph.generateGraph(filePath);
    /*graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addEdge(0, 1);
    graph.addEdge(1, 0);
    graph.removeEdge(1, 0);*/
    graph.generateGraph(filePath);
    graph.showAdj();
    Character[] colors = graph.BreadthFirstSearch(0);
    for(Character c : colors)
      System.out.print(c + " ");


    graph.DijkstraShortestPath(0);

    List<List<Object>> resultsOfDijkstra = graph.DijkstraShortestPath(0);
    List<Object> predecessors= resultsOfDijkstra.get(0);
    List<Object> distances = resultsOfDijkstra.get(1);
  
    System.out.println("\nDistances:");
    distances.forEach(d -> {
      String dAsStr = d.toString();
      if(dAsStr.equals("2147483647"))
        dAsStr = "∞";
      System.out.print(dAsStr + " ");
    });
    System.out.println("\nPredecessors: ");
    predecessors.forEach(p -> {
      System.out.print(p + " ");
    });
  }
}