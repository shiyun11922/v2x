package com.neko.seed.traffic.entity;

import java.util.List;

public class EdgeEntity {

    private String id;
    private List<Double[]> shape;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double[]> getShape() {
        return shape;
    }

    public void setShape(List<Double[]> shape) {
        this.shape = shape;
    }
}
