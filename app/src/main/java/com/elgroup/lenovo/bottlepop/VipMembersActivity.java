package com.elgroup.lenovo.bottlepop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elgroup.lenovo.bottlepop.Adapters.VipPagerAdapter;
import com.neopixl.pixlui.components.textview.TextView;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.ButterKnife;

/**
 * Created by Lenovo on 08-04-2016.
 */
public class VipMembersActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView txtContent;
    private VipPagerAdapter vipPagerAdapter;
    private CirclePageIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.vip_members_activity);


        InitViews();
    }

    private void InitViews() {
        circleIndicator = (CirclePageIndicator) findViewById(R.id.circleIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        txtContent = (TextView) findViewById(R.id.txtContent);
        vipPagerAdapter = new VipPagerAdapter(this);
        viewPager.setAdapter(vipPagerAdapter);
        circleIndicator.setViewPager(viewPager);
    }

    public void Signup(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
}
