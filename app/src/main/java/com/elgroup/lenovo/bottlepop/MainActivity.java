package com.elgroup.lenovo.bottlepop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.elgroup.lenovo.bottlepop.Fragments.AboutTheAppFragment;
import com.elgroup.lenovo.bottlepop.Fragments.ConciergeFragment;
import com.elgroup.lenovo.bottlepop.Fragments.GuestListFragment;
import com.elgroup.lenovo.bottlepop.Fragments.HomeFragment;
import com.elgroup.lenovo.bottlepop.Fragments.HowIWorksFragment;
import com.elgroup.lenovo.bottlepop.Fragments.Ranking;
import com.elgroup.lenovo.bottlepop.Utils.CommonFunctions;
import com.neopixl.pixlui.components.textview.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    public static FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public Toolbar toolbar;
    public static TextView headerText;
    public static String ClickedCategoryTag = "";
    private NavigationView navigation_view;
    public static DrawerLayout id_drawerlayout;
    private RelativeLayout menu;
    private ImageView imgAppIcon;
    private TextView txtHome, txtAbout, txtHowItWork, txtConcierge, txtVipMembers, txtRequestGuestList, txtLeaderBoard;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean opened = false;

    public CoordinatorLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        InitViews();
    }

    private void InitViews() {


        setupToolbar();
        fragmentManager = getSupportFragmentManager();
        content = (CoordinatorLayout) findViewById(R.id.content);
        id_drawerlayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        id_drawerlayout.setDrawerShadow(R.drawable.shadow, Gravity.RIGHT);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);

        menu = (RelativeLayout) findViewById(R.id.menu);
        double width = getResources().getDisplayMetrics().widthPixels / 1.6;
        DrawerLayout.LayoutParams params1 = (DrawerLayout.LayoutParams) navigation_view.getLayoutParams();
        params1.width = (int) width;
        navigation_view.setLayoutParams(params1);

        imgAppIcon = (ImageView) menu.findViewById(R.id.imgAppIcon);
        imgAppIcon.setVisibility(View.GONE);

        txtHome = (TextView) menu.findViewById(R.id.txtHome);
        txtHome.setOnClickListener(this);

        txtAbout = (TextView) menu.findViewById(R.id.txtAbout);
        txtAbout.setOnClickListener(this);

        txtHowItWork = (TextView) menu.findViewById(R.id.txtHowItWork);
        txtHowItWork.setOnClickListener(this);

        txtConcierge = (TextView) menu.findViewById(R.id.txtConcierge);
        txtConcierge.setOnClickListener(this);

        txtVipMembers = (TextView) menu.findViewById(R.id.txtVipMembers);
        txtVipMembers.setOnClickListener(this);

        txtRequestGuestList = (TextView) menu.findViewById(R.id.txtRequestGuestList);
        txtRequestGuestList.setOnClickListener(this);

        txtLeaderBoard = (TextView) menu.findViewById(R.id.txtLeaderBoard);
        txtLeaderBoard.setOnClickListener(this);
        updateFragment(HomeFragment.newInstance());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, id_drawerlayout, toolbar, R.string.openDrawer, R.string.openDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                opened = false;
                imgAppIcon.setVisibility(View.GONE);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset > 0 && !opened) {
                    opened = true;
                    imgAppIcon.setVisibility(View.VISIBLE);
                    imgAppIcon.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_dialog));
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        id_drawerlayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    protected void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        headerText = (com.neopixl.pixlui.components.textview.TextView) toolbar.findViewById(R.id.toolbar_title);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.side_menu_icon);
    }

    public static void updateFragment(Fragment fragment) {
        String name = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            if (!(fragment instanceof HomeFragment))
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            if (id_drawerlayout.isDrawerOpen(Gravity.LEFT))
                id_drawerlayout.closeDrawers();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (id_drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            id_drawerlayout.closeDrawers();
        } else {
            if (fragmentManager == null)
                finish();

            if (fragmentManager.getBackStackEntryCount() == 0) {
                fragmentManager = null;
                CommonFunctions functions = CommonFunctions.getInstance();
                functions = null;
                finish();
            }
            super.onBackPressed();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtHome:
                updateFragment(new HomeFragment());
                break;
            case R.id.txtAbout:
                updateFragment(new AboutTheAppFragment());
                break;
            case R.id.txtConcierge:
                updateFragment(new ConciergeFragment());
                break;
            case R.id.txtVipMembers:
                Intent intent = new Intent(this, VipMembersActivity.class);
                startActivity(intent);
                break;
            case R.id.txtRequestGuestList:
                updateFragment(new GuestListFragment());
                break;
            case R.id.txtLeaderBoard:
                updateFragment(new Ranking());
                break;
            case R.id.txtHowItWork:
                updateFragment(new HowIWorksFragment());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HomeFragment.homeFragmentModels.clear();
    }
}
