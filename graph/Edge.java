package graph;
import java.util.Set;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

class Edge<T> {
  private boolean directed;
  private Integer weight;
  private Vertex<T> source;
  private Vertex<T> target;

  Edge(Vertex<T> source, Vertex<T> target, Integer weight, boolean directed){
    this.source = source;
    this.target = target;
    this.weight = weight;
    this.directed = directed;
  } 

  Edge(Vertex<T> source, Vertex<T> target){
    this(source, target, null, false);
  }

  Set<Vertex<T>> connectedVertices(){
    return new LinkedHashSet<>(Arrays.asList(this.source, this.target));
  }

  public Vertex<T> getSource() {
    return source;
  }

  public Vertex<T> getTarget() {
    return target;
  }

  public Integer getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge<?> obj = (Edge<?>) o;
    return this.source.equals(obj.getSource()) && this.target.equals(obj.getTarget()) && this.weight.equals(obj.getWeight());
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, target, weight);
  }

  public String toString(){
    String edgeInfo = "";
    edgeInfo += this.directed ? String.format("{%s -> %s}", this.source, this.target) : String.format("{%s, %s}", this.source, this.target);
    edgeInfo+= " weight: " + this.weight; 
    return String.format("[%s]", edgeInfo);
  }
}