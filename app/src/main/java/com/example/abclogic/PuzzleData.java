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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 03/06/2016.
 */
public class PuzzleData {

    private static final String TAG = "PuzzleData";
    public static Integer puzzleNo = 0;
    final public static List<JSONObject> puzzle = new ArrayList<JSONObject>();

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



    public  static void add(JSONObject data){
        //
        puzzle.add(data);
    }

    public static JSONObject getPuzzle(){

        return puzzle.get(puzzleNo++);

    }

    public static void loadPuzzleData(Context mContext){

        String myUrl = "http://192.168.0.9:5000/get-puzzles";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, myUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the array elements
                            Log.d(TAG,response.toString());

                            JSONArray test = new JSONArray(response.getJSONObject(0).getString("data"));

                            Log.d(TAG,test.toString());

                            for (int i = 0; i < test.length(); i++) {
                                // Get current json object
                                JSONObject data = test.getJSONObject(i);
                                String firstName = data.getString("PuzzleID");
                                //String lastName = data.getString("Clue");
                                //String age = data.getString("Solution");
                                Log.d(TAG,firstName);
                                Log.d(TAG,data.getString("Clue"));
                                Log.d(TAG,data.getString("Solution"));

                                PuzzleData.add(data);
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
}
