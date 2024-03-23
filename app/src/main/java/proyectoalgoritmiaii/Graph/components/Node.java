package proyectoalgoritmiaii.Graph.components;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
  public T data;

  public Node(T data) {
    this.data = data;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public int hashCode() {
    return this.data.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (this.getClass() != o.getClass()) {
      return false;
    }
    Node<?> obj = (Node<?>) o;
    return this.data.equals(obj.getData());
  }
  @Override
  public String toString() {
    return this.data.toString();
  }
  @Override
  public int compareTo(Node<T> o) {
    return this.data.compareTo(o.getData());
  }
}
  