package com.elgroup.lenovo.bottlepop.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.elgroup.lenovo.bottlepop.Adapters.CountryCodeCustomAdapter;
import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CallBackInterface;
import com.elgroup.lenovo.bottlepop.Utils.CommonFunctions;
import com.elgroup.lenovo.bottlepop.Utils.GlobalAsyncTask;
import com.elgroup.lenovo.bottlepop.Utils.Keys;
import com.elgroup.lenovo.bottlepop.Utils.UrlList;
import com.neopixl.pixlui.components.textview.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by Lenovo on 14-01-2016.
 */
public class ReservationFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener {

    private Dialog dialog;

    private LinearLayout attendingPeopleCountLL;
    private TextView manCount, womanCount, txtConfirmBooking;
    private EditText edtFullName, edtPhone, edtEmail, edtClubName, edtTime, edtSpend;
    private int year, month, day;
    private DatePicker datePicker;
    private Calendar calendar;
    private static String project, price;
    private int priceInt;
    private Spinner countryCodeSpinner;
    private String Name, number, email, time, spend, manNumbers = "", womenNumbers = "";
    private String countryCode = "";
    private LinearLayout clubLL;
    private String type = "0";

    public static ReservationFragment newInstance(String projectNameValue, String priceValue) {
        ReservationFragment fragment = new ReservationFragment();

        project = projectNameValue;
        price = priceValue;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reservation_fragment, container,
                false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CommonFunctions.getInstance().setActionBarWithBackButton(getActivity());
        MainActivity.headerText.setText("RESERVATION");

        InitViews();
    }

    private void InitViews() {

        edtFullName = (EditText) getView().findViewById(R.id.edtFullName);

        countryCodeSpinner = (Spinner) getView().findViewById(R.id.countryCodeSpinner);
        CountryCodeCustomAdapter countryCodeCustomAdapter = new CountryCodeCustomAdapter(getActivity(), Arrays.asList(Keys.countryCodeArray));
        countryCodeSpinner.setAdapter(countryCodeCustomAdapter);
        countryCodeSpinner.setOnItemSelectedListener(this);

        edtPhone = (EditText) getView().findViewById(R.id.edtPhone);

        edtEmail = (EditText) getView().findViewById(R.id.edtEmail);

        clubLL = (LinearLayout) getView().findViewById(R.id.clubLL);
        clubLL.setVisibility(View.GONE);
        edtClubName = (EditText) getView().findViewById(R.id.edtClubName);

        edtTime = (EditText) getView().findViewById(R.id.edtTime);
        edtTime.setOnTouchListener(this);

        edtSpend = (EditText) getView().findViewById(R.id.edtSpend);

        attendingPeopleCountLL = (LinearLayout) getView().findViewById(R.id.attendingPeopleCountLL);
        attendingPeopleCountLL.setOnClickListener(this);

        manCount = (TextView) getView().findViewById(R.id.manCount);

        womanCount = (TextView) getView().findViewById(R.id.womanCount);

        txtConfirmBooking = (TextView) getView().findViewById(R.id.txtConfirmBooking);
        txtConfirmBooking.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (BookingFragment.pos != HomeFragment.homeFragmentModels.size() - 1) {
            type = "0";
            priceInt = Integer.parseInt(price);
        } else {
            type = "1";
            priceInt = 500;
            clubLL.setVisibility(View.VISIBLE);
        }


        showDate(formatDate(year, month, day));
    }


    @Override
    public void onClick(View v) {
        if (v == attendingPeopleCountLL)
            /*
                 creating wheel for selecting male and female count
             */
            SelectNumberDialog();
        else if (v == txtConfirmBooking)
             /*
                calling web service for saving and confirming booking
            */
            SendDataToBackend();
    }

    private void SendDataToBackend() {

        Name = edtFullName.getText().toString();
        number = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        time = edtTime.getText().toString();
        spend = edtSpend.getText().toString();
        manNumbers = manCount.getText().toString();
        womenNumbers = womanCount.getText().toString();


        if (!CommonFunctions.getInstance().validateEmpty(edtFullName)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_name), Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!CommonFunctions.getInstance().validateEmpty(edtPhone)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_number), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!CommonFunctions.getInstance().validateNumber(edtPhone)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_number), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!CommonFunctions.getInstance().validateEmpty(edtEmail)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_email), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!CommonFunctions.getInstance().validateEmail(edtEmail)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_email), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (clubLL.getVisibility() == View.VISIBLE) {
            if (!CommonFunctions.getInstance().validateEmpty(edtClubName)) {
                Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_club_name), Snackbar.LENGTH_LONG).show();
                return;
            } else
                project = edtClubName.getText().toString();
        }
        if (!CommonFunctions.getInstance().validateEmpty(edtTime)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_time), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!CommonFunctions.getInstance().validateEmpty(edtSpend)) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_empty_spend), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (priceInt > Integer.parseInt(edtSpend.getText().toString())) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_spend_amount), Snackbar.LENGTH_LONG).show();
            return;
        }

        if (Integer.parseInt(manNumbers) <= 0 && Integer.parseInt(womenNumbers) <= 0) {
            Snackbar.make(getView(), getResources().getText(R.string.err_msg_visitors_count), Snackbar.LENGTH_LONG).show();
            return;
        }


        GlobalAsyncTask task = new GlobalAsyncTask(getActivity(), createUrlFromParameters(), new CallBackInterface() {
            @Override
            public void onSuccess(String string) {
                try {
                    String error = new JSONObject(string).getString("error");
                    if (error.equals("0")) {
                        MainActivity.updateFragment(ConfirmationFragment.newInstance());
                    } else
                        Snackbar.make(getView(), new JSONObject(string).optString("msg"), Snackbar.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String string) {

            }
        });
        task.execute(new ArrayList<NameValuePair>());
    }

    /*
        creating url for sending data to backend.
    */
    private String createUrlFromParameters() {
        String url = "name=" + Name + "&male_no=" + manNumbers + "&female_no=" + womenNumbers + "&phone=" + countryCode + number + "&email=" + email + "&arrival_time=" + time + "&tablename=" + project.replace(" ", "") + "&tableprice=" + String.valueOf(priceInt).replace(" ", "") + "&exspend=" + spend.replace(" ", "") + "&type=" + type;
        return UrlList.BASE_URL + "tablebooking.php?" + url.replace(" ", "");
    }


    protected DatePickerDialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            return new DatePickerDialog(getActivity(), myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date date = sdf.parse((new StringBuilder().append(dayOfMonth).append("-")
                        .append(month).append("-").append(year)).toString());

                sdf = new SimpleDateFormat("DD-MMMM-yyyy");
                String formatedDate = sdf.format(date);
                showDate(formatDate(year, monthOfYear, dayOfMonth));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    };

    private String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");

        return sdf.format(date);
    }

    private void showDate(String date) {
        edtTime.setText(date);
    }

    /*
            Dialog for selecting numbers of male and female
     */
    private void SelectNumberDialog() {
        dialog = new Dialog(getActivity(), R.style.DialogSlideAnim1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.count_picker);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        final WheelView manWheel = (WheelView) dialog.findViewById(R.id.manWheel);
        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(getActivity(), 0, 20, "%2d");
        hourAdapter.setItemResource(R.layout.wheel_text_item);
        hourAdapter.setItemTextResource(R.id.text);
        manWheel.setViewAdapter(hourAdapter);
        manWheel.setCyclic(true);

        final WheelView womanWheel = (WheelView) dialog.findViewById(R.id.womanWheel);
        hourAdapter.setItemResource(R.layout.wheel_text_item);
        hourAdapter.setItemTextResource(R.id.text);
        womanWheel.setViewAdapter(hourAdapter);
        womanWheel.setCyclic(true);

        final TextView txtOK = (TextView) dialog.findViewById(R.id.txtOK);
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (manWheel.getCurrentItem() > 0)
                manCount.setText(String.valueOf(manWheel.getCurrentItem()));
                //if (womanWheel.getCurrentItem() > 0)
                womanCount.setText(String.valueOf(womanWheel.getCurrentItem()));
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            DatePickerDialog dialog = onCreateDialog(999);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
            // showDate(year, month+1, day);
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        countryCode = Keys.countryCodeArray[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
