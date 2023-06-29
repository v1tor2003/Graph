package graph;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Kruskal {
  public static <T> Set<Edge<T>> MST(Graph<T> graph){
    Set<Edge<T>> A = new LinkedHashSet<>();
    DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
    graph.getVertices().forEach(v -> disjointSet.makeSet(v)); // O(V)
    SortedSet<Edge<T>> sortedEdges = new TreeSet<>(new Comparator<Edge<T>>() {
      public int compare(Edge<T> e1, Edge<T> e2){
        return Integer.compare(e1.getWeight(), e2.getWeight());
      } 
    });
    sortedEdges.addAll(graph.getEdges());   // O(log E)
    sortedEdges.forEach(e -> {              // O(E)
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
