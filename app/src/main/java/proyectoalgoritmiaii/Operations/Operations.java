package proyectoalgoritmiaii.Operations;

import proyectoalgoritmiaii.Graph.components.Edge;
import proyectoalgoritmiaii.Graph.components.Node;
import proyectoalgoritmiaii.Graph.Graph;
import proyectoalgoritmiaii.Algoritmo.Kruskal;
import proyectoalgoritmiaii.Graph.MaxSubGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import proyectoalgoritmiaii.fileHandler.FileGraphParser;

public class Operations {
  public Set<Node<String>> invitados;
  public Map <Node<String>, List <Node<String>>> groups;
  public List <Node<String>> strongestRelation;
  public List <Node<String>> leastRelation;
  public Graph<String> graph;

  public Operations(String path, int x, int k){
    this.graph = new Kruskal<>(new FileGraphParser(path).parseGraph()).getG();
    this.strongestRelation = new LinkedList<>();
    this.leastRelation = new LinkedList<>();
    this.invitados = this.getGreaterThanAlgorithm(x);
    this.groups = this.findGroups(k);
  }

  public void removeEdgesLessThan(int x) {
    List<Edge<String>> edgesToRemove = new ArrayList<>();

    for (Edge<String> edge : this.graph.getAllEdges()) {
      if (edge.getWeight() <= x) {
        edgesToRemove.add(edge);
      }
    }

    for (Edge<String> edge : edgesToRemove) {
      this.graph.removeEdge(edge.getWeight(), edge.getOrigin(), edge.getDestination());
    }
  }

  public void removeEmptyNodes() {
    this.graph.getAllNodes().removeIf(node -> this.graph.getEdgesNode(node).isEmpty());
    this.graph = new MaxSubGraph<>(this.graph).getG();
  }

  public Set<Node<String>> getGreaterThanAlgorithm(int x) {
    removeEdgesLessThan(x);
    removeEmptyNodes();
    return this.graph.getAllNodes();
  }

  public String getInvitados() {
    String result = "\n";
    for (Node<String> node : invitados) {
      result += node + " ";
    }
    result = result.trim();
    return result;
  }

  public Map<Node<String>, List<Node<String>>> groupsByNode() {
    Map<Node<String>, List<Node<String>>> groups = new HashMap<>();

    for (Node<String> node : this.graph.getAllNodes()) {
      List<Node<String>> group = new LinkedList<>();
      group.add(node);
      groups.put(node, group);
    }

    return groups;
  }

  public Map<Node<String>, Node<String>> getNodes() {
    Map<Node<String>, Node<String>> nodes = new HashMap<>();

    for (Node<String> node : this.graph.getAllNodes()) {
      nodes.put(node, node);
    }

    return nodes;
  }

  public Map<Node<String>, List<Node<String>>> findGroups(int k) {
    Map<Node<String>, List<Node<String>>> groups = groupsByNode();
    if (groups.size() == k) {
      return groups;
    }

    Map<Node<String>, Node<String>> nodes = getNodes();
    Set<Edge<String>> visitedEdges = new HashSet<>();
    boolean firstRelation = true;

    while (groups.size() > k) {
      Edge<String> selectedEdge = null;
      Node<String> selectedSource = null;
      Node<String> selectedDestination = null;

      for (Edge<String> edge : this.graph.getAllEdges()) {
        Node<String> source = edge.getOrigin();
        Node<String> destination = edge.getDestination();

        if (visitedEdges.contains(edge) || nodes.get(source) == nodes.get(destination)) {
          continue;
        }

        if (selectedEdge == null || edge.getWeight() > selectedEdge.getWeight()) {
          selectedEdge = edge;
          selectedSource = source;
          selectedDestination = destination;
        }
      }

      if (selectedEdge == null) {
        break;
      }

      visitedEdges.add(selectedEdge);
      visitedEdges.add(new Edge<>(selectedEdge.getWeight(), selectedDestination, selectedSource));

      Node<String> prevDestination = nodes.get(selectedDestination);
      List<Node<String>> prevGroup = groups.get(prevDestination);

      for (Node<String> node : prevGroup) {
        nodes.put(node, nodes.get(selectedSource));
        groups.get(nodes.get(selectedSource)).add(node);
      }

      groups.remove(prevDestination);

      if (firstRelation) {
        this.strongestRelation = groups.get(nodes.get(selectedSource));
        firstRelation = false;
      }

      if (groups.size() == k) {
        this.leastRelation = groups.get(nodes.get(selectedSource));
        break;
      }
    }

    if (groups.size() != k) {
      return null;
    }

    return groups;
  }

  public String getGroups() {
    StringBuilder result = new StringBuilder();
    result.append("Groups:\n");
    
    if (this.groups == null) {
      return result.append("It is not possible").toString();
    }
    
    for (Map.Entry<Node<String>, List<Node<String>>> entry : this.groups.entrySet()) {
      List<Node<String>> nodes = entry.getValue();
      
      for (Node<String> node : nodes) {
        result.append(node).append(" ");
      }
      
      result.delete(result.length() - 1, result.length());
      result.append("\n");
    }
    
    result.delete(result.length() - 1, result.length());
    return result.toString();
  }

  public String getStrongestRelation() {
    StringBuilder result = new StringBuilder();
    result.append("Group with strongest friendly relationship: ");

    if (this.strongestRelation.isEmpty()) {
      result.append("None");
    } else {
      for (Node<String> node : this.strongestRelation) {
        result.append(node).append(" ");
      }
      result.delete(result.length() - 1, result.length());
    }

    return result.toString();
  }

  public String getLeastRelation() {
    StringBuilder result = new StringBuilder();
    result.append("Group with least friendly relationship: ");

    if (this.leastRelation.isEmpty()) {
      result.append("None");
    } else {
      for (Node<String> node : this.leastRelation) {
        result.append(node).append(" ");
      }
      result.delete(result.length() - 1, result.length());
    }

    return result.toString();
  }

  
  @Override
  public String toString() {
    if (this.groups == null) {
      return getInvitados() + "\n" + getGroups()+ "\n";
    } else {
      return getInvitados() + "\n" + getGroups() + "\n" + getStrongestRelation() + "\n" + getLeastRelation()+ "\n";
    }
  }

}