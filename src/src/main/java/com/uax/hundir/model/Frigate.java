package com.uax.hundir.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Frigate")
public class Frigate extends Ship {

    public Frigate() {
        super();
    }

    public Frigate(String name) {
        super(name, 3);
    }

    @Override
    public String getType() {
        return "Frigate";
    }
}
