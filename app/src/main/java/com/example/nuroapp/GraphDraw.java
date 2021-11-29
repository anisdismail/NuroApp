package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import ir.shahinsoft.graphdraw.GraphView;
import ir.shahinsoft.graphdraw.model.Graph;

public class GraphDraw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Graph g = new Graph.Builder(5)
                .setEdge(1,2) // set edge between first node and second node.
                .setEdge(2,4) // set edge between second node and third node.
                .setNodePosition(0,10,10) // set postion (10,10) to node in position 0 ( actualy with id=0)
                .setNodePosition(1,14,2)
                .setNodePosition(2,50,10)
                .setNodePosition(3,20,80)
                .setNodePosition(4,45,9)
                .setDirected(true)
                .setNodeLabel("node1",0) // set label 'node1' to node with id=0
                .build();
        g.setDragable(true);
        GraphView graphView = (GraphView) findViewById(R.id.graphView);;
        graphView.setGraph(g);
    }

}