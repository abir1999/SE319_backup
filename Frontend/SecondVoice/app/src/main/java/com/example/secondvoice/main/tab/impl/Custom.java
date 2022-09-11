package com.example.secondvoice.main.tab.impl;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Custom extends Tab {

    private String[] CUSTOM_BUTTONS = {
            "- Custom Button 1 -",
            "- Custom Button 2 -",
            "- Custom Button 3 -",
            "- Custom Button 4 -",
            "- Custom Button 5 -",
            "- Custom Button 6 -",
            "- Custom Button 7 -",
            "- Custom Button 8 -",
            "- Custom Button 9 -",
            "- Custom Button 10 -"
    };

    private TextToSpeech tts;
    private Button button;

    public Custom() {
        super("Custom");
    }

    @Nullable @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab4_fragment, container,false);

        updateCustom();
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
        button = view.findViewById(R.id.speak4);
        final EditText text = view.findViewById(R.id.enter4);
        final RequestQueue requests = Volley.newRequestQueue(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                tts.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
            }
        });

        Button button2 = view.findViewById(R.id.clear4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayout4);
        int i = 0;
            for (final String string : CUSTOM_BUTTONS) {
                final Button button = new Button(getContext());
                button.setText(string);
                layout.addView(button);
                final int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        final EditText text = new EditText(getContext());
                        if (button.getText().toString().contains("Custom Button")) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Modify Button")
                                    .setMessage("Enter a custom word/phrase you would like to add")
                                    .setView(text)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            if (!text.getText().toString().equals("")) {
                                                button.setText(text.getText());
                                                System.out.println(finalI);
                                                CUSTOM_BUTTONS[finalI] = text.getText().toString();

                                                //update backend with new custom phrase
                                                String url = "http://coms-319-103.cs.iastate.edu:8080/add_custom/"+getName();
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
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    }).show();
                        } else {
                            tts.speak(button.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                        }
                    }
                });
                i++;
            }

        return view;
    }

    @Override
    public String[] getDefaultButtons() {
        return new String[0];
    }

    public void updateCustom(){
        final RequestQueue request = Volley.newRequestQueue(getContext());
        String username = getName();
        String update_url = "http://coms-319-103.cs.iastate.edu:8080/retrieve_custom/"+username;

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

                        for (int i = 0; i < length; i++) {
                            try {
                                CUSTOM_BUTTONS[i] = temp.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        request.add(jsonObjectRequest);
    }
}
