package com.example.secondvoice;

import android.content.ComponentName;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.Toast;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.secondvoice.login.LoginActivity;
import com.example.secondvoice.main.MainActivity;
import com.example.secondvoice.settings.SettingsActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.secondvoice", appContext.getPackageName());
    }

    @Test
    public void testConstructionTTS() {
        try {
            TextToSpeech tts = null;
            tts = new TextToSpeech(getInstrumentation().getTargetContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) { }

                });
        } catch (Exception e) {
            fail("Cannot initialize TTS engine.");
        }
    }

    @Test
    public void testSettings() {
        Espresso.onView(ViewMatchers.withId(R.id.settings)).perform(ViewActions.click());
        Intents.intended(hasComponent(new ComponentName(getInstrumentation().getTargetContext(), SettingsActivity.class)));

        Espresso.onView(ViewMatchers.withId(R.id.gender)).perform(ViewActions.click());
        Espresso.onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(ViewActions.click());
        assertEquals(SettingsActivity.MALE, false);
    }

    @Test
    public void testLogout() {
        Espresso.onView(ViewMatchers.withId(R.id.logout)).perform(ViewActions.click());
        Intents.intended(hasComponent(new ComponentName(getInstrumentation().getTargetContext(), LoginActivity.class)));

        Espresso.onView(ViewMatchers.withId(R.id.loginName)).perform(ViewActions.typeText("hello"));
        Espresso.onView(ViewMatchers.withId(R.id.loginPassword)).perform(ViewActions.typeText("hello"));
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        //Intents.intended(hasComponent(new ComponentName(getInstrumentation().getTargetContext(), MainActivity.class)));
    }

    @Test
    public void testButtons() {
        Espresso.onView(ViewMatchers.withId(R.id.enter1)).perform(ViewActions.typeText("Testing custom string to speak!"));
        Espresso.onView(ViewMatchers.withId(R.id.speak1)).perform(ViewActions.click());
    }



}
