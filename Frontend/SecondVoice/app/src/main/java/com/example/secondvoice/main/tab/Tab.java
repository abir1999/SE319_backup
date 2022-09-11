package com.example.secondvoice.main.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class Tab extends Fragment {

    private final String title;

    public String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Tab(String title) {
        this.title = title;
    }

    public abstract View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract String[] getDefaultButtons();

    public String getTitle() {
        return title;
    }

}
