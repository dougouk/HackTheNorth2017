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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Group.CreateGroupActivity;
import com.dan190.covfefe.Group.GroupViewFragment;
import com.dan190.covfefe.Models.FacebookAccount;
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.Util.GroupUtils;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.facebook.login.LoginManager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView headerName;
    private TextView headerContactInfo;
    private User currentUser;
    private FrameLayout container;
    private GroupViewFragment groupViewFragment;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Groups");

        // Initialization stuff
        loadSideMenu();
        setUpFAB();

        // Load group view fragment into activity
        contentMain.findViewById(R.id.container);
        if(groupViewFragment == null){
            groupViewFragment = new GroupViewFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.container, groupViewFragment).commit();

        // Initialize Http Volley
        queue = Volley.newRequestQueue(MyApplication.getInstance());

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
            }
        });

        FloatingActionButton cloudMessaging = new FloatingActionButton(MyApplication.getInstance());
        cloudMessaging.setIconDrawable(getDrawable(R.drawable.ic_cloud_circle_black_24dp));
        cloudMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountname = getAccount();

                final String scope = "audience:server:client_id:" + getString(R.string.o_auth_key_dan);
                String idToken = null;
                try{
                    idToken = GoogleAuthUtil.getToken(MyApplication.getInstance(), accountname, scope);
                } catch (GoogleAuthException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String SENDER_ID = "BLACH";
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                .setMessageId("Message id")
                .addData("my_message", "Hello message")
                .addData("my_action", "notification")
                .build());
            }
        });
        fab.addButton(createGroup);
        fab.addButton(joinGroup);
        fab.addButton(cloudMessaging);
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
        headerName = (TextView) headerView.findViewById(R.id.name);
        headerContactInfo = (TextView) headerView.findViewById(R.id.contactInfo);

        switch(MainSharedPreferences.retrieveAccountType(MyApplication.getInstance())){
            case 0:
                // No Account
                break;
            case MainSharedPreferences.EMAIL_ACCOUNT:
                currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
                headerName.setText(currentUser.getDisplayName());
                break;
            case MainSharedPreferences.FACEBOOK_ACCOUNT:
                FacebookAccount facebookAccount = MainSharedPreferences.retrieveFacebookAccount(MyApplication.getInstance());
                currentUser = new User(facebookAccount.getUsername(), MyApplication.getFirebaseAuth().getCurrentUser().getUid(), null, facebookAccount);
                headerName.setText(facebookAccount.getUsername());
                headerContactInfo.setText("");
                break;
            default:
                break;


        }
        currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
        headerName.setText(currentUser.getDisplayName());
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
        if (id == R.id.action_settings) {
            return true;
        }

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
        MyApplication.getFirebaseAuth().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }
}
