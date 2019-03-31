package com.kamilkorzeniewski.stockcontrolclient;


import androidx.fragment.app.Fragment;

public abstract class CustomFragment extends Fragment {
    public abstract CustomFragment newInstance();
    public abstract String getPageTitle();
}
