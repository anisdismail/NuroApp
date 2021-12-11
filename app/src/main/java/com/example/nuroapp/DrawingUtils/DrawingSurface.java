package com.example.nuroapp.DrawingUtils;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.nuroapp.DrawingActivity;
import com.example.nuroapp.GraphUtils.model.Edge;
import com.example.nuroapp.GraphUtils.model.Graph;
import com.example.nuroapp.GraphUtils.model.Node;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Gorkem on 4/17/2018.
 */

public class DrawingSurface extends View implements View.OnDragListener {

    private Context context;

    private static ArrayList<Shape> shapes;
    private ScaleGestureDetector scaleGestureDetector;

    private static float scaleFactor;
    private boolean isZooming;

    private Shape selectedShape;
    private int lastX, lastY;
    private int posX, posY;
    private int cX, cY;

    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;

    private Graph graph;
    private static final int MAX_CLICK_DURATION = 150;
    private static final int MIN_COORDINATE = -3000;
    private static final int MAX_COORDINATE = 3000;

    private long startClickTime;

    private static final String preprocessing = Shape.SHAPETYPE.PREPROCESSING.toString();
    private static final String dataloading = Shape.SHAPETYPE.DATALOADING.toString();
    private static final String datavisualization = Shape.SHAPETYPE.DATAVISUALIZATION.toString();
    private static final String buildmodel = Shape.SHAPETYPE.BUILDMODEL.toString();
    private static final String trainmodel = Shape.SHAPETYPE.TRAINMODEL.toString();
    private static final String evaluatemodel = Shape.SHAPETYPE.EVALUATEMODEL.toString();
    private static final String launchmodel = Shape.SHAPETYPE.LAUNCHMODEL.toString();

   // private static  final String[] sequence={Shape.SHAPETYPE.DATALOADING.toString(),Shape.SHAPETYPE.PREPROCESSING.toString(),
     //       Shape.SHAPETYPE.DATAVISUALIZATION.toString(),Shape.SHAPETYPE.BUILDMODEL.toString(),
       //     Shape.SHAPETYPE.TRAINMODEL.toString(), Shape.SHAPETYPE.EVALUATEMODEL.toString(),Shape.SHAPETYPE.LAUNCHMODEL.toString()  };
   private static  final String[] sequence={Shape.SHAPETYPE.DATALOADING.toString(),Shape.SHAPETYPE.PREPROCESSING.toString()};
    private int nextId;
    private boolean hideLines = false;

    public DrawingSurface(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        shapes = new ArrayList<Shape>();
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16f);
        scaleFactor = 0.25f;
        posX = 0;
        posY = 0;
        setOnDragListener(this);
        nextId = 0;
        graph = new Graph();

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.save();
        posX = Math.min(MAX_COORDINATE, Math.max(MIN_COORDINATE, posX));
        posY = Math.min(MAX_COORDINATE, Math.max(MIN_COORDINATE, posY));
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor);
        if (!hideLines) {
            paint.setColor(Color.GRAY);
            for (int a = -10; a < 10; a++) {
                for (int b = -10; b < 10; b++) {
                    canvas.drawLine(1000 * a, -10000, 1000 * a, 10000, paint);
                    canvas.drawLine(-10000, 1000 * b, 10000, 1000 * b, paint);
                }
            }
            paint.setColor(Color.BLACK);
        }
        for (Shape shape : shapes) {
            shape.drawThis();
        }
        canvas.restore();
    }

    public void reset() {
        shapes.clear();
        graph.reset();
        for(Shape shape:shapes){
            deleteShape(shape);
        }
        invalidate();
    }

    public void addShape(Shape.SHAPETYPE shapetype, int x, int y) {
        float usefulScale = 1.625F;
        float[] coords = new float[2];

        coords[0] = x;
        coords[1] = y;
        Matrix matrix = new Matrix();
        matrix.set(getMatrix());
        matrix.preTranslate(posX, posY);
        matrix.preScale(scaleFactor, scaleFactor);
        matrix.invert(matrix);
        matrix.mapPoints(coords);

        int l = Math.round(this.getHeight());
        int w = Math.round(this.getWidth());


        shapes.add(new Shape(context, this, x, y, l, w, shapetype, nextId));
        Node node=Node.createNodeWithLabel(nextId,shapetype.toString());
        graph.addNode(node);
        nextId++;
        invalidate();
    }

    public void select(Shape newShape) {
        if (selectedShape != null) {
            selectedShape.setSelect(false);
            selectedShape = null;
        }
        selectedShape = newShape;
        selectedShape.setSelect(true);
    }

    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.setQuickScaleEnabled(true);
        scaleGestureDetector.onTouchEvent(event);
        final int action = event.getAction();

        float[] coords = new float[2];
        coords[0] = event.getX();
        coords[1] = event.getY();

        Matrix matrix = new Matrix();
        matrix.set(getMatrix());

        matrix.preTranslate(posX, posY);
        matrix.preScale(scaleFactor, scaleFactor);
        matrix.invert(matrix);
        matrix.mapPoints(coords);

        final int x = Math.round(event.getX());
        final int y = Math.round(event.getY());

        cX = Math.round(coords[0]);
        cY = Math.round(coords[1]);

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                isZooming = false;
                lastX = x;
                lastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (scaleGestureDetector.isInProgress()) {
                    isZooming = true;
                } else if (!isZooming) {
                    final int dX = (x - lastX);
                    final int dY = (y - lastY);
                    if (selectedShape != null) {
                        selectedShape.translate(Math.round(dX / scaleFactor), Math.round(dY / scaleFactor));
                    } else {
                        posX += dX;
                        posY += dY;
                    }
                }
                lastX = x;
                lastY = y;
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION && !scaleGestureDetector.isInProgress()) {
                    boolean touchedAShape = false;
                    boolean addedTextOrLine = false;
                    Shape lastShape;

                    for (Shape shape : shapes) {
                        if (shape.contains(new Point(cX, cY))) {
                            if (selectedShape != null) {
                                addedTextOrLine = true;
                                if (selectedShape == shape) {
                                    //  getShapeTextInput(shape);
                                } else {

                                    //delete the edge to the old shape if found
                                    Node selectedShapeNode=graph.findNode(selectedShape.getId());
                                    for (int i=0;i<graph.getEdges().size();i++) {
                                        if (graph.getEdges().get(i).getStartNodeId() == selectedShapeNode.getId()) {
                                            graph.removeEdge(graph.getEdges().get(i));
                                        }

                                    }
                                    //delete the edge to the previous of shape
                                    Node shapeNode=graph.findNode(shape.getId());
                                    for (int i=0;i<graph.getEdges().size();i++) {
                                        if (graph.getEdges().get(i).getEndNodeId() == shapeNode.getId()) {
                                            graph.removeEdge(graph.getEdges().get(i));
                                        }
                                    }
                                    //shape delete any old edge between the two shapes
                                    for (int i=0;i<graph.getEdges().size();i++) {
                                        if (graph.getEdges().get(i).getStartNodeId() == shapeNode.getId() &&
                                                graph.getEdges().get(i).getEndNodeId() == selectedShapeNode.getId() ) {
                                            graph.removeEdge(graph.getEdges().get(i));
                                        }
                                    }
                                    selectedShape.setLine(shape);
                                    Edge edge= Edge.createEdge(selectedShape.getId(),shape.getId());
                                    graph.addEdge(edge);



                                }
                            }
                            select(shape);
                            touchedAShape = true;
                        }
                    }

                    if ((!touchedAShape) || addedTextOrLine) {
                        if (selectedShape != null) selectedShape.setSelect(false);
                        selectedShape = null;
                    }
                }
                invalidate();
            }
        }

        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        if (dragEvent.getAction() == DragEvent.ACTION_DROP) {

            float[] coords = new float[2];
            coords[0] = dragEvent.getX();
            coords[1] = dragEvent.getY();

            Matrix matrix = new Matrix();
            matrix.set(getMatrix());

            matrix.preTranslate(posX, posY);
            matrix.preScale(scaleFactor, scaleFactor);
            matrix.invert(matrix);
            matrix.mapPoints(coords);

            final float x = dragEvent.getX();
            final float y = dragEvent.getY();

            cX = Math.round(coords[0]);
            cY = Math.round(coords[1]);

            ClipData.Item item = dragEvent.getClipData().getItemAt(0);
            String dragData = item.getText().toString();
            if (dragData.equals(preprocessing)) {
                addShape(Shape.SHAPETYPE.PREPROCESSING, cX, cY);
            } else if (dragData.equals(datavisualization)) {
                addShape(Shape.SHAPETYPE.DATAVISUALIZATION, cX, cY);
            } else if (dragData.equals(dataloading)) {
                addShape(Shape.SHAPETYPE.DATALOADING, cX, cY);
            } else if (dragData.equals(trainmodel)) {
                addShape(Shape.SHAPETYPE.TRAINMODEL, cX, cY);
            } else if (dragData.equals(evaluatemodel)) {
                addShape(Shape.SHAPETYPE.EVALUATEMODEL, cX, cY);
            } else if (dragData.equals(buildmodel)) {
                addShape(Shape.SHAPETYPE.BUILDMODEL, cX, cY);
            } else if (dragData.equals(launchmodel)) {
                addShape(Shape.SHAPETYPE.LAUNCHMODEL, cX, cY);
            }
        }
        return true;
    }

    public void deleteShape() {
        Shape shapeToDelete = selectedShape;
        if (shapeToDelete != null){
            if (shapeToDelete.getPreviousShape() != null){
                shapeToDelete.getPreviousShape().removeLine(shapeToDelete, Line.POSITION.TOP);
            }
            if (shapeToDelete.getOtherPreviousShape() != null){
                shapeToDelete.getOtherPreviousShape().removeLine(shapeToDelete, Line.POSITION.TOP);
            }

            shapes.remove(selectedShape);
            graph.removeNode(graph.findNode(selectedShape.getId()));
            selectedShape=null;
            invalidate();
        }
    }
    public void deleteShape(Shape shape) {
        Shape shapeToDelete = shape;
        if (shapeToDelete != null){
            if (shapeToDelete.getPreviousShape() != null){
                shapeToDelete.getPreviousShape().removeLine(shapeToDelete, Line.POSITION.TOP);
            }
            if (shapeToDelete.getOtherPreviousShape() != null){
                shapeToDelete.getOtherPreviousShape().removeLine(shapeToDelete, Line.POSITION.TOP);
            }

            shapes.remove(shapeToDelete);
            graph.removeNode(graph.findNode(shapeToDelete.getId()));
            selectedShape=null;
            invalidate();
        }
    }

    public boolean validateSequence() {
        int seqCounter=0;
        if(!graph.isEmpty()){
        Node start=graph.getNodes().get(0);
        if(start.getLabel().equals(sequence[seqCounter])){
            seqCounter++;
        }else{
            //failed
            return false;
        }
        ArrayList<Node> neighbors=graph.getNeigbors(start);
       while(!neighbors.isEmpty() && seqCounter<sequence.length){
           Node node=neighbors.get(0);
            System.out.println(neighbors);
            if(node.getLabel().equals(sequence[seqCounter]) && neighbors.size()==1){
                seqCounter++;
            }else{
                //failed
                return false;
            }
            neighbors=graph.getNeigbors(node);

        }

            return seqCounter == sequence.length && graph.getNodes().size() == sequence.length;
        }
return false;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (selectedShape != null) {
                float shapeScaleFactor = detector.getScaleFactor();
                selectedShape.scale(shapeScaleFactor);
            } else {
                scaleFactor *= detector.getScaleFactor();
                scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 1.0f));
            }
            invalidate();
            return true;
        }
    }

    /*    private void getShapeTextInput (final Shape shape){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Enter some text.");
            final EditText shapeTextEditText = new EditText(context);
            shapeTextEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(shapeTextEditText);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String shapeString = shapeTextEditText.getText().toString();
                    shape.setText(new Text(DrawingSurface.this, shape, shapeString));

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    */
    public void prepareToSaveAsImage(boolean hideLines) {
        this.hideLines = hideLines;
        invalidate();
        /*
        int left, right, top, bottom;
        left = right = top = bottom = 0;
        for (Shape shape : shapes){
            if (shape.getShapeOrigin().getX() - shape.getWidth()/2 < left){
                left = shape.getShapeOrigin().getX() - shape.getWidth()/2;
            }
            if (shape.getShapeOrigin().getX() + shape.getWidth()/2 > right){
                right = shape.getShapeOrigin().getX() + shape.getWidth()/2;
            }
            if (shape.getShapeOrigin().getY() - shape.getHeigth()/2 < bottom){
                top = shape.getShapeOrigin().getY() - shape.getHeigth()/2;
            }
            if (shape.getShapeOrigin().getY() + shape.getHeigth()/2 > top){
                bottom = shape.getShapeOrigin().getY() + shape.getHeigth()/2;
            }
        }
        Log.d(DrawingActivity.class.getSimpleName().toString(), "Top: " + top + " Left: " + left + " Bottom: " + bottom + " Right: " + right);
        */
    }

    /*   public String getDiagramData (){
           Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
           Type type = new TypeToken<ArrayList<Shape>>(){}.getType();
           String data = gson.toJson(shapes, type);
           return data;
       }

       public void setDiagramData (String jShapes, String jLines){
           Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
           Type type = new TypeToken<ArrayList<Shape>>(){}.getType();
           shapes = gson.fromJson(jShapes, type);
           invalidate();
       }
   */
    public Shape getShape(int id) {
        for (Shape shape : shapes) {
            if (shape.getId() == id) {
                return shape;
            }
        }
        return null;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public static float getScaleFactor() {
        return scaleFactor;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

}
