package com.example.secondvoice.main.tab.impl;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.secondvoice.R;
import com.example.secondvoice.main.tab.Tab;
import com.example.secondvoice.settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class FoodAndRestaurant extends Tab {

    private TextToSpeech tts;
    private Button button;

    public FoodAndRestaurant() {
        super("Food & Restaurant");
    }

    @Nullable @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab3_fragment, container,false);

        if (getContext() != null) {
            if (tts == null) {
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
        }

        button = view.findViewById(R.id.speak3);
        final EditText text = view.findViewById(R.id.enter3);
        final RequestQueue requests = Volley.newRequestQueue(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);

                //update backend
                String url = "http://coms-319-103.cs.iastate.edu:8080/usephrase/"+getName();
                Map<String, String> params = new HashMap<String, String>();
                params.put("phrase", text.getText().toString());
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

        Button button2 = view.findViewById(R.id.clear3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayout3);

        if (getDefaultButtons().length >= 1 && getDefaultButtons()[0] != null) {
            for (final String string : getDefaultButtons()) {
                Button button = new Button(getContext());
                button.setText(string);
                layout.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    @Override
    public String[] getDefaultButtons() {
        return new String[] {
                "Can I get a",
                "I'd like to order",
                "Water",
                "Sprite",
                "Coke",
                "Burger",
                "Meal",
                "Fries",
                "Number",
                "Soup",
                "Chicken sandwich",
                "Chicken nuggets",
                "With",
                "Without",
                "ketchup",
                "mustard",
                "ranch",
                "barbeque",
        };
    }
}
