package com.example.nuroapp.GraphUtils.model;

import android.graphics.Color;

public class Edge {

    public static Edge createEdge(int startNodeId, int endNodeId){
        return new Edge(startNodeId,endNodeId);
    }

    public static Edge createEdgeWithWeight(int startNodeId, int endNodeId, int weight){
        return new Edge(startNodeId,endNodeId,weight);
    }

    public static Edge createDirectedEdge(int startNodeId, int endNodeId){
        return new Edge(startNodeId,endNodeId,DEFAULT_WEIGHT,true);
    }

    public static int DEFAULT_WEIGHT = 1;

    private int weight;
    private int startNodeId;
    private int endNodeId;
    private boolean isDirected;

    public Edge(int startNodeId,int endNodeId){
        this(startNodeId,endNodeId,1,false);
    }

    public Edge(int startNodeId,int endNodeId,int weight){
        this(startNodeId,endNodeId,weight,false);
    }

    public Edge(int startNodeId,int endNodeId,int weight,boolean isDirected){
        this.startNodeId = startNodeId;
        this.endNodeId   = endNodeId;
        this.weight      = weight;
        this.isDirected  = isDirected;
    }



    public int getEndNodeId() {
        return endNodeId;
    }

    public int getStartNodeId() {
        return startNodeId;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", startNodeId=" + startNodeId +
                ", endNodeId=" + endNodeId +
                ", isDirected=" + isDirected +
                '}';
    }

    public int getWeight() {
        return weight;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public void setEndNodeId(int endNodeId) {
        this.endNodeId = endNodeId;
    }

    public void setStartNodeId(int startNodeId) {
        this.startNodeId = startNodeId;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public class Builder {

        private int weight         = DEFAULT_WEIGHT;
        private int startNodeId    ;
        private int endNodeId      ;
        private boolean isDirected = false;

        public Builder(int startNodeId,int endNodeId){
            this.startNodeId = startNodeId;
            this.endNodeId   = endNodeId;
        }

        public Builder setWeight(int weight){
            this.weight = weight;
            return this;
        }

        public Builder directed(){
            this.isDirected = true;
            return this;
        }

        public Edge build(){
            return new Edge(startNodeId,endNodeId,weight,isDirected);
        }


    }
}
