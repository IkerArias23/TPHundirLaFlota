package com.uax.hundir.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ship> ships;

    @Transient
    private Random random;

    public Player() {
        // Constructor por defecto para JPA
        ships = new ArrayList<>();
        random = new Random();
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        if (ships.size() < 3) {
            ship.setPlayer(this);
            ships.add(ship);
        } else {
            System.out.println("Máximo 3 barcos permitidos.");
        }
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Simula un ataque: selecciona aleatoriamente uno de los barcos del oponente que no esté hundido y ataca una posición aleatoria
    public void attack(Player opponent) {
        List<Ship> targetShips = new ArrayList<>();
        for (Ship ship : opponent.getShips()) {
            if (!ship.isSunk()) {
                targetShips.add(ship);
            }
        }
        if (targetShips.isEmpty()) return;
        Ship target = targetShips.get(random.nextInt(targetShips.size()));
        int position = random.nextInt(target.getSize());
        target.hit(position);
        System.out.println(name + " ataca a " + opponent.getName() + " en el barco " + target.getName() + " (posición " + position + ")");
        if (target.isSunk()) {
            System.out.println(target.getName() + " (" + target.getType() + ") ha sido hundido!");
        }
    }
}
