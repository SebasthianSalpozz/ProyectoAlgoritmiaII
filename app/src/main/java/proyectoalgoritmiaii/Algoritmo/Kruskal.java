package proyectoalgoritmiaii.Algoritmo;
import proyectoalgoritmiaii.Graph.types.Directed;
import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.Graph;
import proyectoalgoritmiaii.Graph.components.Node;
import proyectoalgoritmiaii.Graph.types.Undirected;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
public class Kruskal<T extends Comparable<T>>{
  public Graph <T> g;
  public Map<Node<T>, Node<T>> subsets;
  public Integer cost;

  public Kruskal(Graph<T> g){
    this.subsets = new TreeMap<>();
    this.cost = 0;
    this.g = kruskal(g);
  }
  
  public Graph<T> getG(){
    return this.g;
  }

  public Integer getCost() {
    return cost;
  }

  public Map<Node<T>, Node<T>> fillSubset(Graph<T> g){
    Map<Node<T>, Node<T>> subsets = new TreeMap<>();
    for(Node<T> node : g.getAllNodes()){
      subsets.put(node, node);
    }
    return subsets;
  }

  public Graph<T> clean(Graph<T> g) {
    Set<Edge<T>> visited = new TreeSet<>();
    Graph<T> cleanGraph = new Directed<>();

    for (Edge<T> edge : g.getAllEdges()) {
      if (!visited.contains(edge)) {
        Node<T> origin = edge.getOrigin();
        Node<T> destination = edge.getDestination();
        Integer weight = edge.getWeight();
        Edge<T> imageEdge = new Edge<>(weight, destination, origin);

        visited.add(edge);
        visited.add(imageEdge);
        cleanGraph.addEdge(weight, origin, destination);
      }
    }

    return cleanGraph;
  }

  public Graph<T> convertToUndirectGraph(Graph<T> graphToConvert) {
    Graph<T> undiGraph = new Undirected<>();
    for (Edge<T> edge : graphToConvert.getAllEdges()) {
      Node<T> origin = edge.getOrigin();
      Node<T> destination = edge.getDestination();
      Integer weight = edge.getWeight();
      
      undiGraph.addEdge(weight, origin, destination);
      undiGraph.addEdge(weight, destination, origin);
    }
    return undiGraph;
  }

  public Node<T> find(Node<T> n) {
    if (subsets.get(n) == n) {
      return n;
    }
    return find(subsets.get(n));
  }

  public void union(Node<T> origin, Node<T> destination) {
    Node<T> rootSource = find(origin);
    Node<T> rootDestination = find(destination);
    subsets.replace(rootSource,
                    rootDestination);
  }

  public Graph<T> kruskal(Graph<T> graphToConvert) {
    Graph<T> mst = graphToConvert instanceof Directed ? new Directed<>():new Undirected<>();
    Graph<T> cleanGraph = clean(convertToUndirectGraph(graphToConvert));
    subsets = fillSubset(cleanGraph);

    for (Edge<T> edge : cleanGraph.getAllEdges()) {
      if (find(edge.getOrigin()) != find(edge.getDestination())) {
        mst.addEdge(edge.getWeight(), edge.getOrigin(), edge.getDestination());
        union(edge.getOrigin(), edge.getDestination());
        cost += edge.getWeight();
      }
    }

    return mst;
  }

  @Override
  public String toString() {
    return this.g.toString() + "Maximum Cost: " + this.getCost();
  }

  
}