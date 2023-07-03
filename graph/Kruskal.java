package graph;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Kruskal {
  /**
    Computes the Minimum Spanning Tree (MST) of a given graph using Kruskal's algorithm.
    
    Desc: This method computes the Minimum Spanning Tree (MST) of a given graph using Kruskal's algorithm. 
    It takes a `graph` as input and returns a set of edges representing the Minimum Spanning Tree of the graph.
    The method initializes an empty set `A` to store the MST edges and creates a `DisjointSet` to keep track of disjoint sets of vertices.
    It then sorts the edges of the graph in ascending order based on their weights using a `TreeSet` and a custom comparator.
    Next, it iterates over the sorted edges. For each edge, it checks if adding the edge to `A` would create a cycle in the MST. If not, the edge is added to `A`, and the disjoint sets of the source and target vertices are unioned using the `union` method of `DisjointSet`.
    Finally, the method returns the set `A`, which represents the Minimum Spanning Tree of the graph.

    The time complexity of this method is O(|E| log |E|), where E is the number of edges in the graph.
    
    @param graph The graph for which to compute the MST.
    @return A set of edges forming the Minimum Spanning Tree of the graph.  
  */
  
  public static <T> Set<Edge<T>> MST(Graph<T> graph){
    Set<Edge<T>> A = new LinkedHashSet<>();
    DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
    graph.getVertices().forEach(v -> disjointSet.makeSet(v)); 
    SortedSet<Edge<T>> sortedEdges = new TreeSet<>(new Comparator<Edge<T>>() {
      public int compare(Edge<T> e1, Edge<T> e2){
        return Integer.compare(e1.getWeight(), e2.getWeight());
      } 
    });
    sortedEdges.addAll(graph.getEdges());   
    sortedEdges.forEach(e -> {              
      Vertex<T> source = e.getSource();
      Vertex<T> target = e.getTarget();
      if(!(disjointSet.find(source).equals(disjointSet.find(target)))){
        A.add(e);
        disjointSet.union(source, target);
      }
    });
    return A;
  }
}
