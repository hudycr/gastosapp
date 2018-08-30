package com.uep.followmymoney.ui.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.uep.followmymoney.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerFragment implements View.OnFocusChangeListener,
        TimePickerDialog.OnTimeSetListener, View.OnClickListener{

    private boolean firstInstance = true;
    private Calendar calendar;
    private EditText editText;
    private EditText editDateText;
    private View nextFocus;
    private Context ctx;
    private TimePickerDialog timePickerDialog;
    private int actualHour;
    private int actualMinute;
    private int actualDay;

    public TimePickerFragment(EditText editText, View nextFocus, Context ctx,EditText editDateText) {
        this.editText = editText;
        this.nextFocus = nextFocus;
        this.editText.setOnFocusChangeListener(this);
        this.editText.setOnClickListener(this);
        setActualTime();
        this.ctx = ctx;
        this.editDateText = editDateText;
    }

    private void setActualTime(){
        calendar = Calendar.getInstance();
        this.actualHour = calendar.get(Calendar.HOUR_OF_DAY);
        this.actualMinute = calendar.get(Calendar.MINUTE);
        this.actualDay = calendar.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        setActualTime();
        if (hasFocus && !firstInstance && (timePickerDialog ==null || !timePickerDialog.isShowing())) {
            createTimePicker();
        }else
            editText.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        setActualTime();
        if(timePickerDialog == null || !timePickerDialog.isShowing()){
            createTimePicker();
            firstInstance = false;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        if(isValidTime(selectedHour,selectedMinute)) {
            String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
            editText.setText(time);
            if (nextFocus != null)
                nextFocus.requestFocus();
        }
        else{
            createTimePicker();
            Toast.makeText(ctx,"Fuera de rango",Toast.LENGTH_LONG).show();
        }

    }

    private void createTimePicker(){
        timePickerDialog = new TimePickerDialog(ctx,this,actualHour,actualMinute,true);
        timePickerDialog.setCancelable(false);
        timePickerDialog.show();
    }

    private boolean isToday(){
        String rechargeDate = editDateText.getText().toString();
        if(rechargeDate.isEmpty())
            return true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(rechargeDate);
            calendar.setTime(date);
            if(actualDay == calendar.get(Calendar.DAY_OF_YEAR))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidTime(int selectedHour,int selectedMinute){
        if(isToday()) {
            if (selectedHour > actualHour)
                return false;
            else if (selectedHour == actualHour)
                if (selectedMinute > actualMinute)
                    return false;
        }
        return true;
    }
}