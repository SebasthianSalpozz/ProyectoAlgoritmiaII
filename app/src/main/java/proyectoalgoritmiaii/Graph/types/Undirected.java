package proyectoalgoritmiaii.Graph.types;

import proyectoalgoritmiaii.Graph.Graph;
import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.components.Node;

import java.util.Set;

public class Undirected<T extends Comparable<T>> extends Graph<T> {
  public Undirected() {
    super();
  }

  @Override
  public boolean removeNode(Node<T> n) {
    if (!super.hasNode(n)) {
      return false;
    }
    
    int c = 0;
    
    for (Node<T> origin : super.getAllNodes()) {
      if (origin.equals(n)) {
        continue;
      }
      
      Set<Edge<T>> edges = this.getEdgesNode(origin);
      c += edges.size();
      
      edges.removeIf(edge -> edge.getDestination().equals(n));
      super.getAdj().get(origin).removeIf(edge -> edge.getDestination().equals(n));
    }
    
    super.getAdj().remove(n);
    super.decreNumEdges(c);
    super.decreNumNodes();
    
    return true;
  }

  @Override
  public boolean addEdge(Integer weight, Node<T> origin, Node<T> destination) {
    addNode(origin);
    addNode(destination);
    
    boolean added = connectNodes(weight, origin, destination) || connectNodes(weight, destination, origin);
    
    if (!added) {
      super.increNumEdges();
    }
    
    return !added;
  }

  @Override
  public boolean removeEdge(Integer weight, Node<T> origin, Node<T> destination) {
    Edge<T> edge1 = new Edge<>(weight, origin, destination);
    Edge<T> edge2 = new Edge<>(weight, destination, origin);
    boolean removed = false;
    
    for (Node<T> node : super.getAllNodes()) {
      Set<Edge<T>> edges = super.getEdgesNode(node);
      
      if (edges.removeIf(e -> e.equals(edge1))) {
        removed = true;
      }
      
      if (edges.removeIf(e -> e.equals(edge2))) {
        removed = true;
      }
      
      if (removed) {
        super.decreNumEdges();
        return true;
      }
    }
    
    return false;
  }

  @Override
  public boolean updateEdge(Integer weight, Node<T> origin, Node<T> destination, int newWeight) {
    Edge<T> edge1 = new Edge<>(weight, origin, destination);
    Edge<T> edge2 = new Edge<>(weight, destination, origin);
    
    for (Node<T> current : super.getAllNodes()) {
      Set<Edge<T>> edges = super.getEdgesNode(current);
      
      if (!edges.isEmpty()) {
        for (Edge<T> edge : edges) {
          if (edge.equals(edge1)) {
            edge.setWeight(newWeight);
            return true;
          }
          
          if (edge.equals(edge2)) {
            edge.setWeight(newWeight);
            return true;
          }
        }
      }
    }
    
    return false;
  }
}
