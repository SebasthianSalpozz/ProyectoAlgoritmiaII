package proyectoalgoritmiaii.Graph.types;

import proyectoalgoritmiaii.Graph.Graph;
import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.components.Node;

import java.util.Iterator;
import java.util.Set;

public class Directed<T extends Comparable<T>> extends Graph<T> {

  public Directed() {
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
      
      Set<Edge<T>> edges = super.getEdgesNode(origin);
      
      edges.removeIf(e -> e.getDestination().equals(n));
      
      c += edges.size();
    }
    
    c += super.getEdgesNode(n).size();
    
    super.getAdj().remove(n);
    super.decreNumEdges(c);
    super.decreNumNodes();
    
    return true;
  }

  @Override
  public boolean addEdge(Integer weight, Node<T> origin, Node<T> destination) {
    super.addNode(origin);
    super.addNode(destination);
    if (super.connectNodes(weight,origin,destination)) {
      return false;
    }
    super.increNumEdges();
    return true;
  }

  @Override
  public boolean removeEdge(Integer weight, Node<T> origin, Node<T> destination) {
    Edge<T> edgeToRemove = new Edge<>(weight, origin, destination);
    
    for (Node<T> node : super.getAllNodes()) {
      Set<Edge<T>> edges = super.getEdgesNode(node);
      
      for (Iterator<Edge<T>> iterator = edges.iterator(); iterator.hasNext();) {
        Edge<T> edge = iterator.next();
        
        if (edge.equals(edgeToRemove)) {
          iterator.remove();
          super.decreNumEdges();
          return true;
        }
      }
    }
    
    return false;
  }
  
  @Override
  public boolean updateEdge(Integer weight, Node<T> origin, Node<T> destination, int newWeight) {
    Edge<T> edgeToUpdate = new Edge<>(weight, origin, destination);
    
    for (Node<T> node : super.getAllNodes()) {
      Set<Edge<T>> edges = super.getEdgesNode(node);
      
      for (Edge<T> edge : edges) {
        if (edge.equals(edgeToUpdate)) {
          edge.setWeight(newWeight);
          return true;
        }
      }
    }
    return false;
  }
}

