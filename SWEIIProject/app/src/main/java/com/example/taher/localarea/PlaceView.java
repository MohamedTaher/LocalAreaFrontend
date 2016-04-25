package com.example.taher.localarea;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kareem on 4/25/16.
 */
public class PlaceView  extends Fragment {
    private PlaceModel place = new PlaceModel();
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PlaceInfoFrag placeInfoFrag = new PlaceInfoFrag();
    private PlaceOptionsFrag placeOptionsFrag = new PlaceOptionsFrag();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.placemodel, container, false);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("placeID", place.getId()+"");
        Connection con = new Connection(params, new GetPlacePostListener());
        con.execute(Constants.getPlaceByID);
        viewPager = (ViewPager) view.findViewById(R.id.placeViewPager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.placeTabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(placeInfoFrag, "Place Info");
        adapter.addFragment(placeOptionsFrag, "Options");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
        this.place = place;
    }


    private class GetPlacePostListener implements ConnectionPostListener{

        @Override
        public void doSomething(String result) {
            try {
                JSONObject jPlace = new JSONObject(result);
                place.setUserID(jPlace.getInt("userID"));
                place.setLng(jPlace.getDouble("lng"));
                place.setLat(jPlace.getDouble("lat"));
                place.setDescription(jPlace.getString("description"));
                place.setName(jPlace.getString("name"));
                place.setNumberOfCheckins(jPlace.getInt("numberOfCheckins"));
                place.setRateSum(jPlace.getInt("rateSum"));
                place.setUserNum(jPlace.getInt("userNum"));
                placeInfoFrag.initiate(place);
                placeOptionsFrag.initiate(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
