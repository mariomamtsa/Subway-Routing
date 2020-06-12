package me.subwayrouting.model.graph;
import java.util.*;

/**
 * Represents graph
 *
 * @param <T> custom object to hold in each node
 */

public class Graph<T extends Comparable<T>>{
    private Set<Node<T>> nodeSet;
    private Map<Node<T>, Map<Node<T>, Integer>> nodeMap;

    public Graph(){
        this.nodeSet = new HashSet<>();
        this.nodeMap = new HashMap<>();
    }

    /**
     * Add new nodes connection
     *
     * @param nodeFrom node from
     * @param nodeTo node to
     * @param distance distance
     */
    public void addConnection(Node<T> nodeFrom, Node<T> nodeTo, int distance) {
        Map<Node<T>, Integer> adjacentNodeMap = nodeMap.computeIfAbsent(nodeFrom, k -> new HashMap<>());
        adjacentNodeMap.put(nodeTo, distance);
        nodeSet.add(nodeFrom);
        nodeSet.add(nodeTo);
    }

    /**
     * Return distance between two nodes, or -1 if not connected.
     *
     * @param nodeFrom node from
     * @param nodeTo node to
     * @return distance, -1 if nodes not connected
     */
    public int getDistance(Node<T> nodeFrom, Node<T> nodeTo) {
        Map<Node<T>, Integer> adjacentNodeMap = nodeMap.get(nodeFrom);
        if (adjacentNodeMap == null) return -1;
        else return adjacentNodeMap.getOrDefault(nodeTo, -1);
    }

    /**
     * Return adjacent nodes to given
     *
     * @param node given node
     * @return set of adjacent nodes
     */
    public Set<Node<T>> getAdjacentNodes(Node<T> node) {
        return Collections.unmodifiableSet(nodeMap.getOrDefault(node, new HashMap<>()).keySet());
    }

    /**
     * Return immutable node map
     *
     * @return node map
     */
    public Map<Node<T>, Map<Node<T>, Integer>> getNodeMap() {
        return Collections.unmodifiableMap(nodeMap);
    }

    /**
     * Return immutable set of all nodes in the graph
     *
     * @return node set
     */
    public Set<Node<T>> getNodeSet() {
        return Collections.unmodifiableSet(nodeSet);
    }

}
