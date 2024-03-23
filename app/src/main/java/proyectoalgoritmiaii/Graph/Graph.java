package proyectoalgoritmiaii.Graph;

import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.components.Node;

import java.util.*;

public abstract class Graph<T extends Comparable<T>> implements Comparable<Graph<T>> {

    public final Map<Node<T>, Set<Edge<T>>> adj;

    public Integer nEdges;
  
    public Integer nNodes;
  
    
    public Graph() {
      adj = new TreeMap<>();
      nEdges = 0;
      nNodes = 0;
    }
  
    public Map<Node<T>, Set<Edge<T>>> getAdj() {
      return this.adj;
    }
  
    public Integer getnNodes() {
      return this.nNodes;
    }
  
    public Integer getnEdges() {
      return this.nEdges;
    }
  
    private void increNumNodes() {
      this.nNodes++;
    }
  
    protected void decreNumNodes() {
      this.nNodes--;
    }
  
    protected void increNumEdges() {
      this.nEdges++;
    }
  
    protected void decreNumEdges() {
      this.nEdges--;
    }
  
    protected void decreNumEdges(int num) {
      this.nEdges -= num;
    }
  
    public Set<Edge<T>> getAllEdges() {
      Set<Edge<T>> allEdges = new TreeSet<>();
      for (Node<T> node : getAllNodes()) {
        Set<Edge<T>> edges = getEdgesNode(node);
        allEdges.addAll(edges);
      }
      return allEdges;
    }
  
    public Set<Node<T>> getAllNodes() {
      return adj.keySet();
    }
  
    public boolean hasNode(Node<T> node) {
      return adj.containsKey(node);
    }
  
    public Set<Edge<T>> getEdgesNode(Node<T> node) {
      return adj.get(node);
    }
  
    public Node<T> getNode(T no) {
      Node<T> nodeFind = new Node<>(no);
      return this.getAllNodes().stream().filter(n -> n.equals(nodeFind)).findFirst().orElse(null);
    }
  
    public boolean addNode(Node<T> newNo) {
      if (newNo != null && !hasNode(newNo)) {
        Set<Edge<T>> newAdj = new TreeSet<>();
        adj.put(newNo, newAdj);
        increNumNodes();
        return true;
      }
      return false;
    }
  
    public boolean addNode(T newNo) {
      Node<T> node = new Node<>(newNo);
      if (this.hasNode(node)) {
        return false;
      }
      Set<Edge<T>> newAdj = new TreeSet<>();
      adj.put(node, newAdj);
      increNumNodes();
      return true;
    }
  
    public abstract boolean removeNode(Node<T> n);
  
    public boolean updateNode(Node<T> node, T newData) {
      for (Node<T> n : getAllNodes()) {
        if (n.equals(node)) {
          n.setData(newData);
          return true;
        }
      }
      return false;
    }
  
    public Edge<T> getEdge(Integer weight, Node<T> origin, Node<T> destination) {
      if (!this.hasNode(origin)) {
        return null;
      }
      
      Edge<T> edgeFind = new Edge<>(weight, origin, destination);
      Set<Edge<T>> originEdges = this.getEdgesNode(origin);
      
      for (Edge<T> edge : originEdges) {
        if (edge.equals(edgeFind)) {
          return edge;
        }
      }
      
      return null;
    }
    
    public abstract boolean addEdge(Integer weight, Node<T> origin, Node<T> destination);
  
    public abstract boolean removeEdge(Integer weight, Node<T> origin, Node<T> destination);
  
    public abstract boolean updateEdge(Integer weight, Node<T> origin, Node<T> destination,
                                       int newWeight);
  
    protected boolean connectNodes(Integer weight, Node<T> origin, Node<T> destination) {
      Edge<T> newEdge = new Edge<>(weight, origin, destination);
      Set<Edge<T>> originEdges = this.getEdgesNode(origin);
      
      for (Edge<T> edge : originEdges) {
        if (edge.equals(newEdge)) {
          return true;
        }
      }
      
      originEdges.add(newEdge);
      return false;
    }
  
    public int getWeightSum() {
      int sum = 0;
      for (Edge<T> edge : getAllEdges()) {
        sum += edge.getWeight();
      }
      return sum;
    }
  
    private int bfs(Node<T> node, Set<Node<T>> visited, int connections) {
      Queue<Node<T>> queue = new LinkedList<>();
      queue.add(node);
      visited.add(node);

      while (!queue.isEmpty()) {
        Node<T> current = queue.poll();
        for (Edge<T> neighbor : getEdgesNode(current)) {
          if (!visited.contains(neighbor.getDestination())) {
            visited.add(neighbor.getDestination());
            queue.add(neighbor.getDestination());
            connections++;
          }
        }
      }

      return connections;
    }
  
    public int getNumberConnectedNodes() {
      Set<Node<T>> visited = new HashSet<>();
      int maxConnections = 0;

      for (Node<T> node : getAllNodes()) {
        Set<Node<T>> currentVisited = new HashSet<>();
        int connections = bfs(node, currentVisited, 0);
        
        if (connections > maxConnections) {
          maxConnections = connections;
          visited = currentVisited;
        }
      }

      return visited.size();
    }
  
    @Override
    public int hashCode() {
      return this.adj.hashCode();
    }
  
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Graph<?> graph = (Graph<?>) o;
      return getAllEdges().equals(graph.getAllEdges());
    }
  
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (Node<T> node : getAllNodes()) {
        sb.append(node).append(" -> {");
        Set<Edge<T>> edges = getEdgesNode(node);
        if (!edges.isEmpty()) {
          for (Edge<T> edge : edges) {
            sb.append(" [ ").append(edge).append(" ],");
          }
          sb.deleteCharAt(sb.length() - 1);
        } else {
          sb.append(" Empty");
        }
        sb.append(" }\n");
      }
      return sb.toString();
    }
    
    @Override
    public int compareTo(Graph<T> g) {
      int selfSize = this.getNumberConnectedNodes();
      int graphSize = g.getNumberConnectedNodes();
      int selfWeight = this.getWeightSum();
      int graphWeight = g.getWeightSum();
      if (selfSize == graphSize) {
        return Integer.compare(graphWeight,
                               selfWeight);
      }
      return Integer.compare(graphSize,
                             selfSize);
    }
  }
  