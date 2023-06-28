package graph;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import files.FileManipulation;

public class Graph<T>{
  private boolean directed;
  private boolean weighted;
  private Set<Vertex<T>> vertices;
  private Set<Edge<T>> edges; 

  public Graph(String path, boolean directed, boolean weighted){
    this.directed = directed;
    this.weighted = weighted;
    this.vertices = new HashSet<>();
    this.edges = new HashSet<>();
    if(path != null) this.readAdjacentMatrice(path);
  }

  public Graph(boolean directed, boolean weighted){
    this(null, directed, weighted);
  }

  public Graph(String path, boolean weighted){
    this(path, false, weighted);
  }

  public Graph(){
    this(false, false);
  }

  public void addVertex(T data){
    this.vertices.add(new Vertex<T>(data));
  }

  public void removeVertex(T data){
    Vertex<T> v = new Vertex<T>(data);
    if(this.vertices.contains(v)) this.vertices.remove(v);
    else throw new NoSuchElementException(String.format("Vertex \"%s\" was not found", data));
  }

  public void putEdge(T vData, T uData, Integer weight){
    Integer newWeight = weight;

    if(!this.weighted && weight != null){
      System.out.println(String.format("Non-weighted graph, the given weight (%s) was not considered.", weight));
      newWeight = null;
    }

    Vertex<T> v = new Vertex<T>(vData), u = new Vertex<T>(uData);
    if(this.vertices.contains(v) && this.vertices.contains(u))
      this.edges.add(new Edge<T>(v, u, newWeight, this.directed));
    else throw new NoSuchElementException(String.format("Neither %s or %s was found.", vData, uData));
  }

  public void removeEdge(T vData, T uData, Integer weight){
    Vertex<T> v = new Vertex<T>(vData), u = new Vertex<T>(uData);
    Edge<T> e = new Edge<T>(v, u, weight, this.directed);
    if(this.vertices.contains(v) && this.vertices.contains(u) && this.edges.contains(e))
      this.edges.remove(e);
    else throw new NoSuchElementException(String.format("Edge %s was not found.", e.toString()));
  }

  public void putEdge(T vData,T uData){
    this.putEdge(vData, uData, null);
  }

  public void removeEdge(T vData, T uData){
    this.removeEdge(vData, uData, null);
  }

  public Set<Vertex<T>> getNeighborhood(T source){
    Vertex<T> v = new Vertex<T>(source);
    Set<Vertex<T>> neighborhood = new HashSet<>();
    this.edges.forEach(edge -> {
      if(edge.getSource().equals(v)) neighborhood.add(edge.getTarget());
      else if (edge.getTarget().equals(v) && !this.directed) neighborhood.add(edge.getSource());
    });
    return neighborhood;
  }
  /* //TODO
    getNeighborhood //
    BreathFirstSearch()//
    DeepFirstSearch()//
  */ 

  @SuppressWarnings("unchecked")
  public void readAdjacentMatrice(String path){
    if(!this.vertices.isEmpty()) this.vertices.clear();
    if(!this.edges.isEmpty()) this.edges.clear();
    T[][] adjMatrice = FileManipulation.turnInto2DArray(FileManipulation.readFile(path));
    initializeGraph(adjMatrice);
    int n = adjMatrice.length;
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        if(!adjMatrice[i][j].equals(0)) 
          this.putEdge((T) Integer.valueOf(i), (T) Integer.valueOf(j), (Integer) adjMatrice[i][j]);
  }

  @SuppressWarnings("unchecked")
  public void initializeGraph(T[][] adjMatrice){
    int n = adjMatrice.length;
    for(int i = 0; i < n; i++){
      this.addVertex((T) Integer.valueOf(i));
    }
  }

  private String graphSettings(){
    String str = "";
    str += this.directed ? "directed" : "not directed";
    str += this.weighted ? " and weighted" : " and not weighted";
    return str;
  }

  @Override
  public String toString() {
    return String.format("Graph G %s such as G = (V, E): \n(V = %s,\n E = %s)", this.graphSettings(), this.vertices, this.edges);
  }
      
}