package com.dan190.covfefe.Group;


import android.app.Fragment;
import android.content.Intent;
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
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.GroupUtils;
import com.dan190.covfefe.Util.Keys;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dan on 16/09/2017.
 */

public class GroupViewFragment extends Fragment {
    private static final String TAG = GroupViewFragment.class.getSimpleName();

    private List<List<User>> listListUser;
    private GroupViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> listOfGroups;
    private List<User> listOfUsers;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_view, container, false);
        ButterKnife.bind(this, view);

        listListUser = new ArrayList<>();
        listOfGroups = new ArrayList<>();
        listOfUsers = new ArrayList<>();
        getMembers();

        loadGroups();

        listListUser.add(listOfUsers);

        adapter = new GroupViewAdapter(getContext(), "Group1", -1, listListUser);
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
                        if(position == 0){
                            startActivity(new Intent(MyApplication.getInstance(), GroupViewActivity.class));
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Log.d("recycler", "onclick at " + position);
                    }
                }
        ));
        linearLayoutManager = new LinearLayoutManager(MyApplication.getInstance());
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadGroups();
        });
        return view;
    }

    private void loadGroups() {
        // TODO get list of groups this user is in
        DatabaseReference ref = MyApplication.getGlobalDB().getReference(Keys.USERS);
        String firebaseID = MainSharedPreferences.retrieveUser(MyApplication.getInstance()).getFirebaseDbId();
        String currentSignOnId = MainSharedPreferences.retrieveUser(MyApplication.getInstance()).getSignOnId();

        Log.i(TAG, "Current id is " + currentSignOnId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String signOnId = (String) snapshot.child(Keys.USER_SIGN_ON_ID).getValue();
                    Log.d(TAG, "comparing against " + signOnId);
                    if (signOnId.equals(currentSignOnId)) {
                        DataSnapshot groups = snapshot.child(Keys.DATABASE_GROUPS);
                        for (DataSnapshot group : groups.getChildren()) {
                            Log.i(TAG, group.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // TODO get list of members of each group

        // TODO Load the lists into the adapter

        swipeRefreshLayout.setRefreshing(false);
    }

    private void getMembers(){
        DatabaseReference ref = MyApplication.getGlobalDB().getReference("user");
//        String groupKey = (String) ref.child(MainSharedPreferences.retrieveFirebaseId(MyApplication.getInstance())).child("group").getKey();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot list: dataSnapshot.getChildren()){
                    Map<String, Object> map = ((Map<String, Object>) list.getValue());
                    String name = (String) map.get(Keys.USER_NAME);

                    String email = (String) map.get(Keys.USER_EMAIL);

                    User user = new User(name, email, null);
                    listOfUsers.add(user);
                    adapter.notifyDataSetChanged();

                    /*if(name.equals("Dan")){
                        Map<String, Object> groups = (Map<String, Object>) map.get(USER_GROUPS);
                        Set<String> keySet = groups.keySet();
                        for(String key : keySet){
                            listOfGroups.add(key);
                        }
                    }*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        });

        /*Group group = null;
        Set<String> userIds = null;
        DatabaseReference groups = MyApplication.getGlobalDB().getReference(DATABASE_GROUPS);
        groups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot list: dataSnapshot.getChildren()){
                    Log.d(TAG, list.getKey());
                    if(listOfGroups.contains(list.getKey())){
                        // Get list of users as a group
                        Map<String, Object> groupMap = ((Map<String, Object>) list.getValue());
                        String groupName = (String) groupMap.get(DATABASE_GROUP_NAME);
                        Map<String, Object> users = ((Map<String, Object>) groupMap.get(DATABASE_USERS));
                        Set<String> keySet = users.keySet();
                        List<User> members = new ArrayList<User>();
                        for(String key : keySet){
                            listOfUsers.add(key);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        return;


    }
}
