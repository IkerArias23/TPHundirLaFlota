package com.uax.hundir.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ship_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String name;
    protected int size;

    @Convert(converter = BooleanArrayConverter.class)
    protected boolean[] hits;

    @ManyToOne
    @JoinColumn(name = "player_id")
    protected Player player;

    public Ship() {
        // Constructor por defecto para JPA
    }

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.hits = new boolean[size];
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    // Registra un impacto en la posición indicada (0-indexado)
    public void hit(int position) {
        if (position >= 0 && position < size) {
            hits[position] = true;
        }
    }

    // Devuelve true si todas las posiciones han sido impactadas
    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }

    public abstract String getType();
}
