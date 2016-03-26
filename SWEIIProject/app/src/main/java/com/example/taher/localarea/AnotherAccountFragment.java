package com.example.taher.localarea;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taher.localarea.R;

/**
 * Created by taher on 21/03/16.
 */
public class AnotherAccountFragment extends Fragment {
    View rootview;
    Button getUserLastLocation;
    Context context;
    UserModel user = null;
    UserModel wanted = null;

    public void setUser(UserModel user, UserModel wanted) {
        this.user = user;
        this.wanted = wanted;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.another_account_fragment, container, false);
        context = getContext();
        getUserLastLocation = (Button) rootview.findViewById(R.id.getUserLastLocation);
        getUserLastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootview;
    }
}
