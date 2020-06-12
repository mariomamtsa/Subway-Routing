package me.subwayrouting.model.graph;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents graph node
 *
 * @param <T> custom object to hold
 */

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>  {
    private T object;

    public Node(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(object, node.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }

    @Override
    public int compareTo(Node<T> o) {
        return this.object.compareTo(o.object);
    }

    @Override
    public String toString() {
        return "Node{" +
                "object=" + object +
                '}';
    }


}
