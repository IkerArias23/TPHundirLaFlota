package com.uax.hundir.hash;

import com.uax.hundir.model.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipHashManager {

    // Tabla hash por tipo: clave (tipo de barco) -> lista de barcos
    private Map<String, List<Ship>> typeTable;
    // Tabla hash por número: se asigna un número único a cada barco
    private Map<Integer, Ship> numberTable;
    // Tabla hash por nombre: clave (nombre del barco) -> barco
    private Map<String, Ship> nameTable;

    private int shipCounter;

    public ShipHashManager() {
        typeTable = new HashMap<>();
        numberTable = new HashMap<>();
        nameTable = new HashMap<>();
        shipCounter = 1;
    }

    public void addShip(Ship ship) {
        // Añadir a la tabla por tipo
        String type = ship.getType();
        typeTable.putIfAbsent(type, new ArrayList<>());
        typeTable.get(type).add(ship);

        // Añadir a la tabla por número
        numberTable.put(shipCounter, ship);
        shipCounter++;

        // Añadir a la tabla por nombre
        nameTable.put(ship.getName(), ship);
    }

    public List<Ship> getShipsByType(String type) {
        return typeTable.getOrDefault(type, new ArrayList<>());
    }

    public Ship getShipByNumber(int number) {
        return numberTable.get(number);
    }

    public Ship getShipByName(String name) {
        return nameTable.get(name);
    }
}
