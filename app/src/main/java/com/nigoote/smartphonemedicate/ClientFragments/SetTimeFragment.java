package com.nigoote.smartphonemedicate.ClientFragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nigoote.smartphonemedicate.R;

import java.util.Calendar;

public class SetTimeFragment extends Fragment {
// this fragment helps the user to set time of getting pills per day by reminder(alarm)
    TimePicker timePicker;
    Button btnset;
    public SetTimeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_time,container,false);
        timePicker = view.findViewById(R.id.timePicker);
        btnset = view.findViewById(R.id.buttonSetAlarm);

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >=23) {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(),
                            timePicker.getMinute(),
                            0
                    );
                }else{
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute(),
                            0
                    );
                }

                setAlarm(calendar.getTimeInMillis());
            }

            private void setAlarm(long timeInMillis) {
                AlarmManager alarmManager = (AlarmManager) getLayoutInflater().getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getActivity(),MyAlarm.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
                Toast.makeText(getActivity(), "Time is Set", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }




}
