package com.example.secondvoice.main.tab.impl;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.secondvoice.R;
import com.example.secondvoice.main.tab.Tab;
import com.example.secondvoice.settings.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class FrequentUsed extends Tab {

    public FrequentUsed(){
        super("FrequentUsed");
    }

    private TextToSpeech tts;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_frequent_used, container, false);

        final RequestQueue requests = Volley.newRequestQueue(getContext());
        button = view.findViewById(R.id.update);
        final List<String> freq_list = null;
//        JSONObject responseObject;
        if (getContext() != null) {
            tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int ttsLang = tts.setLanguage(SettingsActivity.LOCALE);

                        if (SettingsActivity.MALE) {
                            Voice voice = null;
                            for (Voice tmpVoice : tts.getVoices()) {
                                if (tmpVoice.getName().contains("#male") && tmpVoice.getName().contains("en-us")) {
                                    voice = tmpVoice;
                                    break;
                                } else {
                                    voice = null;
                                }
                            }

                            if (voice != null) {
                                tts.setVoice(voice);
                            }
                        }


                        if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "The Language is not supported!");
                        } else {
                            Log.i("TTS", "Language Supported.");
                        }
                        Log.i("TTS", "Initialization success.");
                    } else {
                        Toast.makeText(getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getName();
                String update_url = "http://coms-319-103.cs.iastate.edu:8080/findcommon/"+username;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, update_url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray temp = null;
                                try {
                                    temp = response.getJSONArray("Data");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                int length = temp.length();
                                String [] phrases = new String [length];
                                for (int i = 0; i < length; i++) {
                                    try {
                                            phrases[i] = temp.getString(i);
                                    } catch (JSONException e) {
                                            e.printStackTrace();
                                    }
                                }
                                //Toast.makeText(getContext(),phrases[2],Toast.LENGTH_SHORT).show();
                                //make buttons out of the retrieved phrases
                                LinearLayout layout = view.findViewById(R.id.linearLayout5);
                                for (final String string : phrases) {
                                    Button button = new Button(getContext());
                                    button.setText(string);
                                    layout.addView(button);
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                        @Override
                                        public void onClick(View v) {
                                            tts.speak(string, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);

                                            //update backend
                                            String url = "http://coms-319-103.cs.iastate.edu:8080/usephrase/"+getName();
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("phrase", string);
                                            JSONObject parameter = new JSONObject(params);
                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                String status = response.getString("Status");
                                                            } catch (JSONException e) {
                                                                Toast.makeText(getContext(), "common phrase error", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                }
                                            });
                                            requests.add(request);
                                        }
                                    });
                                }


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });
                requests.add(jsonObjectRequest);
            }
        });


        return view;

    }

    @Override
    public String[] getDefaultButtons() {
        return new String[0];
    }
}
