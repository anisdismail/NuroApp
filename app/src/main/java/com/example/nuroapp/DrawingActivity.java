package com.example.nuroapp;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nuroapp.DrawingUtils.DrawingSurface;
import com.example.nuroapp.DrawingUtils.Line;
import com.example.nuroapp.DrawingUtils.Shape;

import java.io.File;
import java.io.FileOutputStream;

public class DrawingActivity extends AppCompatActivity {
    public final String TAG = MainActivity.class.getSimpleName();
    private DrawingSurface drawingSurface;
    public Point point;

    ImageButton deleteButton;
    ImageButton saveButton;
    ProgressBar progressBar;

    private String flowchartName;
    private String data;

    //DatabaseHelper databaseHelper;
    String pathToFolder, pathToImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        flowchartName = getIntent().getStringExtra("flowchartName");
        data = getIntent().getStringExtra("data");
      //  if (data != null && data.length() > 0){
        //    new LoadDiagramDataTask().execute();
       // }
        ImageView preprocessingShape = findViewById(R.id.preprocessing_shape);
        setDraggable(preprocessingShape);
        ImageView dataloadingShape = findViewById(R.id.dataloading_shape);
        setDraggable(dataloadingShape);
        ImageView datavisualizationShape = findViewById(R.id.datavisualization_shape);
        setDraggable(datavisualizationShape);
        ImageView buildmodelShape = findViewById(R.id.buildmodel_shape);
        setDraggable(buildmodelShape);
        ImageView trainmodelShape = findViewById(R.id.trainmodel_shape);
        setDraggable(trainmodelShape);
        ImageView evaluateShape = findViewById(R.id.evaluatemodel_shape);
        setDraggable(evaluateShape);
        ImageView launchmodelShape = findViewById(R.id.launchmodel_shape);
        setDraggable(launchmodelShape);
        drawingSurface = (DrawingSurface) findViewById(R.id.drawingSurface);

      //  databaseHelper = new DatabaseHelper(getContext());
       // pathToFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name);
       // pathToImage = pathToFolder + "/" + flowchartName + ".png";
    }

    private void setDraggable (final ImageView view){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(String.valueOf(view.getTag()));
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);
                return v.startDragAndDrop(data,
                        myShadow,
                        null,
                        0
                );
            }
        });
    }

    public void setDeleteButton (View view){
        drawingSurface.deleteShape();

    }

    public void setSaveButton (View view){
       /* String data = drawingSurface.getDiagramData();
        if (!(new File(pathToImage).exists())){
            saveImage();
        }
        if (databaseHelper.getDiagram(flowchartName) != null){
            databaseHelper.updateDiagram(flowchartName, data);
        } else {
            databaseHelper.insertDiagram(flowchartName, data, pathToImage);
        }
    */
    }
    public void runButton(View view){
    drawingSurface.validateSequence();
    }
    public void setImageButton (View view){
        saveImage();
    }

    private void saveImage(){
        View content = drawingSurface;
        drawingSurface.prepareToSaveAsImage(true);
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();
        FileOutputStream ostream;
        File folder = new File(pathToFolder);
        if (!folder.exists()){
            folder.mkdirs();
        }
        File file = new File (pathToImage);
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            Toast.makeText(getApplicationContext(), "image saved" + pathToImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
        drawingSurface.prepareToSaveAsImage(false);
    }

    public void resetButton(View view) {
        drawingSurface.reset();
    }

/*    private class LoadDiagramDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int nextId = 0;
            try {
                JSONArray jsonArray = new JSONArray(data);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jShape = jsonArray.getJSONObject(i);
                    int id = jShape.getInt("id");
                    if (id >= nextId){
                        nextId = id + 1;
                    }
                    int width = jShape.getInt("width");
                    int height = jShape.getInt("height");
                    JSONObject jOrigin = jShape.getJSONObject("shapeOrigin");
                    int x = jOrigin.getInt("x");
                    int y = jOrigin.getInt("y");
                    String shapeTypeString = jShape.getString("shapetype");
                    Shape.SHAPETYPE shapetype = Shape.SHAPETYPE.valueOf(shapeTypeString);
                    String text = "";
                    if (jShape.has("text")){
                        JSONObject jText = jShape.getJSONObject("text");
                        text = jText.getString("string");
                    }
                    if (jShape.has("line")){
                        JSONObject jLine = jShape.getJSONObject("line");
                    }
                    switch (shapetype){
                        case WHILE:
                            drawingSurface.getShapes().add(new WhileShape(DrawingActivity.this, drawingSurface, x, y, width, height, shapetype, id));
                            break;
                        case CONDITION:
                            drawingSurface.getShapes().add(new ConditionShape(DrawingActivity.this, drawingSurface, x, y, width, height, shapetype, id));
                            break;
                        default:
                            drawingSurface.getShapes().add(new Shape(DrawingActivity.this, drawingSurface, x, y, width, height, shapetype, id));
                            break;
                    }
                    if (text.length() > 0){
                        drawingSurface.getShape(id).setText(new Text(drawingSurface, drawingSurface.getShape(id), text));
                    }
                    drawingSurface.setNextId(nextId);
                }
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jShape = jsonArray.getJSONObject(i);
                    int id = jShape.getInt("id");
                    String shapeTypeString = jShape.getString("shapetype");
                    Shape.SHAPETYPE shapetype = Shape.SHAPETYPE.valueOf(shapeTypeString);
                    switch (shapetype){
                        case CONDITION:
                            if (jShape.has("trueLine")){
                                JSONObject jTrueLine = jShape.getJSONObject("trueLine");
                                String firstPositionString = jTrueLine.getString("firstPosition");
                                Line.POSITION firstPosition = Line.POSITION.valueOf(firstPositionString);
                                String secondPositionString = jTrueLine.getString("secondPosition");
                                Line.POSITION secondPosition = Line.POSITION.valueOf(secondPositionString);
                                int firstShapeId = jTrueLine.getInt("firstShapeId");
                                int secondShapeId = jTrueLine.getInt("secondShapeId");
                                ConditionShape conditionShape = (ConditionShape) drawingSurface.getShape(id); 
                                conditionShape.setLineFromJSON(firstPosition, secondShapeId, secondPosition);
                            }
                            if (jShape.has("falseLine")){
                                JSONObject jFalseLine = jShape.getJSONObject("falseLine");
                                String firstPositionString = jFalseLine.getString("firstPosition");
                                Line.POSITION firstPosition = Line.POSITION.valueOf(firstPositionString);
                                String secondPositionString = jFalseLine.getString("secondPosition");
                                Line.POSITION secondPosition = Line.POSITION.valueOf(secondPositionString);
                                int firstShapeId = jFalseLine.getInt("firstShapeId");
                                int secondShapeId = jFalseLine.getInt("secondShapeId");
                                ConditionShape conditionShape = (ConditionShape) drawingSurface.getShape(id);
                                conditionShape.setLineFromJSON(firstPosition, secondShapeId, secondPosition);
                            }
                            break;
                        case WHILE:
                            if (jShape.has("line")){
                                JSONObject jLine = jShape.getJSONObject("line");
                                String firstPositionString = jLine.getString("firstPosition");
                                Line.POSITION firstPosition = Line.POSITION.valueOf(firstPositionString);
                                String secondPositionString = jLine.getString("secondPosition");
                                Line.POSITION secondPosition = Line.POSITION.valueOf(secondPositionString);
                                int firstShapeId = jLine.getInt("firstShapeId");
                                int secondShapeId = jLine.getInt("secondShapeId");
                                drawingSurface.getShape(id).setLineFromJSON(firstPosition, secondShapeId, secondPosition);
                            }
                            if (jShape.has("whileLine")){
                                JSONObject jWhileLine = jShape.getJSONObject("whileLine");
                                String firstPositionString = jWhileLine.getString("firstPosition");
                                Line.POSITION firstPosition = Line.POSITION.valueOf(firstPositionString);
                                String secondPositionString = jWhileLine.getString("secondPosition");
                                Line.POSITION secondPosition = Line.POSITION.valueOf(secondPositionString);
                                int firstShapeId = jWhileLine.getInt("firstShapeId");
                                int secondShapeId = jWhileLine.getInt("secondShapeId");
                                WhileShape whileShape = (WhileShape) drawingSurface.getShape(id);
                                whileShape.setLineFromJSON(firstPosition, secondShapeId, secondPosition);
                            }
                            break;
                        default:
                            if (jShape.has("line")){
                                JSONObject jLine = jShape.getJSONObject("line");
                                String firstPositionString = jLine.getString("firstPosition");
                                Line.POSITION firstPosition = Line.POSITION.valueOf(firstPositionString);
                                String secondPositionString = jLine.getString("secondPosition");
                                Line.POSITION secondPosition = Line.POSITION.valueOf(secondPositionString);
                                int firstShapeId = jLine.getInt("firstShapeId");
                                int secondShapeId = jLine.getInt("secondShapeId");
                                drawingSurface.getShape(id).setLineFromJSON(firstPosition, secondShapeId, secondPosition);
                            }
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            drawingSurface.invalidate();
        }
    }*/
}
