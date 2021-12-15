package com.example.nuroapp;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
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

    public void runButton(View view){
        Intent intent;
        if(drawingSurface.validateSequence()){
        intent= new Intent(DrawingActivity.this, SuccessActivity.class);
        startActivity(intent);
    }else{
            intent= new Intent(DrawingActivity.this, FailedActivity.class);
            startActivity(intent);
        };
    }

    private void saveImage(){
 //save image as json
    }

    public void resetButton(View view) {
        drawingSurface.reset();
    }

    public void returnButton(View view) {
     //save before leaving
        saveImage();
        Intent intent= new Intent(DrawingActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
