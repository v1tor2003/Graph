import graph.Graph;

class Main {
  public static void main(String[] args) {
    String filePath = "files\\Adj.txt";
    Graph<Character> graph = new Graph<>(true, false);
    graph.addVertex('A');
    graph.addVertex('B');
    graph.addVertex('C');
    graph.putEdge('A', 'B');
    graph.putEdge('C', 'A');
    
    System.out.println(graph);
    System.out.println(graph.getNeighborhood('A'));
  }
}