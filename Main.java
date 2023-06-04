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
    Character[] colors = graph.DeepFirstSearch();
    for(Character c : colors)
      System.out.print(c + " ");
  }
}