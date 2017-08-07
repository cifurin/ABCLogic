package com.example.abclogic;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
        PuzzleSolver.setChoice("A");
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
