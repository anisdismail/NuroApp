package com.example.nuroapp.GraphUtils.model;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private Edge[][] graph;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void setGraph(Edge[][] graph) {
        this.graph = graph;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Edge[][] getGraph() {
        return graph;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {

        List<Integer> indices=new ArrayList<>();
            if(!edges.isEmpty()){
           for (int i=0;i<edges.size();i++) {
               if (edges.get(i).getStartNodeId() == node.getId() || edges.get(i).getEndNodeId() == node.getId()) {
                   indices.add(i);
               }

           }
                Collections.sort(indices, Collections.reverseOrder());
                for(Integer i:indices){

                    edges.remove((int)i);
                }
            }


        nodes.remove(node);

    }

    public void addEdge(Edge edge) {
        updateDegree(edge,true);
        edges.add(edge);
    }

    private void updateDegree(Edge edge, boolean insert) {
        if (insert){
            findNode(edge.getStartNodeId()).increaseDegree();
            findNode(edge.getEndNodeId()).increaseDegree();
        } else {
            findNode(edge.getStartNodeId()).decreaseDegree();
            findNode(edge.getStartNodeId()).decreaseDegree();
        }
    }

    public void removeEdge(Edge edge) {
        updateDegree(edge,false);
        edges.remove(edge);
    }

    public Edge addEdge(Node start, Node end) {
        return addEdge(start.getId(), end.getId());
    }

    public Edge addEdge(int startId, int endId) {
        Edge edge = new Edge(startId, endId);
        edges.add(edge);
        updateDegree(edge,true);
        return edge;
    }
  public ArrayList<Node> getNeigbors(Node node){
   ArrayList<Node> neighbors=new ArrayList<>();
      for(Edge edge:edges){
          if(edge.getStartNodeId()==node.getId()){
              neighbors.add(findNode(edge.getEndNodeId()));
          }
      }
      return neighbors;
  };

    /**
     * creates the graph and sets every parameters.
     * in order to properly create graph all previous data will be removed.
     *
     * @param graph is a NxN graph that i is i'th node and j is j'th node and graph[i][j] is weight of
     *              edge between i and j. use zero when there is no edge between i and j.
     */
    public void setGraph(int[][] graph) {
        int size = graph.length;
        for (int[] ints : graph) {
            if (size != ints.length) throw new IllegalArgumentException("graph must be NxN");
        }

        nodes.clear();
        edges.clear();

        this.graph = new Edge[size][size];

        //create nodes
        for (int i = 0; i < graph.length; i++) {
            nodes.add(Node.createNode(i));
        }

        //create edges
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] > 0) {
                    Edge edge = Edge.createEdge(i, j);
                    edges.add(edge);
                    this.graph[i][j] = edge;
                }
            }
        }

    }

    public Node findNode(int nodeId) {
        for (Node node : nodes) {
            if (node.getId() == nodeId) return node;
        }
        return null;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public static class Builder {
        Node[] nodes;
        ArrayList<Edge> edges;
        private boolean isDirected;
        public Builder(int nodeCount) {
            nodes = new Node[nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                nodes[i] = Node.createNode(i /* as id */);
            }
            edges = new ArrayList<>();
        }

        public Builder setEdge(int start, int end) {
            setEdge(start, end, Edge.DEFAULT_WEIGHT);
            return this;
        }

        public Builder setEdge(int start, int end, int weight) {
            edges.add(Edge.createEdgeWithWeight(start, end, weight));
            return this;
        }

        public Builder setDirected(boolean isDirected) {

            this.isDirected = isDirected;
            return this;
        }

        public Builder setNodeLabel(String label, int nodeId) {
            nodes[nodeId].setLabel(label);
            return this;
        }


        public Graph build() {
            Graph graph = new Graph();
            graph.nodes.addAll(Arrays.asList(nodes));
            graph.edges.addAll(edges);
            if (isDirected) {
                for (Edge edge : graph.getEdges()) {
                    edge.setDirected(true);
                }
            }
            return graph;
        }

    }
    public void reset(){
        nodes.clear();
        edges.clear();

    }
}
