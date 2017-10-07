package com.javahelps.todobasic;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    Intent myIntent;
    PendingIntent pendingIntent ;
    Calendar myCalendar ;
    EditText task , date ;
    TimePicker timePicker ;
    EditText time ;
    String format = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        task = (EditText) findViewById(R.id.edittext_task);
        date = (EditText) findViewById(R.id.editdate_task);
//        timePicker = (TimePicker)findViewById(R.id.timePicker);
        time = (EditText)findViewById(R.id.edittime_task);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this , dateSetListener , myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH ),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddActivity.this, onStartTimeListener, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
            }

            TimePickerDialog.OnTimeSetListener onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String AM_PM = " ";

                    if (hourOfDay >= 12)
                        AM_PM = "PM";
                    else
                        AM_PM = "AM" ;
                    time.setText(hourOfDay + " : " + minute + "  " + AM_PM);
                    myCalendar.set(Calendar.HOUR, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                }
            };
        });

        myCalendar.getTimeInMillis();
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }

    public void onAddClicked(View view){
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        String taskText = task.getText().toString();
        String dateText = date.getText().toString();
        String timeText = time.getText().toString();

        myIntent = new Intent(AddActivity.this , AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this , 0  ,myIntent , 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP , SystemClock.elapsedRealtime()+3000 , 3000 , pendingIntent);
        TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TODO_TASK , taskText);
        contentValues.put(Contract.TODO_DATE , dateText);
        contentValues.put(Contract.TODO_TIME , timeText);

        long id = db.insert(Contract.TODO_DATA_TABLE , null , contentValues);

        Intent result = new Intent();
        result.putExtra(Contract.TODO_TASK_ID , id);
        setResult(20 , result);
        Log.i("tag", "onAddClicked: added");
        finish();
    }
}
