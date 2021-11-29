package ir.shahinsoft.graphdraw.model;

import android.graphics.Color;

public class Edge {

    public static Edge createEdge(int startNodeId,int endNodeId){
        return new Edge(startNodeId,endNodeId);
    }

    public static Edge createEdgeWithWeight(int startNodeId,int endNodeId,int weight){
        return new Edge(startNodeId,endNodeId,weight);
    }

    public static Edge createEdgeWithColor(int startNodeId,int endNodeId, int color){
        return new Edge(startNodeId,endNodeId,DEFAULT_WEIGHT,false,color);
    }

    public static Edge createDirectedEdge(int startNodeId,int endNodeId){
        return new Edge(startNodeId,endNodeId,DEFAULT_WEIGHT,true);
    }

    public static Edge createDirectedEdgeWithColor(int startNodeId,int endNodeId,int color){
        return new Edge(startNodeId,endNodeId,DEFAULT_WEIGHT,true,color);
    }


    public static int DEFAULT_COLOR = Color.BLACK;
    public static int DEFAULT_WEIGHT = 1;

    private int weight;
    private int color;
    private int startNodeId;
    private int endNodeId;
    private boolean isDirected;

    public Edge(int startNodeId,int endNodeId){
        this(startNodeId,endNodeId,1);
    }

    public Edge(int startNodeId,int endNodeId,int weight){
        this(startNodeId,endNodeId,weight,false);
    }

    public Edge(int startNodeId,int endNodeId,int weight,boolean isDirected){
        this(startNodeId,endNodeId,weight,isDirected,DEFAULT_COLOR);
    }

    public Edge(int startNodeId,int endNodeId,int weight,boolean isDirected,int color){
        this.startNodeId = startNodeId;
        this.endNodeId   = endNodeId;
        this.weight      = weight;
        this.isDirected  = isDirected;
        this.color       = color;
    }

    public int getColor() {
        return color;
    }

    public int getEndNodeId() {
        return endNodeId;
    }

    public int getStartNodeId() {
        return startNodeId;
    }

    public int getWeight() {
        return weight;
    }

    public void setColor(int color) {
        this.color = color;
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
        private int color          = DEFAULT_COLOR;
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

        public Builder setColor(int color){
            this.color = color;
            return this;
        }

        public Builder directed(){
            this.isDirected = true;
            return this;
        }

        public Edge build(){
            return new Edge(startNodeId,endNodeId,weight,isDirected,color);
        }


    }
}
