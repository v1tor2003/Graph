package graph;
import java.util.Set;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

class Edge<T> {
  private boolean directed; // indicates whether the edge is directed or undirected.
  private Integer weight; // contains the edge weight
  private Vertex<T> source; // indicates the edge source vertice
  private Vertex<T> target; // indicates the edge target vertice

  /**
    Constructs a new edge with the specified source vertex, target vertex, weight, and directed flag.

    Desc: This constructor is used to create a new edge object with the provided source vertex, target vertex, weight, and directed flag. 
    It initializes the respective fields of the edge with the given parameters.
    The `source` parameter represents the source vertex of the edge.
    The `target` parameter represents the target vertex of the edge.
    The `weight` parameter represents the weight of the edge.
    The `directed` parameter indicates whether the edge is directed or undirected.

    By using this constructor, you can create an edge object to represent a connection between vertices in a graph, specifying the source and target vertices, the weight of the edge, and whether it is directed or undirected.

    @param source The source vertex of the edge.
    @param target The target vertex of the edge.
    @param weight The weight of the edge.
    @param directed A flag indicating whether the edge is directed or undirected.
  */

  Edge(Vertex<T> source, Vertex<T> target, Integer weight, boolean directed){
    this.source = source;
    this.target = target;
    this.weight = weight;
    this.directed = directed;
  } 

  /**
    Constructs a new edge with the specified source vertex and target vertex.
    The weight is set to null and the edge is considered as undirected.
  
    Desc: This constructor is a convenience constructor that creates a new edge object with the provided source vertex and target vertex. 
    It sets the weight of the edge to null and considers the edge as undirected.
    The `source` parameter represents the source vertex of the edge.
    The `target` parameter represents the target vertex of the edge.
    By using this constructor, you can create an edge object to represent an undirected connection between vertices in a graph without specifying a weight for the edge.

    @param source The source vertex of the edge.
    @param target The target vertex of the edge.
  */

  Edge(Vertex<T> source, Vertex<T> target){
    this(source, target, null, false);
  }

  /**
    Returns a set of connected vertices for this edge.

    Desc: This method returns a set of connected vertices for this edge. 
    The connected vertices include both the source vertex and the target vertex of the edge.
    The method creates a new `LinkedHashSet` and uses `Arrays.asList()` to create a list containing the source and target vertices. The `LinkedHashSet` ensures that the order of vertices is preserved and duplicates are not allowed.
    By calling this method on an edge object, you can obtain a set containing the source and target vertices connected by the edge.

    @return A set of connected vertices.
  */

  Set<Vertex<T>> connectedVertices(){
    return new LinkedHashSet<>(Arrays.asList(this.source, this.target));
  }

  /**
    Returns the source vertex of this edge.

    Desc: This method returns the source vertex of the edge. 
    It retrieves and returns the value of the `source` field.
    By calling this method on an edge object, you can obtain the source vertex connected by the edge.

    @return The source vertex.
  */

  public Vertex<T> getSource() {
    return source;
  }

  /**
    Returns the target vertex of this edge.

    Desc: This method returns the target vertex of the edge. 
    It retrieves and returns the value of the `target` field.
    By calling this method on an edge object, you can obtain the target vertex connected by the edge.

    @return The target vertex.
  */

  public Vertex<T> getTarget() {
    return target;
  }

  /**
    Returns the weight of this edge.

    Desc: This method returns the weight of the edge. 
    It retrieves and returns the value of the `weight` field.
    By calling this method on an edge object, you can obtain the weight associated with the edge.

    @return The weight of the edge.
  */

  public Integer getWeight() {
    return weight;
  }

  /**
    Checks if this edge is equal to the specified object.

    Desc: This method overrides the default implementation of the `equals()` method from the `Object` class.
    It checks if this edge is equal to the specified object.
    The method first performs basic reference equality checks to determine if the objects are the same instance or have the same class. 
    If either of these conditions is met, it returns `true`.
    If the object is of the same class as the edge, the method compares the source vertex, target vertex, and weight of both edges for equality using the `equals()` method.
    The weight comparison uses `Objects.equals()` to handle the case where the weight is `null` for one of the edges.
    If all the conditions for equality are met, the method returns `true`, indicating that the edges are equal. 
    Otherwise, it returns `false`.
    By using this method, you can compare two edge objects for equality based on their source vertex, target vertex, and weight.

    @param o The object to compare with.
    @return true if the objects are equal, false otherwise.
  */

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge<?> obj = (Edge<?>) o;
    return this.source.equals(obj.getSource()) && this.target.equals(obj.getTarget()) && this.weight.equals(obj.getWeight());
  }

  /**
    Returns the hash code value for this edge.

    Desc: This method overrides the default implementation of the `hashCode()` method from the `Object` class. 
    It calculates and returns the hash code value for this edge based on its source vertex, target vertex, and weight.
    The method uses the `Objects.hash()` method to compute the hash code by combining the hash codes of the source vertex, target vertex, and weight fields.
    By overriding the `hashCode()` method, you ensure that equal edges produce the same hash code value, which is necessary when using objects in hash-based data structures like hash sets or hash maps.

    @return The hash code value.
  */

  @Override
  public int hashCode() {
    return Objects.hash(source, target, weight);
  }

  /**
    Returns a string representation of this edge.

    Desc: This method returns a string representation of the edge. 
    It constructs a string that includes the source vertex, target vertex, and weight of the edge.
    If the edge is directed, the string representation is in the format "{source -> target}". 
    Otherwise, it is in the format "{source, target}". 
    The weight of the edge is appended to the string.
    The final string is enclosed in square brackets for clarity.
    By calling this method on an edge object, you can obtain a string representation of the edge, including its source and target vertices and weight.

    @return The string representation of the edge.
  */  

  public String toString(){
    String edgeInfo = "";
    edgeInfo += this.directed ? String.format("{%s -> %s}", this.source, this.target) : String.format("{%s, %s}", this.source, this.target);
    edgeInfo+= " weight: " + this.weight; 
    return String.format("[%s]", edgeInfo);
  }
}