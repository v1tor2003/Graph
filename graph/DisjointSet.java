package graph;
import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    /**
      Constructs a new DisjointSet object.

      Desc: This constructor initializes a new DisjointSet object. 
      It creates empty hash maps for the parent and rank data structures used in the disjoint set implementation.
      The `parent` map is used to store the parent of each element in the disjoint set. 
      Initially, each element is its own parent, representing a disjoint set with only one element.
      The `rank` map is used to keep track of the rank (or depth) of each element's subtree. 
      It is used to optimize the union operation by always attaching the smaller tree to the root of the larger tree, which helps maintain a balanced tree structure.
      By calling this constructor, you can create a new DisjointSet object to manage disjoint sets.

    */

    public DisjointSet() {
      parent = new HashMap<>();
      rank = new HashMap<>();
    }

    /**
      Creates a new disjoint set with the specified element.

      Desc: This method creates a new disjoint set with the specified element. 
      It initializes the parent and rank of the element in the disjoint set data structure.
      The `parent` map associates the element with itself as its parent, indicating that it is the representative (or root) of its own set.
      The `rank` map sets the rank of the element to 0, indicating that it is the only element in its set and has no children.
      By calling this method on a DisjointSet object, you can create a new set with the specified element and add it to the disjoint set data structure.

      @param element The element to create a set with.
    */

    public void makeSet(T element) {
      parent.put(element, element);
      rank.put(element, 0);
    }

    /**
      Finds the representative (root) of the set that the specified element belongs to.
      Performs path compression during the find operation for optimization.

      Desc: This method finds the representative (root) of the set that the specified element belongs to in the disjoint set data structure. 
      It utilizes path compression to optimize future find operations.
      Initially, it retrieves the parent of the element from the `parent` map. 
      If the element is not its own parent (i.e., it is not the representative), the method recursively applies the find operation on the parent until the representative is reached. 
      During this process, the element's parent is updated to the representative, effectively compressing the path and reducing the height of the tree.
      Finally, the method returns the representative of the set that the element belongs to.
      By calling this method on a DisjointSet object, you can find the representative (root) of a specific element and perform path compression to optimize future find operations.

      @param element The element to find the representative of.
      @return The representative (root) of the set.
    */

    public T find(T element) {
      if (!(parent.get(element).equals(element))) 
        parent.put(element, find(parent.get(element)));
      return parent.get(element);
    }

    /**
      Unions the sets that contain the specified elements by merging them based on their ranks.
      Performs rank-based union to maintain a balanced tree structure.

      Desc: This method unions (merges) the sets that contain the specified elements in the disjoint set data structure.
      It uses rank-based union to maintain a balanced tree structure and optimize future find operations.
      First, it finds the representatives (roots) of the sets that the elements belong to using the `find` method.
      If the roots are not equal (indicating that the elements are in different sets), the method compares the ranks of the roots. 
      The root with the lower rank is attached to the root with the higher rank. If the ranks are equal, one of the roots is chosen as the parent, and the rank of the chosen root is incremented.
      By calling this method on a DisjointSet object, you can union (merge) the sets that contain the specified elements based on their ranks, ensuring a balanced tree structure in the disjoint set data structure.

      @param element1 The first element.
      @param element2 The second element.
    */

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
