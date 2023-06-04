class Edge<T> {
  private Integer weight;
  private Vertex<T> start;
  private Vertex<T> end;

  Edge(Vertex<T> start, Vertex<T> end, Integer weight) {
    this.weight = weight;
    this.start = start;
    this.end = end;
  }

  Vertex<T> getStart() {
    return this.start;
  }

  Vertex<T> getEnd() {
    return this.end;
  }

  void setWeight(int weight) {
    this.weight = weight;
  }

  int getWeight() {
    return this.weight;
  }

  boolean existsFor(Vertex<T> v, Vertex<T> u, Integer weight) {
    return isAdjacentFor(v, u) && this.weight == weight;
  }

  boolean disconected() {
    return this.start == null || this.end == null;
  }

  boolean isAdjacentFor(Vertex<T> v, Vertex<T> u) {
    return this.start == v && end == u;
  }

  public String endString() {
    return String.format("(w): %d", this.getWeight());
  }
}
