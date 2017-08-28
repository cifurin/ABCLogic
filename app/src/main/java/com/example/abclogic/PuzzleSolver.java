package com.example.abclogic;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Chris on 31/05/2016.
 */
public class PuzzleSolver {

    private static final String TAG = "PuzzleSolver";
    private static Observer mObserver;
    private static CellStates[][] Solution = new CellStates[6][6];
    private static CellStates[][] Cells = new CellStates[6][6];
    private static String[][] rowClues = new String[3][6];
    private static String[][] colClues = new String[3][6];
    private static int gameID = 0;
    private GameView gameView;

    public PuzzleSolver(GameView v) {
        this.gameView = v;
        newGame();
    }

    // this is to set the observer
    public void setObserver(Observer observer) {
        mObserver = observer;
    }

    // here be the magic
    private static void callGameActivity() {
        if (mObserver != null) {
            mObserver.callback();
        }
    }

    public void newGameNetwork() {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

            Log.d(TAG, "New Game Called but getting data from Network Server ");

            JSONParser parser = new JSONParser();
            Log.d(TAG, "json response is " + parser.getJSONFromUrl().toString());

            try {
                JSONArray data = new JSONArray(parser.getJSONFromUrl().getJSONObject(0).getString("data"));

                String clue = data.getJSONObject(3).getString("Clue");
                String solution = data.getJSONObject(3).getString("Solution");

                Log.d(TAG, clue);
                Log.d(TAG, solution);

                for (int n = 1; n < 6; n++) {
                    rowClues[1][n] = clue.substring(n - 1, n);
                    rowClues[2][n] = clue.substring(n - 1 + 5, n + 5);
                    colClues[1][n] = clue.substring(n - 1 + 10, n + 10);
                    colClues[2][n] = clue.substring(n - 1 + 15, n + 15);
                }

                for (int row = 1; row < 6; row++) {
                    for (int col = 1; col < 6; col++) {
                        Cells[row][col] = CellStates.EMPTY;
                        Solution[row][col] = CellStates.getEnum(solution.substring((row - 1) * 5 + (col - 1), (row - 1) * 5 + col));
                    }
                }


            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
    }

    public void newGame() {
        Log.d(TAG, "New Game Called ");

        gameID++;
        if (gameID > 3) {
            gameID = 1;
        }  //2 only !!

        for (int row = 1; row < 6; row++) {
            rowClues[1][row] = PuzzleData.Clues[gameID - 1][row - 1];
            rowClues[2][row] = PuzzleData.Clues[gameID - 1][row - 1 + 5];
        }

        for (int col = 1; col < 6; col++) {
            colClues[1][col] = PuzzleData.Clues[gameID - 1][col - 1 + 10];
            colClues[2][col] = PuzzleData.Clues[gameID - 1][col - 1 + 15];
        }

        for (int row = 1; row < 6; row++) {
            for (int col = 1; col < 6; col++) {
                Cells[row][col] = CellStates.EMPTY;
                Solution[row][col] = CellStates.getEnum(PuzzleData.Solutions[gameID - 1][5 * (row - 1) + col - 1]);
            }
        }

        gameView.invalidate();
        callGameActivity();
    }

    public static int getScore() {
        int score = 0;
        for (int row = 1; row < 6; row++) {
            for (int col = 1; col < 6; col++) {
                if (Cells[row][col] == Solution[row][col]) {
                    score++;
                }
            }
        }
        return score;
    }

    public static void setCell(int row, int col) {
        Cells[row][col] = Cells[row][col].next();
        Log.d(TAG, "ENUM  " + Cells[row][col]);

        callGameActivity();
    }

    public static String getChoice(int row, int col) {
        return (Cells[row][col]).toString();
    }

    public static void setChoice(String s) {
        //choice = s;
    }

    public static String getRowClue(int start, int row) {
        return rowClues[start][row];
    }

    public static String getColClue(int start, int col) {
        return colClues[start][col];
    }
}
