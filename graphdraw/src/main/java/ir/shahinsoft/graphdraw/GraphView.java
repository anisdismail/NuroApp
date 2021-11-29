package ir.shahinsoft.graphdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static java.lang.Math.abs;

import ir.shahinsoft.graphdraw.model.Edge;
import ir.shahinsoft.graphdraw.model.Graph;
import ir.shahinsoft.graphdraw.model.Node;

public class GraphView extends View {

    float maxRelativeSizeX = 100f;
    float maxRelativeSizeY = 100f;
    float radius = 60f;

    float clickRadius = 50f;

    float stroke = 8f;

    String label = null;

    Graph graph;
    Paint paint;

    OnNodeClickListener onNodeClickListener;

    int selectedNodeColor = Color.CYAN;


    private int CLICK_ACTION_THRESHOLD = 100;
    private float clickX;
    private float clickY;
    OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    clickX = event.getX();
                    clickY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    return handleTouch(event);
            }
            return false;
        }
    };

    public void setOnNodeClickListener(OnNodeClickListener onNodeClickListener) {
        this.onNodeClickListener = onNodeClickListener;
    }

    private boolean handleTouch(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();


        if (abs(touchX - clickX) < CLICK_ACTION_THRESHOLD && abs(touchY - clickY) < CLICK_ACTION_THRESHOLD) {
            onClick(clickX, clickY);
            return true;
        }
        return false;
    }

    private void onClick(float x, float y) {
        for (Node node : graph.getNodes()) {
            if (checkNodeClicked(node, x, y)) {
                performNodeClicked(node);
                return;
            }
        }
    }

    private void performNodeClicked(Node node) {
        if (onNodeClickListener != null) {
            onNodeClickListener.onClick(this, node);
        }

        this.label = node.getLabel();
        graph.setFocusForNode(node.getId());
        invalidate();
    }

    private boolean checkNodeClicked(Node node, float x, float y) {
        float posx = getPosXForNode(node.getRelativePositionX());
        float posy = getPosYForNode(node.getRelativePositionY());
        return abs(posx - x) < clickRadius && abs(posy - y) < clickRadius;
    }

    public GraphView(Context context) {
        super(context);
        initView(null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);

        setOnTouchListener(onTouchListener);

        //todo get styleable variables from attrs
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (graph == null || graph.isEmpty()) return;

        drawEdges(canvas);

        drawNodes(canvas);

        if (label != null) {
            drawLabel(canvas);
        }
    }

    private void drawLabel(Canvas canvas) {
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        float textWidth = paint.measureText(label);

        int center = width / 2;

        float startx = center - textWidth / 2;

        paint.setColor(Color.BLACK);
        paint.setTextSize(32f);
        canvas.drawText(label, startx, 50, paint);
    }

    private void drawEdges(Canvas canvas) {
        for (Edge edge : graph.getEdges()) {
            drawEdge(canvas, edge);
        }
    }

    private void drawEdge(Canvas canvas, Edge edge) {
        paint.setColor(edge.getColor());
        Node startNode = graph.findNode(edge.getStartNodeId());
        Node endNode = graph.findNode(edge.getEndNodeId());
        if (startNode == null || endNode == null) return;

        float startPosX = getPosXForNode(startNode.getRelativePositionX());
        float startPosY = getPosYForNode(startNode.getRelativePositionY());

        float endPosX = getPosXForNode(endNode.getRelativePositionX());
        float endPoxY = getPosYForNode(endNode.getRelativePositionY());

        canvas.drawLine(startPosX, startPosY, endPosX, endPoxY, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        /*sPath path = new Path();
        path.moveTo(0, -10);
        path.lineTo(5, 0);
        path.lineTo(-5, 0);
        path.close();
        path.offset(10, 40);
        canvas.drawPath(path, paint);
        path.offset(50, 100);
        canvas.drawPath(path, paint);
// offset is cumlative
// next draw displaces 50,100 from previous
        path.offset(50, 100);
        canvas.drawPath(path, paint);*/
    }

    private void drawNodes(Canvas canvas) {
        for (Node node : graph.getNodes()) {
            drawNode(canvas, node);
        }
    }

    private void drawNode(Canvas canvas, Node node) {

        float posx = getPosXForNode(node.getRelativePositionX());
        float posy = getPosYForNode(node.getRelativePositionY());
        
        if (node.hasFocus()) {
            paint.setColor(selectedNodeColor);
            canvas.drawCircle(posx, posy, radius, paint);
        } else {
            paint.setColor(node.getColor());
            canvas.drawCircle(posx, posy, radius, paint);
        }

    }

    private float getPosYForNode(float relativePositionY) {
        if (relativePositionY > maxRelativeSizeY)
            return getHeight() - getPaddingTop() - radius + 32;

        float height = getHeight() - getPaddingTop() - getPaddingBottom() - 2 * radius - 32;

        return height * relativePositionY / maxRelativeSizeY + getPaddingTop() + radius + 32/* temp */;
    }

    private float getPosXForNode(float relativePositionX) {
        if (maxRelativeSizeX > maxRelativeSizeX) return getWidth() - getPaddingLeft() - radius;

        float width = getWidth() - getPaddingLeft() - getPaddingRight() - 2 * radius;

        return width * relativePositionX / maxRelativeSizeX - getPaddingLeft() + radius;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        graph.setView(this);
        invalidate();
    }
}
