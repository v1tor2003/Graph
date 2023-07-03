package graph;
import java.util.Objects;

class Vertex<T>{
  private T data; // data payload of the vertex

  /**
    Constructs a new vertex with the given data.

    Desc: This constructor is used to create a new vertex object with the provided data. 
    It initializes the `data` field of the vertex with the given `data` parameter. 
    The `data` parameter represents the data associated with the vertex, which can be of any type `T` depending on the graph implementation.

    @param data The data associated with the vertex.
  */

  Vertex(T data){
    this.data = data;
  }

  /**
    Returns the data associated with the vertex.

    Desc: This method returns the data associated with the vertex. 
    It simply returns the value of the `data` field of the vertex. 
    The data can be of any type `T` depending on the graph implementation.

    @return The data associated with the vertex.
  */

  public T getData(){
    return this.data;
  }
  
  /**
    Sets the data associated with the vertex.

    Desc: This method sets the data associated with the vertex to the specified `data` value. 
    It updates the `data` field of the vertex with the provided value. 
    The data can be of any type `T` depending on the graph implementation.

    @param data The new data to be associated with the vertex.
  */

  public void setData(T data) {
    this.data = data;
  }

  /**
    Checks if this vertex is equal to the specified object.

    Desc: This method overrides the default implementation of the `equals()` method from the `Object` class. 
    It checks if this vertex is equal to the specified object.
    The method first checks if the object reference is the same as this vertex using the `==` operator. 
    If they are the same, it returns `true`.
    Next, it performs type checking to ensure that the object is of the same class as this vertex. 
    If not, it returns `false`.
    After type checking, it casts the object to a `Vertex<?>` (vertex with any type) to access its data.
    Finally, it compares the data of this vertex with the data of the other vertex using the `equals()` method of the data type. 
    If the data is equal, it returns `true`. Otherwise, it returns `false`.
    Note that the type parameter `T` is represented as `<?>` since the data type can be any type depending on the graph implementation.

    @param o The object to compare against.
    @return {@code true} if the object is equal to this vertex, {@code false} otherwise.
  */

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vertex<?> obj = (Vertex<?>) o;
    return this.data.equals(obj.getData());
  }

  /**
    Computes the hash code value for this vertex.

    Desc: This method overrides the default implementation of the `hashCode()` method from the `Object` class. 
    It computes the hash code value for this vertex.
    The method computes the hash code based on the `data` field of the vertex using the `Objects.hash()` method. 
    The `Objects.hash()` method generates a hash code by combining the hash codes of the provided objects.
    By using the `data` field as the argument for computing the hash code, the implementation ensures that vertices with equal data will have the same hash code, which is important for consistent hash-based data structures and algorithms.
    The computed hash code is returned as the result.

    @return The hash code value for this vertex.
  */

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  /**
    Returns a string representation of this vertex.

    Desc: This method returns a string representation of the vertex. 
    It formats the vertex data as a string using the `toString()` method and constructs a formatted string using `String.format()`.
    The resulting string includes the vertex data enclosed in square brackets, preceded by the label "data:". 
    For example, if the vertex data is "abc", the string representation will be "[data: abc]".
    This method is useful for displaying the vertex in a human-readable format, especially when debugging or printing the contents of the graph.

    @return A string representation of this vertex.
  */

  public String toString(){
    return String.format("[data: %s]",this.data.toString());
  }
}
