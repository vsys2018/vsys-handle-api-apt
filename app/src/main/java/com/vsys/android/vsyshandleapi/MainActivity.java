package com.vsys.android.vsyshandleapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import huynguyen.hlibs.android.helper.Ui;
import huynguyen.hlibs.java.Net;

public class MainActivity extends AppCompatActivity {

    private String lastResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (BuildConfig.DEBUG) Net.setOnLog(s -> Log.w(getPackageName(), s));
        handleData(intent);
    }

    private void handleData(Intent intent) {
        if (intent != null) {
            String extra = intent.getAction();
            assert extra != null;
            if (extra.equals("android.intent.action.MAIN")) {
                finish();
                return;
            }

            String[] spl = extra.split(",");

            if (spl[0].equals("1")) {
                if (!spl[3].equals("")) {
                    String url = spl[3].startsWith("http://") ? spl[3] : "http://" + spl[3];
                    Net.getStringGET(url, s -> {
                        lastResponse = s;
                        String r = handleJSON(s, spl[1], spl[2]);
                        if (r.startsWith("ERROR")) {
                            String rs = Net.getStringGET("http://127.0.0.1:22099/>22&" + r);
                        } else {
                            String rs = Net.getStringGET("http://127.0.0.1:22099/>20&" + r);
                        }
                    }, f -> {
                        int a = f;
                    });
                } else {
                    Net.getStringGET("http://127.0.0.1:22099/>22&ERROR", s -> {
                    });
                }
            } else if (spl[1] == "2") {
                // Get more
            } else {
                Net.getStringGET("http://127.0.0.1:22099/>22&ERROR", s -> {
                });
            }
        }
    }

    private String handleJSON(String input, String position, String key) {
        try {
            if (input.startsWith("{")) {
                JSONObject jo = new JSONObject(input);
                return jo.getString(key);
            } else if (input.startsWith("[")) {
                JSONArray ja = new JSONArray(input);
                JSONObject jo = ja.getJSONObject(Integer.parseInt(position));
                return jo.getString(key);
            } else {
                return "ERROR";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleData(intent);
    }
}
