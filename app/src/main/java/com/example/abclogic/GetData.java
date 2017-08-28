package com.example.abclogic;

/**
 * Created by Chris on 27/08/2017.
 */

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.IOException;

public class GetData {

    public static JSONObject getData() {

        String result = "";
        JSONObject jsonObject = null;
        String URL;

        URL = "127.0.0.1:5000/get-puzzles";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {

            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
            if (httpEntity != null){
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                inputStream.close();
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
