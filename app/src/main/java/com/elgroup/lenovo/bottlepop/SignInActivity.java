package com.elgroup.lenovo.bottlepop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lenovo on 11-04-2016.
 */
public class SignInActivity extends AppCompatActivity {
    @Bind(R.id.headerView)
    HeaderView headerView;
    @Bind(R.id.edtFullName)
    EditText edtFullName;
    @Bind(R.id.edtPassword)
    EditText edtPassword;
    @Bind(R.id.loginButtonFacebook)
    LoginButton loginButtonFacebook;
    @Bind(R.id.loginTV)
    TextView loginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.sign_in_activity);

        ButterKnife.bind(SignInActivity.this);
        InitViews();
    }

    private void InitViews() {
        headerView.InitViews(this);
    }

    @OnClick(R.id.loginTV)
    public void onClick() {

    }

}
