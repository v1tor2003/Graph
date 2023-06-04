import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

class Graph<T>{
  private final static int DEFAULT_WEIGHT = 1;
  private final static int POSITIVE_INFINITY = Integer.MAX_VALUE;
  private boolean directed;
  private boolean weighted;
  private List<Vertex<T>> vertices;

  public Graph(boolean directed, boolean weighted){
    this.vertices = new ArrayList<>();
    this.directed = directed;
    this.weighted = weighted;
  }

  public Graph(){
    this(false, false);
  }

  public void generateGraph(String path){
    if(!vertices.isEmpty()) vertices.clear();
    Integer[][] adjMatrice = turnInto2DArray(readFile(path));
    int n = adjMatrice.length;
    initializeGraph(n);
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        if(adjMatrice[i][j] == 1) addEdge(i, j);
  }

  private void initializeGraph(int n){
    for(int i = 0; i < n; i++)
      this.addVertex(null);
  }

  public void addVertex(T data){
    int id = this.vertices.size();
    this.vertices.add(new Vertex<>(data, id));
  }

  public void removeVertex(int vId){
    Vertex<T> v = vertices.get(vId);
    if(!v.exists()) return;
    v.removeRelatedEdges();
    this.vertices.remove(v);
  }
  
  public void addEdge(int vId, int uId, Integer weight){
    Vertex<T> v = vertices.get(vId);
    Vertex<T> u = vertices.get(uId);
    
    if(!v.canHaveEdgeTo(u)){
      System.out.println("Neither v nor u belong to the graph.");
      return;
    }
    v.putEdge(u, getWeight(weight).orElse(DEFAULT_WEIGHT));
  } 

  public void addEdge(int vId, int uId){
    this.addEdge(vId,uId, null);
  }

  public void removeEdge(int vId, int uId){
    this.removeEdge(vId, uId, null);
  }

  public void removeEdge(int vId, int uId, Integer weight){
    Vertex<T> v = vertices.get(vId);
    Vertex<T> u = vertices.get(uId);

    if(!v.canHaveEdgeTo(u)){
      System.out.println("Neither v nor u belong to the graph.");
      return;
    }
    v.removeEdgeFor(u, getWeight(weight).orElse(DEFAULT_WEIGHT));
  }

  private static Optional<Integer> getWeight(Integer value){
    return Optional.ofNullable(value);
  }

  public Character[] BreadthFirstSearch(int rootId){
    vertices.forEach(v -> v.setColor('W'));
    Vertex<T> root = vertices.get(rootId);
    root.setColor('G');
    Queue<Vertex<T>> queue = new LinkedList<>();
    queue.add(root);
    while(!queue.isEmpty()){
      Vertex<T> u = queue.poll();
      for(Vertex<T> v : vertices)
        if(u.isAdjacentTo(v) && v.getColor() == 'W'){
          v.setColor('B');
          queue.offer(v);
        }
      u.setColor('B');
    }
    
    return mountColorsArray();
  }

  private Character[] mountColorsArray(){
    List<Character> colors = new ArrayList<>();
    this.vertices.forEach(v -> colors.add(v.getColor()));
    return colors.toArray(new Character[0]);
  }

  private List<List<Object>> mountDijkstraResults(){
    List<Object> predecessors = new ArrayList<>();
    List<Object> distances = new ArrayList<>();
    this.vertices.forEach(v -> {
      if(v.getPredecessor() != null)
        predecessors.add(v.getPredecessor().getId());
      else 
        predecessors.add(v.getPredecessor());
      distances.add(v.getDistance());
    });

    List<List<Object>> results = new ArrayList<>();
    results.add(predecessors);
    results.add(distances);
    return results;
  }

  public void showAdj(){
    this.vertices.forEach(System.out::print);
  }
  
  public Character[] DeepFirstSearch(){
    vertices.forEach(v -> v.setColor('W'));
    for(Vertex<T> v : vertices)
      if(v.getColor() == 'W')
        visit(v, v.getPredecessor());

    return mountColorsArray();
  }

  private void visit(Vertex<T> root, Vertex<T> predecessor){
    root.setColor('G');
    for(Vertex<T> v : vertices)
      if(root.isAdjacentTo(v) && v.getColor() == 'B'){
        v.setPredecessor(root);
        visit(v, v.getPredecessor());
      }
    root.setColor('B');
  }

  public List<List<Object>> DijkstraShortestPath(int rootId){
    Set<Vertex<T>> S = new HashSet<>();
    Queue<Vertex<T>> Q = new LinkedList<>();
    
    this.vertices.forEach(v -> {
      v.setPredecessor(null);
      v.setDistance(POSITIVE_INFINITY);
      Q.add(v);
    });

    this.vertices.get(rootId).setDistance(0);

    while(!Q.isEmpty()){
      Vertex<T> u = getMinimumDistance(Q);
      S.add(u);
      Q.remove(u);
      for(Vertex<T> v : u.getNeighourhood()){
        if(v.getDistance() > (u.getDistance() + u.edgeWeightTo(v))){
          v.setDistance(u.getDistance() + u.edgeWeightTo(v));
          v.setPredecessor(u);
        }
      }
    }

    return mountDijkstraResults(); 
  }

  private Vertex<T> getMinimumDistance(Queue<Vertex<T>> Q){
    Vertex<T> minimum = Q.peek();
    int leastDistance = minimum.getDistance();
    
    for(Vertex<T> v : Q)
      if(v.getDistance() < leastDistance){
        leastDistance = v.getDistance();
        minimum = v;
      }

    return minimum;
  }

  private static Integer[][] turnInto2DArray(List<Integer> uniArray){
    int n = (int)((float) Math.sqrt(uniArray.size()));
    int k = 0;
    Integer[][] twoDArray = new Integer[n][n]; 
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        twoDArray[i][j] = uniArray.get(k++);
    
    uniArray = null;
    return twoDArray;
  }

  private static List<Integer> readFile(String path){
    try{
      BufferedReader reader = new BufferedReader(new FileReader(path));
      List<Integer> elements = new ArrayList<Integer>();      
    
      String line = reader.readLine();
      int n = Integer.parseInt(line);
     
      while((line = reader.readLine()) != null)
        for(String rawDimention : line.split(" "))
           elements.add(Integer.parseInt(rawDimention));

      reader.close();
      boolean validInput = elements.size() == (n * n);
      if(!validInput)
        throw new IllegalArgumentException("The quantity of values expected does not match the file.");
      return elements;
    }catch (IOException e){
      System.out.printf("An error occured trying to read %s.\n", path);
      e.printStackTrace();
    }
    return new ArrayList<Integer>();
  }  
}