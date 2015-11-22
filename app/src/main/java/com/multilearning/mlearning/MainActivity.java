package com.multilearning.mlearning;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.multilearning.mlearning.model.User;
import com.multilearning.mlearning.navigationdrawer.NavigationDrawerCallbacks;
import com.multilearning.mlearning.navigationdrawer.NavigationDrawerFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private User user;

    private boolean pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        user = new User();

        getInfoUser();

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(user.getName(), user.getEmail(), BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

        mNavigationDrawerFragment.SignOut();


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;
        switch(position){
            case 1:
                fragment = new WordsFragment();
                break;
            case 2:
                fragment = new LessonFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }

        return super.onKeyDown(keyCode, event);
    }

    public User getUser() {
        return user;
    }

    private void getInfoUser(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user");

        user = (User) bundle.getSerializable("info_user");
    }

}
