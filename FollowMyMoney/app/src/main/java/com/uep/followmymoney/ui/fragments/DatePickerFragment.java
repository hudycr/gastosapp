package com.uep.followmymoney.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by diego.panti on 25/10/2016.
 */

 public class DatePickerFragment implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener{

    public static String DATE_FORMAT = "dd MMMM yyyy";
    private boolean firstInstance = true;
    private EditText editText;
    private View nextFocus;
    private Calendar myCalendar;
    private Context ctx;
    private DatePickerDialog datePickerDialog;

    public DatePickerFragment(EditText editText, View nextFocus, Context ctx) {
        this.editText = editText;
        this.nextFocus = nextFocus;
        this.editText.setOnFocusChangeListener(this);
        this.editText.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        this.ctx = ctx;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        editText.setText(sdformat.format(myCalendar.getTime()));
        if  (nextFocus != null)
            nextFocus.requestFocus();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if (hasFocus && !firstInstance && (datePickerDialog ==null || !datePickerDialog.isShowing())) {
            createDatePicker();
        }else
            editText.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        if(datePickerDialog == null || !datePickerDialog.isShowing()){
            createDatePicker();
            firstInstance = false;
        }
    }

    private void createDatePicker(){
        datePickerDialog = new DatePickerDialog(ctx, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
}