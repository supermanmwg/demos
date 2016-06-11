package com.demos.guidepage.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demos.R;
import com.library.SwipeCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiguangmeng on 16/3/23.
 */
public class tantanFragment extends Fragment {

    SwipeCard swipeCard;

    private ArrayList<String> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tantan, container, false);
        swipeCard = (SwipeCard) view.findViewById(R.id.swipe_card);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mData = new ArrayList<>();
        Adapter adapter = new SwipeStackAdapter(mData);
        swipeCard.setAdapter(adapter);

        fillWithTestData();
    }

    private void fillWithTestData() {
        for (int x = 0; x < 10; x++) {
            mData.add(" " + (x + 1));
        }
    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<String> mData;

        public SwipeStackAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =getLayoutInflater(null).inflate(R.layout.card, parent, false);
            }

            TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
            textViewCard.setText(mData.get(position));

            return convertView;
        }
    }
}
