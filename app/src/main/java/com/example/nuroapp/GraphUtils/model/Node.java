package com.example.nuroapp.GraphUtils.model;

import android.graphics.Color;

public class Node {

    public static Node createNode(int id) {
        return new Node(id);
    }

    public static Node createNodeWithColor(int id, int color) {
        return new Node(id, color);
    }

    public static Node createNodeWithLabel(int id, int color, String label) {
        return new Node(id, color, label);
    }

    public static Node createNode(int id, int posX, int posY) {
        return new Builder(id).setPosx(posX).setPoxy(posY).build();
    }

    public static Node createNodeWithColor(int id, int posX, int posY, int color) {
        return new Builder(id).setColor(color).setPosx(posX).setPoxy(posY).build();
    }

    public static Node createNodeWithLable(int id, int posX, int posY, int color, String label) {
        return new Builder(id).setColor(color).setLable(label).setPosx(posX).setPoxy(posY).build();
    }

    public static final int DEFAULT_COLOR = Color.BLACK;

    private int color;
    private int id;
    private String label;
    private int degree = 0;

    private boolean hasFocus = false;

    private float relativePositionX;
    private float relativePositionY;

    public Node(int id) {
        this(id, DEFAULT_COLOR);
    }

    public Node(int id, int color) {
        this(id, color, null);
    }

    public Node(int id, int color, String label) {
        this.id = id;
        this.color = color;
        this.label = label;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRelativePositionX(float relativePositionX) {
        this.relativePositionX = relativePositionX;
    }

    public void setRelativePositionY(float relativePositionY) {
        this.relativePositionY = relativePositionY;
    }

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public float getRelativePositionX() {
        return relativePositionX;
    }

    public float getRelativePositionY() {
        return relativePositionY;
    }

    public String getLabel() {
        return label;
    }

    public void setFocus() {
        hasFocus = true;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public void removeFocus() {
        this.hasFocus = false;
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

        private int color = DEFAULT_COLOR;
        private int id;
        private String label;
        private int posX;
        private int posY;

        public Builder(int id) {
            this.id = id;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setLable(String label) {
            this.label = label;
            return this;
        }

        public Builder setPosx(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPoxy(int posY) {
            this.posY = posY;
            return this;
        }

        public Node build() {
            Node node = new Node(id, color, label);
            node.setRelativePositionX(posX);
            node.setRelativePositionY(posY);
            return node;
        }
    }
}
