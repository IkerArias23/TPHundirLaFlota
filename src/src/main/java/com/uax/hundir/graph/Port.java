package com.uax.hundir.graph;

import java.util.HashMap;
import java.util.Map;

public class Port {
    private String name;
    // Lista de adyacencia: puerto conectado -> distancia
    private Map<String, Integer> adjacent;

    public Port(String name) {
        this.name = name;
        adjacent = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(String portName, int distance) {
        adjacent.put(portName, distance);
    }

    public int degree() {
        return adjacent.size();
    }
}
