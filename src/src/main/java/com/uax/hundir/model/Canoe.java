package com.uax.hundir.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Canoe")
public class Canoe extends Ship {

    public Canoe() {
        super();
    }

    public Canoe(String name) {
        super(name, 1);
    }

    @Override
    public String getType() {
        return "Canoe";
    }
}
