package com.dan190.covfefe.Group;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan190.covfefe.Adapters.GroupViewAdapter;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Listeners.RecyclerViewClickListener;
import com.dan190.covfefe.Listeners.RecyclerViewOnTouchListener;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.GroupUtils;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dan on 16/09/2017.
 */

public class GroupViewFragment extends Fragment {
    private static final String TAG = GroupViewFragment.class.getSimpleName();

    private static final String USER_DISPLAY_NAME = "displayName";
    private static final String USER_PHOTO_URL = "photoUrl";
    private static final String USER_SIGN_ON_ID = "signOnId";

    private List<User> users;
    private GroupViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_view, container, false);
        ButterKnife.bind(this, view);

        Log.d(TAG, "Group fragment oncreate");
        String groupkey = "1";
        users = new ArrayList<>();
        getMembers(groupkey);

        adapter = new GroupViewAdapter(getContext(), "Group1", -1, users);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getInstance(),
                DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerViewOnTouchListener(
                MyApplication.getInstance(),
                recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Log.d("recycler", "onclick at" + position);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Log.d("recycler", "onclick at " + position);
                    }
                }
        ));
        linearLayoutManager = new LinearLayoutManager(MyApplication.getInstance());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void getMembers(String groupKey){
        DatabaseReference ref = MyApplication.getGlobalDB().getReference("user");
//        String groupKey = (String) ref.child(MainSharedPreferences.retrieveFirebaseId(MyApplication.getInstance())).child("group").getKey();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, String.format("%d children", dataSnapshot.getChildrenCount()));
                for(DataSnapshot list: dataSnapshot.getChildren()){
                    Map<String, Object> map = ((Map<String, Object>) list.getValue());
                    String displayName = (String) map.get(USER_DISPLAY_NAME);
                    String photoUrl = (String) map.get(USER_PHOTO_URL);
                    String signOnId = (String) map.get(USER_SIGN_ON_ID);
                    User user = new User(displayName, signOnId, photoUrl, null);
                    users.add(user);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        });

        return;


    }
}
