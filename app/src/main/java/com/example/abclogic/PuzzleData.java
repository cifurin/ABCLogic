package com.example.abclogic;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 03/06/2016.
 */
public class PuzzleData {

    private static final String TAG = "PuzzleData";

    public static Integer puzzleNo = 0;

    //final public static List<JSONObject> puzzle = new ArrayList<JSONObject>();

    final public static List<JSONObject> puzzle = new ArrayList<JSONObject>() {
        {
            try {

                JSONObject temp = new JSONObject();
                temp.put("Clue", " C  AAABA B  CAAAC  ");
                temp.put("Solution", "BCA**C*B*AA**CB*BCA**A*BC");
                add(temp);
                JSONObject temp1 = new JSONObject();
                temp1.put("Clue", "C  A  B CB C BBCABCC");
                temp1.put("Solution", "*CAB***CABB**CAAB**CCAB**");
                add(temp1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

//{"PuzzleID":1,"Clue":" AB  C AC   CAC CBBA","Solution":"*B*AC*AC*BBCA**A*BC*C**BA"}

    final public static String[][] Clues = {
            {"", "C", "", "", "A", "A", "A", "B", "A", "", "B", "", "", "C", "A", "A", "A", "C", "", ""},
            {"C", "", "", "A", "", "", "B", "", "C", "B", "", "C", "", "B", "B", "C", "A", "B", "C", "C"},
            {"A", "", "", "", "", "C", "", "C", "A", "C", "C", "", "", "", "", "A", "B", "B", "", "A"}
    };

    final public static String[][] Solutions = {
            {"B", "C", "A", "*", "*", "C", "*", "B", "*", "A", "A", "*", "*", "C", "B", "*", "B", "C", "A", "*", "*", "A", "*", "B", "C"},
            {"*", "C", "A", "B", "*", "*", "*", "C", "A", "B", "B", "*", "*", "C", "A", "A", "B", "*", "*", "C", "C", "A", "B", "*", "*"},
            {"*", "*", "A", "B", "C", "C", "*", "*", "A", "B", "B", "A", "C", "*", "*", "*", "C", "B", "*", "A", "A", "B", "*", "C", "*"}

    };

    public static void add(JSONObject data) {
        //
        puzzle.add(data);
    }

    public static JSONObject getPuzzle() {

        //puzzleNo++;
        if (puzzleNo == puzzle.size()) {
            puzzleNo = 0;
        }
        Log.d(TAG, puzzleNo.toString());
        return puzzle.get(puzzleNo++);
    }

    public static void loadPuzzleData(Context mContext) {

        String myUrl = "http://192.168.0.9:5000/get-puzzles";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, myUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray data = new JSONArray(response.getJSONObject(0).getString("data"));
                            Log.d(TAG, data.toString());

                            for (int i = 0; i < data.length(); i++) {
                                PuzzleData.add(data.getJSONObject(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        // Add JsonArrayRequest to the RequestQueue
        Volley.newRequestQueue(mContext).add(jsonArrayRequest);
    }

    public static void loadJSONFromAsset(Context mContext) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("puzzleData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            Log.d(TAG, json);

            try {
                JSONArray data = new JSONArray(json);
                Log.d(TAG, data.toString());

                JSONArray puzzles = new JSONArray(data.getJSONObject(0).getString("data"));

                for (int i = 0; i < puzzles.length(); i++) {
                    PuzzleData.add(puzzles.getJSONObject(i));
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
