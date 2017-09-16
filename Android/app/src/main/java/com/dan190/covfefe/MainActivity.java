package com.dan190.covfefe;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.FacebookAccount;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.facebook.login.LoginManager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView headerName;
    private TextView headerContactInfo;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace action to create a group!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        FloatingActionButton createGroup = new FloatingActionButton(MyApplication.getInstance());
        createGroup.setIconDrawable(getDrawable(R.drawable.ic_add_circle_black_24dp));
        createGroup.setEnabled(true);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Create group clicked");
            }
        });

        FloatingActionButton joinGroup = new FloatingActionButton(MyApplication.getInstance());
        joinGroup.setIconDrawable(getDrawable(R.drawable.ic_exit_to_app_black_24dp));
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Join group clicked");
            }
        });
        fab.addButton(createGroup);
        fab.addButton(joinGroup);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set username, email and profile picture
        View headerView = navigationView.getHeaderView(0);
        headerName = (TextView) headerView.findViewById(R.id.name);
        headerContactInfo = (TextView) headerView.findViewById(R.id.contactInfo);

        //TODO incorrect display name and email
        switch(MainSharedPreferences.retrieveAccountType(MyApplication.getInstance())){
            case 0:
                // No Account
                break;
            case MainSharedPreferences.EMAIL_ACCOUNT:
                currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
                headerName.setText(currentUser.getDisplayName());
                headerContactInfo.setText(currentUser.getEmail());
                break;
            case MainSharedPreferences.FACEBOOK_ACCOUNT:
                FacebookAccount facebookAccount = MainSharedPreferences.retrieveFacebookAccount(MyApplication.getInstance());
                currentUser = new User(facebookAccount.getUsername(), null, null, null, facebookAccount);
                headerName.setText(facebookAccount.getUsername());
                headerContactInfo.setText("");
                break;
            default:
                break;


        }
        currentUser = MainSharedPreferences.retrieveUser(MyApplication.getInstance());
        headerName.setText(currentUser.getDisplayName());
        headerContactInfo.setText(currentUser.getEmail());


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

    private void signOutAndFinish(){
        MyApplication.getFirebaseAuth().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }
}
