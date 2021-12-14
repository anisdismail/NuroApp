package com.example.nuroapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    private String latestTitle;

    Button ImageDetectionBtn;
    Button ImageClassificationBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.content_frame, new FlowchartsFragment()).commit();
        MediaPlayer clickSound = MediaPlayer.create(this,R.raw.click);
       actionBar = getSupportActionBar();
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open_description, R.string.drawer_close_description) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (latestTitle != null && latestTitle.length() > 0) actionBar.setTitle(latestTitle);
                else actionBar.setTitle(R.string.app_name);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle(R.string.drawer_open_title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                actionBar.setTitle(menuItem.getTitle().toString());
                latestTitle = actionBar.getTitle().toString();
                switch (menuItem.getItemId()) {
                    case R.id.preferences:
                        clickSound.start();
                        //fragmentTransaction.replace(R.id.content_frame, new PreferencesFragment()).commit();
                        break;
                    case R.id.help:
                        clickSound.start();
                        //fragmentTransaction.replace(R.id.content_frame, new HelpFragment()).commit();
                        break;
                    case R.id.about:
                    {

                        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(getString(R.string.app_name));
                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }});
                        builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                                        startActivity(intent);

                                    }});}
                                    //fragmentTransaction.replace(R.id.content_frame, new AboutFragment()).commit();
                        break;
                                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        //if (Build.VERSION.SDK_INT >= 23){
         //   if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
           //     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
           // }
       // }
       // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Flowcharts";
       // File folder = new File(path);
       // if (!folder.exists()) {
         //   folder.mkdirs();
        //}

        ImageDetectionBtn = findViewById(R.id.buttonMLWorkflow);
        ImageDetectionBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, StepsActivity.class);
            startActivity(i);
            clickSound.start();
        });
        ImageClassificationBtn = findViewById(R.id.buttonImgClassification);
        ImageClassificationBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, StepsActivity.class);
            startActivity(i);
            clickSound.start();
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}
