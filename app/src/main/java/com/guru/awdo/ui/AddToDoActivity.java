package com.guru.awdo.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.guru.awdo.R;
import com.guru.awdo.db.ToDoTableHelper;
import com.guru.awdo.pojos.ToDoData;
import com.guru.awdo.receiver.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToDoActivity extends AppCompatActivity {

    private EditText mDescriptionET;
    private AutoCompleteTextView mAutocompleteCategoryView;
    private Button   mTodayBt,mTomorrowBt,mCustomDateBt,mCustomTimeBt,mSubmitBt;
    private Button   mPlusOneHourBt,mPlusTwoHourBt;
    private TextView mTimeSetTv;
    private int mYear,mMonth,mDate,mHour,mMinute;
    private long mTimeStamp,mSelectedTime;
    private String mTempDate,mTempMonth,mTempYear,mTempHour,mTempMin;
    private String mAMorPM;
    SimpleDateFormat mSimpleDateFormatOfHourMinute;
    List<String>mCategoryList = new ArrayList<>();
    ToDoData mToDoData = new ToDoData();
    ToDoTableHelper mTodoTableHelper = new ToDoTableHelper(this);
    int mSelectedYear,mSelectedMonth,mSelectedDate,mSelectedHour,mSelectedMinute;
    long mInsertedRowID;
    Calendar lCalendar = Calendar.getInstance();

    private String mDescriptionVal,mCategoryVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("layout","before");
        setContentView(R.layout.activity_add_todo);
        Log.d("layout","after");

        mDescriptionET = findViewById(R.id.add_description);
        mAutocompleteCategoryView = findViewById(R.id.add_category);
        mSubmitBt = findViewById(R.id.submit);

       // Log.d("date check", " "+);

        mTodayBt = findViewById(R.id.today);
        mTomorrowBt = findViewById(R.id.tomorrow);
        mCustomDateBt = findViewById(R.id.custom_date_picker);
        mTimeSetTv = findViewById(R.id.time);

        mPlusOneHourBt = findViewById(R.id.plus_1hour);
        mPlusTwoHourBt = findViewById(R.id.plus2hour);
        mCustomTimeBt = findViewById(R.id.custom_time_picker);
        mTodoTableHelper = new ToDoTableHelper(this);
        mCategoryList.addAll(mTodoTableHelper.getCategories());
        if (mCategoryList.size() == 0)
        {
            mCategoryList.add("work");
            mCategoryList.add("personal");
        }
        ArrayAdapter<String> mAutoCompleteTxtAdapter = new ArrayAdapter<>(this,R.layout.auto_complete,mCategoryList);
        mAutocompleteCategoryView.setThreshold(1);
        mAutocompleteCategoryView.setAdapter(mAutoCompleteTxtAdapter);


        mSimpleDateFormatOfHourMinute = new SimpleDateFormat("yyyyMMddHHmm");


        mYear = lCalendar.get(Calendar.YEAR);
        mMonth = lCalendar.get(Calendar.MONTH);
        mDate = lCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = lCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = lCalendar.get(Calendar.MINUTE);


        mSelectedYear = mYear;
        mSelectedMonth = mMonth;
        mSelectedDate = mDate;
        mSelectedHour = mHour;
        mSelectedMinute = mMinute;





        mTempDate = String.valueOf(mDate);
        mTempMonth = String.valueOf(mMonth);
        mTempYear = String.valueOf(mYear);
        mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));
        mTempMin = String.valueOf(mMinute);

//
//
        mTimeStamp = Long.parseLong(mTempYear+mTempMonth+mTempDate+mHour+mMinute);
       // mTempDate = String.valueOf(new SimpleDateFormat("yyyyMMdd").getCalendar().getTime());
        // add value for category list

        Log.d("Hours"," "+Calendar.HOUR+1+" "+ Calendar.HOUR_OF_DAY+1);

        lCalendar.add(Calendar.HOUR,1);
        int lAmPm = lCalendar.get(Calendar.AM_PM);
        if (lAmPm == 0)
        {
            mAMorPM = "AM";
        }else {
            mAMorPM = "PM";
        }
        mPlusOneHourBt.setText((lCalendar.get(Calendar.HOUR)+":"+lCalendar.get(Calendar.MINUTE)+" "+mAMorPM));
        //mPlusOneHourBt.append(" "+mAMorPM);
        lCalendar.add(Calendar.HOUR,1);
        lAmPm = lCalendar.get(Calendar.AM_PM);
        if (lAmPm == 0)
        {
            mAMorPM = "AM";
        }else {
            mAMorPM = "PM";
        }
        mPlusTwoHourBt.setText((lCalendar.get(Calendar.HOUR)+":"+lCalendar.get(Calendar.MINUTE)+" "+mAMorPM));
        //mPlusTwoHourBt.append(" "+mAMorPM);

        mDescriptionET.requestFocus();


        mTempHour = String.valueOf(mDate);

       // ArrayAdapter<String> lCategoryAdapter = new ArrayAdapter<String>(this,)
        mAutocompleteCategoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        mPlusOneHourBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lCalendar = Calendar.getInstance();
                        lCalendar.add(Calendar.HOUR_OF_DAY,1);
                mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));
                mSelectedHour = lCalendar.get(Calendar.HOUR_OF_DAY);
                Log.d("Hours"," "+String.valueOf(lCalendar.get(Calendar.HOUR)));
                int lAmPm = lCalendar.get(Calendar.AM_PM);
                if (lAmPm == 0)
                {
                    mAMorPM = "AM";
                }else {
                    mAMorPM = "PM";
                }

                updateSelectedTime();
            }
        });

        mPlusTwoHourBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lCalendar = Calendar.getInstance();
                lCalendar.add(Calendar.HOUR_OF_DAY,2);
                mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));
                mSelectedHour = lCalendar.get(Calendar.HOUR_OF_DAY);
                Log.d("Hours"," "+String.valueOf(lCalendar.get(Calendar.HOUR)));

                int lAmPm = lCalendar.get(Calendar.AM_PM);
                if (lAmPm == 0)
                {
                    mAMorPM = "AM";
                }else {
                    mAMorPM = "PM";
                }
                updateSelectedTime();

            }
        });

        mTomorrowBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lCalendar = Calendar.getInstance();
                lCalendar.add(Calendar.DAY_OF_MONTH,1);
                mTempDate = String.valueOf(lCalendar.get(Calendar.DAY_OF_MONTH));
                mTempMonth = String.valueOf(lCalendar.get(Calendar.MONTH)+1);
                mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));
                mSelectedDate = lCalendar.get(Calendar.DAY_OF_MONTH);

                int lAmPm = lCalendar.get(Calendar.AM_PM);
                if (lAmPm == 0)
                {
                    mAMorPM = "AM";
                }else {
                    mAMorPM = "PM";
                }
                updateSelectedTime();
            }
        });

        mTodayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lCalendar = Calendar.getInstance();
                mTempDate = String.valueOf(lCalendar.get(Calendar.DAY_OF_MONTH));
                mSelectedDate = lCalendar.get(Calendar.DAY_OF_MONTH);
                mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));
                mTempMonth = String.valueOf(lCalendar.get(Calendar.MONTH)+1);

                int lAmPm = lCalendar.get(Calendar.AM_PM);
                if (lAmPm == 0)
                {
                    mAMorPM = "AM";
                }else {
                    mAMorPM = "PM";
                }
                updateSelectedTime();
            }
        });



        mCustomDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               DatePickerDialog lDatePickerDialog = new DatePickerDialog(AddToDoActivity.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       Log.d("Date picker"," "+year+month+dayOfMonth);

                       mTempDate = String.valueOf(dayOfMonth);
                       mTempMonth = String.valueOf(month+1);
                       mTempYear = String.valueOf(year);

                       mSelectedYear = year;
                       mSelectedMonth = month;
                       mSelectedDate = dayOfMonth;

                       updateSelectedTime();



                   }
               },mYear,lCalendar.get(Calendar.MONTH),mDate);
               lDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

               lDatePickerDialog.show();



            }

        });

        mCustomTimeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog lTimePickerDialog = new TimePickerDialog(AddToDoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Log.d("timepicker"," "+hourOfDay+minute);


                        mTempMin = String.valueOf(minute);

                        mSelectedHour = hourOfDay;
                        mSelectedMinute = minute;
                        lCalendar = Calendar.getInstance();
                        lCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        lCalendar.set(Calendar.MINUTE,minute);

                        mTempHour = String.valueOf(lCalendar.get(Calendar.HOUR));

                          int lAmPm = lCalendar.get(Calendar.AM_PM);
                        if (lAmPm == 0)
                        {
                            mAMorPM = "AM";
                        }else {
                            mAMorPM = "PM";
                        }
                        updateSelectedTime();

                    }
                },mHour,mMinute,false);
                lTimePickerDialog.show();



            }
        });
        mDescriptionVal = String.valueOf(mDescriptionET.getText());
        mCategoryVal = String.valueOf(mAutocompleteCategoryView.getText());


        mSubmitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for null values, and call the insertion method
                if(checkDataNotNull())
                {
                    mToDoData.setmDescription(String.valueOf(mDescriptionET.getText()));
                    mToDoData.setmCategory(String.valueOf(mAutocompleteCategoryView.getText()));
                    Calendar lCalendar = Calendar.getInstance();
                    lCalendar.set(mSelectedYear,mSelectedMonth,mSelectedDate,mSelectedHour,mSelectedMinute);
                    Log.d("deadline in ms"," "+lCalendar.getTimeInMillis());

                    mToDoData.setmDeadline(lCalendar.getTimeInMillis());
                   // Log.d("deadline","milli secs "+mToDoData.getmDeadline());
                    mToDoData.setmTimeStamp(System.currentTimeMillis());
                  // Log.d("timestamp","  "+Long.parseLong(mSimpleDateFormatOfHourMinute.format(new Date())));

                    //db Insertion
                    mInsertedRowID = mTodoTableHelper.SaveTodoTaskToDB(mToDoData);
                    mToDoData.setmID(mInsertedRowID);

                    Intent lIntent = new Intent(AddToDoActivity.this, AlarmReceiver.class);
                    lIntent.putExtra("TaskId",mInsertedRowID);

                    PendingIntent lPendingIntent = PendingIntent.getBroadcast(AddToDoActivity.this, (int) mInsertedRowID,lIntent,PendingIntent.FLAG_CANCEL_CURRENT);
                   // new AlarmManagerHelper().triggerAlarm(AddToDoActivity.this,mToDoData,lPendingIntent);
                    AlarmManager lAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    lAlarmManager.set(AlarmManager.RTC_WAKEUP,mToDoData.getmDeadline(),lPendingIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    }
            }
        });





   }


    /**
     *method to check if the values entered in description and category are not null.
     * @return if the data is entered on edit text
     */
    private boolean checkDataNotNull()
    {
        if (mDescriptionET.getText().toString().length() != 0 )
        {
            if (mAutocompleteCategoryView.getText().toString().length() != 0 )
            {
                return true;

            }
            else {
                Toast.makeText(this,"provide Category",Toast.LENGTH_SHORT).show();
                mAutocompleteCategoryView.requestFocus();
            }


        }else {
            Toast.makeText(this,"Enter description",Toast.LENGTH_SHORT).show();
            mDescriptionET.requestFocus();
        }

        return false;

    }

    /**
     *method to update values on UI when time selected
     */
    private void updateSelectedTime()
    {
        mTimeSetTv.setText(""+"Remind me at ");
        mTimeSetTv.append(""+mTempHour);//hours
        mTimeSetTv.append("."+mTempMin);//mins
        mTimeSetTv.append(" "+mAMorPM);//am/pm
        mTimeSetTv.append(" on "+mTempDate);//day
        mTimeSetTv.append("/"+mTempMonth);//month
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}
