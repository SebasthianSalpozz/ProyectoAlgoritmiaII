package proyectoalgoritmiaii.Graph.components;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    public final Node<T> origin;
    public final Node<T> destination;
    public Integer weight;
    public Edge(Integer weight, Node<T> origin, Node<T> destination) {
        this.weight = weight;
        this.origin = origin;
        this.destination = destination;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public Node<T> getDestination() {
        return destination;
    }
    public Node<T> getOrigin() {
        return origin;
    }
    @Override
    public int hashCode() {
        return this.origin.hashCode() + this.destination.hashCode() + this.weight.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge<?> other = (Edge<?>) o;
        return this.origin.equals(other.getOrigin()) &&
                this.destination.equals(other.getDestination()) &&
                this.weight.equals(other.getWeight());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.origin.toString());
        sb.append(" -> ");
        sb.append(this.destination.toString());
        sb.append(" | weight: ");
        sb.append(this.weight);
        return sb.toString();
    }
    @Override
    public int compareTo(Edge<T> o) {
        int weightComparison = o.getWeight().compareTo(this.weight);
        if (weightComparison != 0) {
            return weightComparison;
        }
        int sourceComparison = o.getOrigin().compareTo(this.origin);
        if (sourceComparison != 0) {
            return sourceComparison;
        }
        return o.getDestination().compareTo(this.destination);

    }
}
