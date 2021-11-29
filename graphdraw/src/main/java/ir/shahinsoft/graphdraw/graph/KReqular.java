package ir.shahinsoft.graphdraw.graph;

import java.util.ArrayList;

import ir.shahinsoft.graphdraw.model.Edge;
import ir.shahinsoft.graphdraw.model.Graph;
import ir.shahinsoft.graphdraw.model.Node;

public class KReqular extends Graph {

    private final int vertexCount;
    private final int k;
    private final int startRadius = 10;
    private final float vertexGap = 10;

    public KReqular(int vertexCount, int k) {

        this.vertexCount = vertexCount;
        this.k = k;

        if (k % 2 == 1 && vertexCount % 2 == 1) {
            throw new IllegalArgumentException(
                    String.format("No k-regular graph with %d vertexes and %d degree exists.", vertexCount, k)
            );
        }
        if (k >= vertexCount) {
            throw new IllegalArgumentException("Vertex degree cannot be greater than vertex count");
        }

        initGraph();
        initVertexPositions();
    }

    private void initVertexPositions() {
        startPositioning(getNodes(), 0, next(0), 50f, 50f);
    }

    private void startPositioning(ArrayList<Node> vertexes, int startIndex, float raduis, float cx, float cy) {
        int max = maxfor(raduis);
        if (vertexes.size() - startIndex > max) {
            position(vertexes, startIndex, max, raduis, cx, cy);
            startPositioning(vertexes, startIndex + max, next(raduis), cx, cy);
        } else {
            position(vertexes, startIndex, vertexes.size() - startIndex, raduis, cx, cy);
        }
    }

    private void position(ArrayList<Node> vertexes, int startIndex, int size, float raduis, float cx, float cy) {
        double degree = 2 * Math.PI / size;
        double drawDegree = degree;
        for (int i = startIndex; i < startIndex + size; i++) {
            vertexes.get(i).setRelativePositionX(getPositionX(raduis, drawDegree, cx));
            vertexes.get(i).setRelativePositionY(getPositionY(raduis, drawDegree, cy));
            drawDegree += degree;
        }
    }

    private float getPositionX(float raduis, double degree, float cx) {
        return (float) (Math.cos(degree) * raduis + cx);
    }
    private float getPositionY(float raduis, double degree, float cy) {
        return (float) (Math.sin(degree) * raduis + cy);
    }


    private int maxfor(float raduis) {
        return (int) ((2f * Math.PI * raduis) / vertexGap);
    }

    private float next(float radius) {
        if (radius == 0) return startRadius;
        return radius * 2f;
    }

    private void initGraph() {
        createVertexes();

        //creating edges
        ArrayList<Node> nodes = getNodes();
        int targetIndex = 1;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            while (node.getDegree() != k) {
                if (targetIndex == nodes.size()) {
                    targetIndex = i + 1;
                }
                Node node1 = nodes.get(targetIndex);
                addEdge(node, node1);
                targetIndex++;
            }
        }
    }

    private void createVertexes() {
        for (int i = 0; i < vertexCount; i++) {
            addNode(Node.createNode(i));
        }
    }
}
