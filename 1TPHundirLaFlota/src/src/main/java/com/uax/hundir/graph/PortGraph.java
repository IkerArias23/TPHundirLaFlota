package com.uax.hundir.graph;

import java.util.*;

public class PortGraph {
    private Map<String, Port> ports;

    public PortGraph() {
        ports = new HashMap<>();
    }

    public void addPort(String name) {
        ports.putIfAbsent(name, new Port(name));
    }

    public void addEdge(String port1, String port2, int distance) {
        if (!ports.containsKey(port1)) addPort(port1);
        if (!ports.containsKey(port2)) addPort(port2);
        ports.get(port1).addAdjacent(port2, distance);
        ports.get(port2).addAdjacent(port1, distance);
    }

    // Recorrido en profundidad desde el puerto dado
    public void depthFirstTraversal(String start) {
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String portName, Set<String> visited) {
        if (!ports.containsKey(portName) || visited.contains(portName)) {
            return;
        }
        System.out.print(portName + " ");
        visited.add(portName);
        for (String adj : ports.get(portName).getAdjacent().keySet()) {
            dfsHelper(adj, visited);
        }
    }

    // Camino más corto utilizando el algoritmo de Dijkstra
    public List<String> shortestPath(String start, String end) {
        if (!ports.containsKey(start) || !ports.containsKey(end)) return Collections.emptyList();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String port : ports.keySet()) {
            distances.put(port, Integer.MAX_VALUE);
            previous.put(port, null);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) break;
            for (Map.Entry<String, Integer> neighbor : ports.get(current).getAdjacent().entrySet()) {
                int alt = distances.get(current) + neighbor.getValue();
                if (alt < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), alt);
                    previous.put(neighbor.getKey(), current);
                    queue.add(neighbor.getKey());
                }
            }
        }
        // Reconstruir el camino
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        if (path.get(0).equals(start)) {
            return path;
        }
        return Collections.emptyList();
    }

    // Determina el puerto con el mayor número de aristas (conexiones)
    public String portWithMostEdges() {
        String result = null;
        int maxDegree = -1;
        for (Port port : ports.values()) {
            int degree = port.degree();
            if (degree > maxDegree) {
                maxDegree = degree;
                result = port.getName();
            }
        }
        return result;
    }

    // Elimina un puerto del grafo
    public void removePort(String name) {
        if (!ports.containsKey(name)) return;
        ports.remove(name);
        for (Port port : ports.values()) {
            port.getAdjacent().remove(name);
        }
    }

    // Devuelve el conjunto de puertos para fines demostrativos
    public Set<String> getPorts() {
        return ports.keySet();
    }
}