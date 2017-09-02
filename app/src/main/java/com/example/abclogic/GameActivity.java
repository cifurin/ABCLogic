package com.example.abclogic;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity {

    private static final String TAG = "GameActivity";
    public static GameView gameView;
    private PuzzleSolver puzzleSolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.gameView);
        puzzleSolver = new PuzzleSolver(gameView);

        someMethod();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

            Log.d(TAG,"starting network call ..");

            JSONParser parser = new JSONParser();
            Log.d(TAG, "json response is " + parser.getJSONFromUrl().toString());


        }
    }

    public void someMethod() {
        puzzleSolver.setObserver(new Observer() {
            public void callback() {
                // here you call something inside your activity, for instance
                Log.d(TAG, "callback called!!  " + PuzzleSolver.getScore());
                TextView txtView = (TextView) findViewById(R.id.textView1);
                txtView.setText("Your Score = " + Integer.toString(PuzzleSolver.getScore()));
            }
        });
    }

    public void newPuzzleOnClick(View v) {
        //
        puzzleSolver.newGameVolley();
    }

    public void selectedAOnClick(View v) {
        //
        Log.d(TAG, "selection " + "A");
        //PuzzleSolver.setChoice("A");
    }

    public void selectedBOnClick(View v) {
        //
        Log.d(TAG, "selection " + "B");
        PuzzleSolver.setChoice("B");
    }

    public void selectedCOnClick(View v) {
        //
        Log.d(TAG, "selection " + "C");
        PuzzleSolver.setChoice("C");
    }

    public void selecteddOnClick(View v) {
        //
        Log.d(TAG, "selection " + "*");
        PuzzleSolver.setChoice("*");
    }
}
