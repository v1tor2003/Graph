package graph;
import java.util.Objects;

class Vertex<T>{
  private T data; 
  private Vertex<T> predecessor;

  Vertex(T data){
    this.data = data;
    this.predecessor = null;
  }

  public T getData(){
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vertex<?> obj = (Vertex<?>) o;
    return this.data.equals(obj.getData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

   // TODO
  // getNeigborHood()
  public String toString(){
    return String.format("[data: %s]",this.data.toString());
  }
}
