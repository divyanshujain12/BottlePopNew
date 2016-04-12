package com.elgroup.lenovo.bottlepop.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;

/**
 * Created by Lenovo on 11-01-2016.
 */
public class CommonFunctions {
    private static CommonFunctions commonFunctions = null;

    private CommonFunctions() {

    }

    public static CommonFunctions getInstance() {
        if (commonFunctions == null)
            commonFunctions = new CommonFunctions();


        return commonFunctions;
    }

    public int getResources(String Name, Context context) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(Name, "drawable",
                context.getPackageName());
        return resourceId;
    }

    public String[] getArray(String name, Context context) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "array",
                context.getPackageName());

        return resources.getStringArray(resourceId);
    }

    public void setActionBarWithBackButton(final Activity activty) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activty.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activty.getResources().getColor(R.color.booking_background));
        }
        MainActivity.headerText.setTextColor(activty.getResources().getColor(R.color.style_color_primary_dark));
        // MainActivity.appBarLayout.setExpanded(true, true);
        ((MainActivity) activty).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  ((MainActivity) activty).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) activty).toolbar.setNavigationIcon(R.drawable.back);
        ((MainActivity) activty).toolbar.setBackgroundColor(activty.getResources().getColor(R.color.booking_background));
        ((MainActivity) activty).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activty.onBackPressed();
            }
        });
    }

    public boolean validateEmpty(EditText inputName) {
        if (inputName.getText().toString().trim().isEmpty()) {

            return false;
        }

        return true;
    }

    public boolean validateEmail(EditText inputEmail) {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {

            return false;
        }

        return true;
    }

    public boolean validateNumber(EditText inputNumber) {
        String number = inputNumber.getText().toString().trim();
        if (number.isEmpty() || number.length() < 10) {

            return false;
        }

        return true;


    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
