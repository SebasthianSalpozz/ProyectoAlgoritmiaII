package proyectoalgoritmiaii.fileHandler;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import proyectoalgoritmiaii.Graph.Graph;
import proyectoalgoritmiaii.Graph.types.Undirected;
import proyectoalgoritmiaii.Graph.components.Node;

public class FileGraphParser {

  public final BufferedReader buffer;

  public final List<String> neighbors;

  public final List<String[]> relations;

  public FileGraphParser(String path) {
    try {
      this.buffer = new BufferedReader(new FileReader(path));
      this.relations = new LinkedList<>();
      this.neighbors = new LinkedList<>();
      this.readNeighbors();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Archivo no encontrado");
    }
  }

  public List<String> getNeighbors() {
    return this.neighbors;
  }

  public List<String[]> getRelations() {
    return this.relations;
  }

  public void readNeighbors() {
    try {
      String line;
      while ((line = this.buffer.readLine()) != null) {
        String[] split = line.split(" ");
        if (split.length == 1) {
          this.neighbors.add(split[0]);
          continue;
        }
        this.relations.add(split);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al leer el archivo", e);
    }
  }

  public Graph<String> parseGraph() {
    Graph<String> graph = new Undirected<>();
    List<String> neighbors = this.getNeighbors();
    List<String[]> relations = this.getRelations();
    for (String neighbor : neighbors) {
      graph.addNode(neighbor);
    }
    for (String[] relation : relations) {
      String from = relation[0];
      String to = relation[1];
      int relationValue = Integer.parseInt(relation[2]);
      graph.addEdge(relationValue, new Node<>(from), new Node<>(to));
    }
    return graph;
  }
}