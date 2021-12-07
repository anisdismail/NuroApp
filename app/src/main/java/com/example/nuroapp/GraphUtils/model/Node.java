package com.example.nuroapp.GraphUtils.model;

import android.graphics.Color;

public class Node {

    public static Node createNode(int id) {
        return new Node(id);
    }


    public static Node createNodeWithLabel(int id,String label) {
        return new Node(id, label);
    }
    private int id;
    private String label;
    private int degree = 0;


    public Node(int id) {
        this(id, null);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", degree=" + degree +
                '}';
    }

    public Node(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void decreaseDegree() {
        if (degree>0) degree--;
    }

    public void increaseDegree(){
        degree++;
    }

    public int getDegree() {
        return degree;
    }

    public static class Builder {

        private int id;
        private String label;


        public Builder(int id) {
            this.id = id;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Node build() {
            Node node = new Node(id, label);
            return node;
        }
    }
}
