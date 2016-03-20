package com.example.taher.localarea;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taher.localarea.R;

/**
 * Created by taher on 21/03/16.
 */
public class AnotherAccountFragment extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.another_account_fragment, container, false);
        return rootview;
    }
}
