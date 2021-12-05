package com.example.nuroapp.GraphUtils.graph;

import static java.lang.Math.min;
import static java.lang.Math.pow;

import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.nuroapp.GraphUtils.model.Edge;
import com.example.nuroapp.GraphUtils.model.Graph;
import com.example.nuroapp.GraphUtils.model.Node;

import java.util.Random;

public class Cube extends Graph {

    public static int MAX_LEVEL = 4;

    /**
     * level is the level of cube and can be at least 1 and at last 5
     */
    private int level;

    private boolean colory = false;

    int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.GRAY};

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Cube(int level) {
        this.level = min(level, MAX_LEVEL);

        initCube();
    }

    public void setColory(boolean hasColor) {
        this.colory = hasColor;
    }

    public boolean isColory() {
        return colory;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    @Override
    public void setView(final View view) {
        super.setView(view);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = view.getWidth();
                int height = view.getHeight();
                if (height < width) {
                    height = width;
                }
                view.getLayoutParams().height = height;
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    private void initCube() {
        //creating graph nodes and edges without positioning
        initGraph(0, (int) pow(2, level), level);

        //positioning the nodes
        initPositions(0, 0, 100, 100, level, 0, getNodes().size());

    }

    private void initPositions(float startX, float startY, float width, float height, int level, int startIndex, int endIndex) {

        if (level == 0) {
            getNodes().get(startIndex).setRelativePositionX(startX + width / 2 + randomDistance());
            getNodes().get(startIndex).setRelativePositionY(startY + height / 2 + randomDistance());
            return;
        }

        if (width >= height) {
            initPositions(startX, startY,//start positions
                    width / 2, height, //end positions
                    level - 1, //level
                    startIndex, (startIndex + endIndex) / 2); // new bounds
            initPositions(startX + width / 2, startY,
                    width / 2, height,
                    level - 1,
                    (startIndex + endIndex) / 2, endIndex);

        } else {
            initPositions(startX, startY,
                    width, height / 2,
                    level - 1,
                    startIndex, (startIndex + endIndex) / 2);
            initPositions(startX, startY + height / 2,
                    width, height / 2,
                    level - 1,
                    (startIndex + endIndex) / 2, endIndex);
        }
    }

    private float randomDistance() {
        return ((float) new Random().nextInt() % 5);
    }

    private void initGraph(int startIndex, int size, int level) {
        if (level > 1) {
            //initializing left part of the graph
            initGraph(startIndex, size / 2, level - 1);
            //initializing right side of the graph
            initGraph(startIndex + size / 2, size / 2, level - 1);
            //connecting left side to right side
            for (int i = startIndex; i < startIndex + (size / 2); i++) {
                int color = Color.BLACK;
                if (isColory()) {
                    color = getColorForLevel(level);
                }
                addEdge(Edge.createEdgeWithColor(i, i + size / 2, color));
            }
        } else {

            //creating a simple level 1 cube.
            Node node1 = new Node(startIndex);
            Node node2 = new Node(startIndex + 1);
            Edge edge = new Edge(startIndex, startIndex + 1);
            edge.setColor(getColorForLevel(level));

            addNode(node1);
            addNode(node2);

            addEdge(edge);
        }
    }

    private int getColorForLevel(int level) {
        return colors[level];
    }
}

