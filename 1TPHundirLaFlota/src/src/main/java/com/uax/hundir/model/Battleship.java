package com.uax.hundir.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Battleship")
public class Battleship extends Ship {

    @Convert(converter = BooleanArrayConverter.class)
    private boolean[] containerHit;

    public Battleship() {
        super();
    }

    public Battleship(String name) {
        super(name, 5);
        containerHit = new boolean[5];
    }

    @Override
    public void hit(int position) {
        super.hit(position);
        if (position >= 0 && position < containerHit.length) {
            containerHit[position] = true;
        }
    }

    @Override
    public boolean isSunk() {
        for (int i = 0; i < size; i++) {
            if (!containerHit[i]) return false;
        }
        return true;
    }

    @Override
    public String getType() {
        return "Battleship";
    }
}
