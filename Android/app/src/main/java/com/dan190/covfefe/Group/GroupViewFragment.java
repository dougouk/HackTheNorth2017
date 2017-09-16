package com.dan190.covfefe.Group;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan190.covfefe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dan on 16/09/2017.
 */

public class GroupViewFragment extends Fragment {
    private static final String TAG = GroupViewFragment.class.getSimpleName();

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
