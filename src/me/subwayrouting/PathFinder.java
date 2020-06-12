package me.subwayrouting;
import java.util.*;

import me.subwayrouting.model.graph.Graph;
import me.subwayrouting.model.graph.Node;
import me.subwayrouting.model.route.Route;
import me.subwayrouting.model.route.Station;

public class PathFinder {
    private Graph<NodeInfo> graph;
    private Map<Station, Node<NodeInfo>> stationNodeMap;

    private Set<Node<NodeInfo>> checked; // checked nodes
    private PriorityQueue<Node<NodeInfo>> unvisited; // nodes to visit

    public PathFinder(Set<Route> routes) {
        graph = new Graph<>();
        stationNodeMap = new HashMap<>();

        /*
         * Making graph from routes
         * Assuming each route is one-way
         */
        for (Route route : routes) {
            Node<NodeInfo> prevNode = null;
            for (Station station : route.getStations().values()) {
                Node<NodeInfo> node = stationNodeMap.computeIfAbsent(station,
                        k -> new Node<>(new NodeInfo(station)));

                if (prevNode != null) graph.addConnection(prevNode, node, 1);
                prevNode = node;
            }
        }
    }

    /**
     * Find shortest path from source to destination station.
     *
     * @param from source station
     * @param to destination station
     * @return list of path
     */
    public List<Station> findShortestPath(Station from, Station to) {
        // clean up
        graph.getNodeSet().forEach(n -> n.getObject().clean());
        unvisited = new PriorityQueue<>();
        checked = new HashSet<>();

        // get target nodes
        Node<NodeInfo> nodeFrom = stationNodeMap.get(from);
        Node<NodeInfo> nodeTo = stationNodeMap.get(to);

        Objects.requireNonNull(nodeFrom, "No `from` station found on the graph.");
        Objects.requireNonNull(nodeTo, "No `to` station found on the graph.");

        // calculate shortest path
        nodeFrom.getObject().setWeight(0);
        checked.add(nodeFrom);
        unvisited.add(nodeFrom);

        while (unvisited.size() > 0) {
            Node<NodeInfo> nextNode = unvisited.poll();
            nextNode.getObject().setVisited(true);
            checkNeighbours(nextNode);
        }

        // map to station list
        if (nodeTo.getObject().getSource() == null) return Collections.emptyList();

        List<Station> stations = new ArrayList<>();
        Node<NodeInfo> current = nodeTo;
        while (!current.equals(nodeFrom)) {
            stations.add(current.getObject().getStation());
            current = current.getObject().getSource();
        }
        stations.add(current.getObject().getStation());
        Collections.reverse(stations);

        return stations;
    }

    private void checkNeighbours(Node<NodeInfo> node) {
        int currentWeight = node.getObject().getWeight();

        for (Node<NodeInfo> adjacentNode : graph.getAdjacentNodes(node)) {
            NodeInfo info = adjacentNode.getObject();
            if (info.isVisited()) continue;

            int distance = graph.getDistance(node, adjacentNode);
            int adjacentWeight = info.getWeight();
            if (adjacentWeight == -1 || adjacentWeight > distance + currentWeight) {
                info.setWeight(distance + currentWeight);
                info.setSource(node);
                if (checked.add(adjacentNode))
                    unvisited.add(adjacentNode);
            }
        }
    }

    private static class NodeInfo implements Comparable<NodeInfo> {
        private boolean visited;
        private int weight;
        private Node<NodeInfo> source;

        private Station station;

        public NodeInfo(Station station) {
            this.visited = false;
            this.weight = -1;
            this.source = null;

            this.station = station;
        }

        // getters

        public boolean isVisited() {
            return visited;
        }

        public int getWeight() {
            return weight;
        }

        public Node<NodeInfo> getSource() {
            return source;
        }

        public Station getStation() {
            return station;
        }

        // setters

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setSource(Node<NodeInfo> source) {
            this.source = source;
        }

        // func

        public void clean() {
            this.weight = -1;
            this.source = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NodeInfo)) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return station.equals(nodeInfo.station);
        }

        @Override
        public int hashCode() {
            return Objects.hash(station);
        }

        @Override
        public int compareTo(NodeInfo o) {
            if (weight == o.weight) return 0;
            else if (o.weight == -1) return -1;
            else return weight > o.weight ? 1 : -1;
        }

        @Override
        public String toString() {
            return "NodeInfo{" +
                    "visited=" + visited +
                    ", weight=" + weight +
                    ", station=" + station +
                    '}';
        }
    }



}
