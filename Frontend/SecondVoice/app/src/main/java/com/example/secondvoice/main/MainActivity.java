package com.example.secondvoice.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.secondvoice.canvas.CustomCanvas;
import com.example.secondvoice.login.LoginActivity;
import com.example.secondvoice.main.tab.TabAdapter;
import com.example.secondvoice.main.tab.impl.CommonWordsAndPhrases;
import com.example.secondvoice.main.tab.impl.Continued;
import com.example.secondvoice.main.tab.impl.Custom;
import com.example.secondvoice.main.tab.impl.FoodAndRestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.secondvoice.R;
import com.example.secondvoice.main.tab.impl.FrequentUsed;
import com.example.secondvoice.settings.SettingsActivity;
import com.google.android.material.tabs.TabLayout;

public final class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout layout = findViewById(R.id.tabs);
        layout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.canvas) {
            if (item.getTitle().equals("Canvas")) {
                CustomCanvas customCanvas = new CustomCanvas(this, null);
                setContentView(customCanvas);
                item.setTitle("Back");
            } else {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        String username = getIntent().getStringExtra("username");
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        CommonWordsAndPhrases commonWordsAndPhrases = new CommonWordsAndPhrases();
        adapter.addTab(commonWordsAndPhrases,username);

        Continued continued = new Continued();
        adapter.addTab(continued,username);

        FoodAndRestaurant foodAndRestaurant = new FoodAndRestaurant();
        adapter.addTab(foodAndRestaurant,username);

        Custom custom = new Custom();
        adapter.addTab(custom,username);

        FrequentUsed frequentUsed = new FrequentUsed();
        adapter.addTab(frequentUsed,username);

        viewPager.setAdapter(adapter);
    }
}
