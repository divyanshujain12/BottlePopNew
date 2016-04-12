package com.elgroup.lenovo.bottlepop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lenovo on 11-04-2016.
 */
public class SignUpActivity extends AppCompatActivity {


    @Bind(R.id.headerView)
    HeaderView headerView;
    @Bind(R.id.edtFullName)
    EditText edtFullName;
    @Bind(R.id.edtPassword)
    EditText edtPassword;
    @Bind(R.id.countryCodeSpinner)
    Spinner countryCodeSpinner;
    @Bind(R.id.edtPhone)
    EditText edtPhone;
    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.loginButtonFacebook)
    LoginButton loginButtonFacebook;
    @Bind(R.id.signUpTV)
    TextView signUpTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.sign_up_activity);

        ButterKnife.bind(this);
        InitViews();
    }

    private void InitViews() {
        headerView.InitViews(this);
    }


    @OnClick(R.id.signUpTV)
    public void onClick() {
    }
}
