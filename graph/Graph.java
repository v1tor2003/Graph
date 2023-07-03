package graph;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import files.FileManipulation;

public class Graph<T> {
  private boolean directed;  // indicates whether the graph is directed or undirected.
  private boolean weighted;  // indicates whether the graph is weighted or unweighted.
  private Set<Vertex<T>> vertices; // The set of vertices in the graph.
  private Set<Edge<T>> edges; // The set of edges in the graph.

  /**
    Constructs a graph by loading the graph edges data from a file and with specified properties.
    The graph can be either directed or undirected based on the 'directed' parameter.
    The edges can be weighted if the 'weighted' parameter is set to true.
    If a valid file path is provided, the graph data is read from the file.

    Desc: This constructor initializes a graph with specified properties. 
    The `directed` parameter determines whether the graph is directed or undirected. 
    If `directed` is set to `true`, the graph is directed; otherwise, it is undirected. 
    The `weighted` parameter specifies whether the edges in the graph are weighted or not.
    The constructor initializes the vertex set and edge set as empty `HashSet`. 
    If a valid file path is provided (`path != null`), it reads the graph data from the file using the `readAdjacentMatrix` method.


    @param path The path to the file containing the graph edge data.
    @param directed Specifies whether the graph is directed (true) or undirected (false).
    @param weighted Specifies whether the edges in the graph are weighted or not.
  */

  public Graph(String path, boolean directed, boolean weighted){
    this.directed = directed;
    this.weighted = weighted;
    this.vertices = new HashSet<>();
    this.edges = new HashSet<>();
    if(path != null) this.readAdjacentMatrix(path);
  }

  /**
    Constructs an empty graph with specified properties.
    The graph can be either directed or undirected based on the 'directed' parameter.
    The edges can be weighted if the 'weighted' parameter is set to true.
    
    Desc: This constructor initializes an empty graph with specified properties. 
    The `directed` parameter determines whether the graph is directed or undirected. 
    If `directed` is set to `true`, the graph is directed; otherwise, it is undirected. 
    The `weighted` parameter specifies whether the edges in the graph are weighted or not.
    The constructor calls another constructor with the `path` parameter set to `null` to indicate that no file path is provided. 
    It passes the `directed` and `weighted` parameters to the other constructor to handle the graph construction.
    
    @param directed Specifies whether the graph is directed (true) or undirected (false).
    @param weighted Specifies whether the edges in the graph are weighted or not.
  */

  public Graph(boolean directed, boolean weighted){
    this(null, directed, weighted);
  }

  /**
    Constructs a graph by loading the graph data from a file.
    By using this constructor the graph can only be undirected
    The edges can be weighted if the 'weighted' parameter is set to true.
  
    Desc: This constructor constructs a graph by loading the graph edges data from a file specified by the `path` parameter. 
    The `weighted` parameter indicates whether the edges in the graph are weighted or not.
    The constructor calls another constructor with the `directed` parameter set to `false` to specify that the graph is not directed. 
    It passes the `path` and `weighted` parameters to the other constructor to handle the graph construction.

    @param path The path to the file containing the graph data.
    @param weighted Specifies whether the edges in the graph are weighted or not.
  */

  public Graph(String path, boolean weighted){
    this(path, false, weighted);
  }

  /**
    Constructs an empty graph.
    By default, the graph is not directed and does not allow parallel edges.

    Desc: This constructor initializes an empty graph with default settings. 
    By default, the graph is not directed (undirected) and does not allow parallel edges (multiple edges between the same pair of vertices). The constructor calls another constructor with the `directed` and `allowParallelEdges` parameters set to `false` to specify these default settings.

  */

  public Graph(){
    this(false, false);
  }

  /**
    Adds a vertex to the graph with the specified data value.

    Desc: This function adds a new vertex to the graph with the specified data value. 
    It creates a new vertex object using the given data value and adds it to the graph's set of vertices.

    @param data The data value of the vertex to be added.
  */

  public void addVertex(T data){
    this.vertices.add(new Vertex<T>(data));
  }

  /**
    Removes a vertex from the graph by its data value.

    Desc: This function removes a vertex from a graph based on its data value. 
    It creates a new vertex object with the given data value and checks if 
    it exists in the graph's set of vertices. 
    If the vertex is found, it is removed from the graph. 
    If the vertex is not found, a `NoSuchElementException` is thrown to indicate that the vertex was not found in the graph.

    @param data The data value of the vertex to be removed.
    @throws NoSuchElementException if the vertex with the specified data value is not found.
  */

  public void removeVertex(T data){
    Vertex<T> v = new Vertex<T>(data);
    if(this.vertices.contains(v)) this.vertices.remove(v);
    else throw new NoSuchElementException(String.format("Vertex \"%s\" was not found", data));
  }

  /**
    Adds an edge to the graph between two vertices with optional weight.

    Desc: This method adds an edge to the graph between two vertices with an optional weight. 
    If the graph is not weighted (`this.weighted` is `false`), the provided weight is not considered, and a message is printed to inform that the weight was not used.
    It creates two `Vertex` objects using the given `vData` and `uData`. If both vertices exist in the graph's vertex set, a new `Edge` object is created with the vertices and the specified weight (or `null` if the graph is not weighted). 
    The edge is then added to the graph's edge set.
    If either of the vertices is not found in the graph, a `NoSuchElementException` is thrown with a message indicating that either `vData` or `uData` (or both) were not found in the graph.
    If the `this.directed` is set to `true`, then the direction of this new edge is vData to uData

    @param vData The data value of the first vertex.
    @param uData The data value of the second vertex.
    @param weight The weight of the edge (optional).
    @throws NoSuchElementException if either of the vertices is not found in the graph.
  */

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

  /**
    Removes an edge from the graph between two vertices with the specified weight.

    Desc: This method removes an edge from the graph between two vertices with the specified weight. 
    It creates `Vertex` objects using the given `vData` and `uData`, and an `Edge` object using those vertices and the provided weight.
    If both vertices and the edge are found in the graph's vertex set and edge set, respectively, the edge is removed from the graph's edge set using the `remove` method. 
    If the edge is not found in the graph, a `NoSuchElementException` is thrown with a message indicating that the edge was not found.

    @param vData The data value of the first vertex.
    @param uData The data value of the second vertex.
    @param weight The weight of the edge.
    @throws NoSuchElementException if the edge is not found in the graph.
  */

  public void removeEdge(T vData, T uData, Integer weight){
    Vertex<T> v = new Vertex<T>(vData), u = new Vertex<T>(uData);
    Edge<T> e = new Edge<T>(v, u, weight, this.directed);
    if(this.vertices.contains(v) && this.vertices.contains(u) && this.edges.contains(e))
      this.edges.remove(e);
    else throw new NoSuchElementException(String.format("Edge %s was not found.", e.toString()));
  }

  /**
    Adds an unweighted edge to the graph between two vertices.

    Desc: This method adds an unweighted edge to the graph between two vertices. 
    It delegates the task to the `putEdge` method with three parameters, where the third parameter (weight) is set to `null`. 
    This effectively adds an edge without any weight specified.
    By calling `putEdge(vData, uData, null)`, it ensures that the edge added between the vertices `vData` and `uData` does not have an explicit weight associated with it.
 
    @param vData The data value of the first vertex.
    @param uData The data value of the second vertex.
  */

  public void putEdge(T vData,T uData){
    this.putEdge(vData, uData, null);
  }

  /**
    Removes an edge from the graph between two vertices without considering the weight.

    Desc: This method removes an edge from the graph between two vertices without considering the weight. 
    It delegates the task to the `removeEdge` method with three parameters, where the third parameter (weight) is set to `null`. 
    This effectively removes an edge between the vertices `vData` and `uData` without considering its weight.
    By calling `removeEdge(vData, uData, null)`, it ensures that the edge to be removed is not specific to any particular weight.

    @param vData The data value of the first vertex.
    @param uData The data value of the second vertex.
  */

  public void removeEdge(T vData, T uData){
    this.removeEdge(vData, uData, null);
  }

  /**
    Retrieves the neighborhood vertices of a given source vertex.

    Desc: This method retrieves the neighborhood vertices of a given source vertex. 
    It creates a `Vertex` object using the `source` parameter and initializes an empty `HashSet` called `neighborhood` to store the neighborhood vertices.
    The method iterates over each edge in the graph's edge set using the `forEach` method. If the source vertex of an edge matches the given source vertex `v`, it means the target vertex of that edge is in the neighborhood. In that case, the target vertex is added to the `neighborhood` set.
    Additionally, if the graph is undirected (`this.directed` is `false`), it checks if the target vertex of an edge matches the given source vertex `v`. In this case, and only in an undirected graph, the source vertex of that edge is added to the `neighborhood` set.
    Finally, the method returns the `neighborhood` set, which contains all the neighboring vertices of the given source vertex.

    @param source The data value of the source vertex.
    @return A set of vertices representing the neighborhood of the source vertex.
  */

  public Set<Vertex<T>> getNeighborhood(T source){
    Vertex<T> v = new Vertex<T>(source);
    Set<Vertex<T>> neighborhood = new HashSet<>();
    this.edges.forEach(edge -> {
      if(edge.getSource().equals(v)) neighborhood.add(edge.getTarget());
      else if (edge.getTarget().equals(v) && !this.directed) neighborhood.add(edge.getSource());
    });
    return neighborhood;
  }

  /**
    Reads an adjacency matrix from a file and updates the graph based on its contents.

    Desc: This method reads an adjacency matrix from a file and updates the graph based on its contents. 
    It takes the `path` parameter specifying the path to the file containing the adjacency matrix.
    The method begins by clearing the existing vertices and edges in the graph, if any. 
    Then, it reads the adjacency matrix from the file and converts it into a 2D array using the `turnInto2DArray` method from the `FileManipulation` class. The `initializeGraph` method is called to set up the initial graph structure based on the size of the adjacency matrix.
    Next, it iterates over each element of the adjacency matrix using two nested `for` loops. 
    If an element in the matrix is non-zero, it indicates the presence of an edge between the corresponding vertices. 
    The `putEdge` method is called to add the edge to the graph, with the appropriate vertices and weight.

    Note: The casting `(T) Integer.valueOf(i)` and `(T) Integer.valueOf(j)` is used to convert the indices `i` and `j` to the generic type `T`. Similarly, `(Integer) adjMatrix[i][j]` is used to cast the weight to `Integer`.
    The `@SuppressWarnings("unchecked")` annotation is used to suppress unchecked warnings related to these castings.

    @param path The path to the file containing the adjacency matrix.
  */

  @SuppressWarnings("unchecked")
  public void readAdjacentMatrix(String path){
    if(!this.vertices.isEmpty()) this.vertices.clear();
    if(!this.edges.isEmpty()) this.edges.clear();
    T[][] adjMatrix = FileManipulation.turnInto2DArray(FileManipulation.readFile(path));
    initializeGraph(adjMatrix);
    int n = adjMatrix.length;
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        if(!adjMatrix[i][j].equals(0)) 
          this.putEdge((T) Integer.valueOf(i), (T) Integer.valueOf(j), (Integer) adjMatrix[i][j]);
  }

  /**
    Initializes the graph based on the provided adjacency matrix.

    Desc: This method initializes the graph based on the provided adjacency matrix. 
    It takes the `adjMatrix` parameter, which is a 2D array representing the adjacency matrix of the graph.
    The method starts by retrieving the size of the adjacency matrix, which represents the number of vertices in the graph.
    Next, it iterates over the indices from 0 to `n-1` (where `n` is the size of the adjacency matrix). 
    For each index, it adds a vertex to the graph using the `addVertex` method, with the index value converted to the generic type `T` using `(T) Integer.valueOf(i)`.

    Note: The casting `(T) Integer.valueOf(i)` is used to convert the index `i` to the generic type `T`.
    The `@SuppressWarnings("unchecked")` annotation is used to suppress unchecked warnings related to this casting.

    @param adjMatrix The adjacency matrix representing the graph.
  */

  @SuppressWarnings("unchecked")
  public void initializeGraph(T[][] adjMatrix){
    int n = adjMatrix.length;
    for(int i = 0; i < n; i++){
      this.addVertex((T) Integer.valueOf(i));
    }
  }

  /**
    Retrieves the set of vertices in the graph.

    Desc: This method returns the set of vertices in the graph. 
    It simply returns the `vertices` set, which contains all the vertices of the graph.

    @return A set of vertices in the graph.
  */

  public Set<Vertex<T>> getVertices() {
    return vertices;
  }

  /**
    Retrieves the set of edges in the graph.

    Desc: This method returns the set of edges in the graph. 
    It simply returns the `edges` set, which contains all the edges of the graph.

    @return A set of edges in the graph.
  */

  public Set<Edge<T>> getEdges() {
    return edges;
  }

  /**
    Returns a string representation of the graph's settings.

    Desc: This private method returns a string representation of the graph's settings. 
    It concatenates the appropriate keywords based on the `directed` and `weighted` properties of the graph. 
    If the graph is directed (`this.directed` is `true`), the string "directed" is appended. Otherwise, the string "undirected" is appended.
    Similarly, if the graph is weighted (`this.weighted` is `true`), the string " and weighted" is appended. 
    Otherwise, the string " and unweighted" is appended.
    The resulting string represents the settings of the graph and is returned as the output.

    @return A string describing the graph's settings.
  */

  private String graphSettings(){
    String str = "";
    str += this.directed ? "directed" : "undirected";
    str += this.weighted ? " and weighted" : " and unweighted";
    return str;
  }

  /**
    Returns a string representation of the graph.

    Desc: This `toString()` method overrides the default implementation of the `toString()` method from the `Object` class. 
    It returns a string representation of the graph.
    The returned string is formatted using `String.format()`. 
    It includes information about the graph's settings (`this.graphSettings()`), the set of vertices (`this.vertices`), and the set of edges (`this.edges`).
    The resulting string has the format: "Graph G [settings] such that G = (V, E):\n(V = [vertices],\nE = [edges])".

    Note that the `graphSettings()` method is called to retrieve the graph's settings as a string.

    @return A string representation of the graph.
  */

  @Override
  public String toString() {
    return String.format("Graph G %s such as G = (V, E): \n(V = %s,\n E = %s)", this.graphSettings(), this.vertices, this.edges);
  }
}