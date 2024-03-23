package proyectoalgoritmiaii.Graph;
import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.components.Node;
import proyectoalgoritmiaii.Graph.types.Directed;
import proyectoalgoritmiaii.Graph.types.Undirected;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public class MaxSubGraph<T extends Comparable<T>> {

  private final Set<Node<T>> visited;

  private Graph<T> g;
  public MaxSubGraph(Graph<T> graph) {
    this.g = graph;
    this.visited = new HashSet<>();
    this.g = getMaxGraph();
  }

  public Graph<T> getG() {
    return this.g;
  }

  private Graph<T> getMaxGraph() {
    PriorityQueue<Graph<T>> graphs = new PriorityQueue<>();
    for (Node<T> node : g.getAllNodes()) {
      Graph<T> graph = bfs(node,
              this.g instanceof Directed ? new Directed<>() :new Undirected<>());
      graphs.add(graph);
    }
    return graphs.peek();
  }

  private Graph<T> bfs(Node<T> origin, Graph<T> bfsGraph) {
    Queue<Node<T>> queue = new LinkedList<>();
    queue.add(origin);
    visited.add(origin);

    while (!queue.isEmpty()) {
      Node<T> currentNode = queue.poll();
      for (Edge<T> neighbor : g.getEdgesNode(currentNode)) {
        if (!visited.contains(neighbor.getDestination())) {
          bfsGraph.addEdge(neighbor.getWeight(), currentNode, neighbor.getDestination());
          queue.add(neighbor.getDestination());
          visited.add(neighbor.getDestination());
        }
      }
    }

    return bfsGraph;
  }
}
  