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

    private static final String USER_DISPLAY_NAME = "displayName";
    private static final String USER_PHOTO_URL = "photoUrl";
    private static final String USER_SIGN_ON_ID = "signOnId";
    private static final String USER_GROUPS = "groups";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    private static final String DATABASE_GROUPS = "groups";
    private static final String DATABASE_USERS = "members";
    private static final String DATABASE_GROUP_NAME = "name";
    private static final String DATABASE_GROUP_CODE = "groupCode";

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
//        getMembers();

        User user1 = new User("Eric", "eric@gmail.com", null);
        User user2 = new User("Sarah", "sarah@gmail.com", null);
        User user3 = new User("Taylor", "taylor@gmail.com", null);
        listOfUsers.add(user1);
        listOfUsers.add(user2);
        listOfUsers.add(user3);

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void getMembers(){
        DatabaseReference ref = MyApplication.getGlobalDB().getReference("user");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot list: dataSnapshot.getChildren()){
                    Map<String, Object> map = ((Map<String, Object>) list.getValue());
                    String name = (String) map.get(USER_NAME);

                    String email = (String) map.get(USER_EMAIL);

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
