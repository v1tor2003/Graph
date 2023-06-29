package graph;
import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    public DisjointSet() {
      parent = new HashMap<>();
      rank = new HashMap<>();
    }

    public void makeSet(T element) {
      parent.put(element, element);
      rank.put(element, 0);
    }

    public T find(T element) {
      if (!(parent.get(element).equals(element))) 
        parent.put(element, find(parent.get(element)));
      return parent.get(element);
    }

    public void union(T element1, T element2) {
      T root1 = find(element1);
      T root2 = find(element2);

      if(!(root1.equals(root2)))
        if(rank.get(root1) < rank.get(root2)) parent.put(root1, root2);
        else if(rank.get(root1) > rank.get(root2)) parent.put(root2, root1);
        else {
          parent.put(root2, root1);
          rank.put(root1, rank.get(root1) + 1);
        } 
    }
}
