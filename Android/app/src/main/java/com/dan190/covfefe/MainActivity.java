package com.dan190.covfefe;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Group.CreateGroupActivity;
import com.dan190.covfefe.Group.GroupViewFragment;
import com.dan190.covfefe.Group.JoinGroupActivity;
import com.dan190.covfefe.Models.FacebookAccount;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.facebook.login.LoginManager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView headerName;
    private TextView headerContactInfo;
    private User currentUser;
    private FrameLayout container;
    private GroupViewFragment groupViewFragment;
    private EditText balance;

    private RequestQueue queue;

    @BindView(R.id.fab)
    FloatingActionsMenu fab;

    @BindView(R.id.content_main)
    ConstraintLayout contentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        balance = findViewById(R.id.BalanceAmountTxt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Groups");

        // Initialization stuff
        loadSideMenu();
        setUpFAB();
        loadGroupsFragment();

        // Initialize Http Volley
        queue = Volley.newRequestQueue(MyApplication.getInstance());
    }

    private void loadGroupsFragment() {
        // Load group view fragment into activity
        contentMain.findViewById(R.id.container);
        if(groupViewFragment == null){
            groupViewFragment = new GroupViewFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.container, groupViewFragment).commit();

        // Initialize Http Volley
        queue = Volley.newRequestQueue(MyApplication.getInstance());

        RequestQueue getQ = Volley.newRequestQueue(this);
        String url ="https://us-central1-hackthenorth2017-630f0.cloudfunctions.net/CheckforAccountBalance";
        // text named balance that needs to be changed
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 5 characters of the response string.
                        balance.setText(response.substring(1,5));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                balance.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    private void setUpFAB() {
        FloatingActionButton createGroup = new FloatingActionButton(MyApplication.getInstance());
        createGroup.setIconDrawable(getDrawable(R.drawable.ic_shape));
        createGroup.setColorNormal(R.color.colorTextColor);
        createGroup.setColorPressed(R.color.colorPrimaryDark);

        createGroup.setEnabled(true);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Create group clicked");
                startActivity(new Intent(MainActivity.this, CreateGroupActivity.class));
            }
        });

        FloatingActionButton joinGroup = new FloatingActionButton(MyApplication.getInstance());
        joinGroup.setIconDrawable(getDrawable(R.drawable.ic_group));
        joinGroup.setColorNormal(R.color.colorTextColor);
        joinGroup.setColorPressed(R.color.colorPrimaryDark);
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Join group clicked");
                startActivity(new Intent(MainActivity.this, JoinGroupActivity.class));
            }
        });

        fab.addButton(createGroup);
        fab.addButton(joinGroup);
    }

    private void loadSideMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set username, email and profile picture
        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.name);
        headerContactInfo = headerView.findViewById(R.id.contactInfo);

        switch(MainSharedPreferences.retrieveAccountType(MyApplication.getInstance())){
            case 0:
                // No Account
                break;
            case MainSharedPreferences.EMAIL_ACCOUNT:
                currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
                headerName.setText(currentUser.getDisplayName());
                headerContactInfo.setText("");
                break;
            case MainSharedPreferences.FACEBOOK_ACCOUNT:
                FacebookAccount facebookAccount = MainSharedPreferences.retrieveFacebookAccount(MyApplication.getInstance());
                currentUser = new User(facebookAccount.getUsername(), MyApplication.getFirebaseAuth().getCurrentUser().getUid(), facebookAccount);
                headerName.setText(facebookAccount.getUsername());
                headerContactInfo.setText("");
                break;
            default:
                break;


        }
        currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
        headerName.setText(currentUser.getDisplayName());

        headerName.setText("Dan");
        headerContactInfo.setText("dougouk@gmail.com");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_logout){
            // Log out
            signOutAndFinish();
        }else if (id == R.id.nav_profile){
            // Edit profile
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
        }else if (id == R.id.nav_history){
            // TODO
        }else if (id == R.id.nav_groups){
            loadGroupsFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // This snippet takes the simple approach of using the first returned Google account,
    // but you can pick any Google account on the device.
    public String getAccount() {
        Account[] accounts = AccountManager.get(getApplicationContext()).
                getAccountsByType("com.google");
        if (accounts.length == 0) {
            return null;
        }
        return accounts[0].name;
    }

    private void signOutAndFinish(){
        MainSharedPreferences.logOut(MyApplication.getInstance());
        MyApplication.getFirebaseAuth().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }
}
