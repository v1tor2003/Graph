import java.util.List;
import java.util.ArrayList;

class Vertex<T> {
  private final int id;
  private Vertex<T> predecessor;
  private Integer distance; 
  private Character color;
  private List<Edge<T>> edges;
  private T data;

  Vertex(T data, int id){
    this.color = null;
    this.edges = new ArrayList<>();
    this.data = data;
    this.id = id;
    this.predecessor = null;
    this.distance = null;
  }
  
  void putEdge(Vertex<T> to, Integer weight){
    this.edges.add(new Edge<>(this, to, weight));
    // ? sugestion improve putting the start and the end of the edges
    //if(directed) return;
    //this.edges.add(new Edge<>(to, this, weight));
  }

  void removeEdgeFor(Vertex<T> to, Integer weight){
    this.edges.removeIf(e -> e.existsFor(this, to, weight));
  }

  void removeRelatedEdges(){
    this.edges.removeIf(e -> e.getStart() == this || e.getEnd() == this);
  }

  int edgeWeightTo(Vertex<T> to){
    for(Edge<T> e : this.edges)
      if(e.getEnd() == to) 
        return e.getWeight();
    
    return 1;
  }

  List<Vertex<T>> getNeighourhood(){
    List<Vertex<T>> neighourhood = new ArrayList<>();
    for(Edge<T> e : this.edges)
      if(e.getEnd() != null) 
        neighourhood.add(e.getEnd());
    
    return neighourhood;
  }

  boolean canHaveEdgeTo(Vertex<T> to){
    return this.exists() && to.exists();
  }

  boolean exists(){
    return this != null;
  }

  boolean isAdjacentTo(Vertex<T> u){
    for(Edge<T> e : this.edges)
      if(e.getEnd() == u)
        return true;

    return false;
  }

  void setColor(Character color){
    this.color = color;
  }

  Character getColor(){
    return this.color;
  }

  T getData() {
    return data;
  }

  void setData(T data){
    this.data = data;
  }

  int getId(){
    return this.id;
  }

  void setDistance(Integer distance){
    this.distance = distance;
  }

  Integer getDistance(){
    return this.distance;
  }

  void setPredecessor(Vertex<T> predecessor){
    this.predecessor = predecessor;
  }

  Vertex<T> getPredecessor(){
    return this.predecessor;
  }

  private String printAdjList(){
    String str = "";
    for(Edge<T> e : this.edges){
      str += "-> " + e.getEnd().getId();
    }
    return str;
  }

  public String toString(){
    return String.format("v%d: %s\n", this.id, this.printAdjList());
  }
}
