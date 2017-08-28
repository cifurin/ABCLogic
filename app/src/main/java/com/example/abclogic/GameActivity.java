package com.example.abclogic;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

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


        //JSONParser parser = new JSONParser();
        //parser.getJSONFromUrl();
        //Log.d(TAG,"jarray " + parser.getJSONFromUrl());

        //Some url endpoint that you may have
        String myUrl = "http://192.168.0.9:5000/get-puzzles";
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        onViewCreated(gameView, savedInstanceState);


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
        puzzleSolver.newGame();
        gameView.invalidate();
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
