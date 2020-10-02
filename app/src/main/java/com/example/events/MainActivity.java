package com.example.events;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
//        long calID = getCalendarId(MainActivity.this, "rayavarapuvikram1@gmail.com");
//        Log.i("WHHSHNFDSFFGR", Long.toString(calID));

    }

//    private void syncwithgooglecalendar2() {
//        Account[] accounts = AccountManager.get(MainActivity.this).getAccountsByType(
//                "com.google");
//        String accountName = "";
//        for (Account acc : accounts) {
//            Log.d(">>>>>>>", "Name: " + acc.name + "-----Type: " + acc.type);
//            accountName = acc.name;
//        }
//        long calID = getCalendarId(MainActivity.this, accountName);
//    //....
//    }
//
//    private long getCalendarId(Activity activity, String MY_ACCOUNT_NAME) {
//        String[] projection = new String[]{BaseColumns._ID};
//        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ?";
//        String[] selArgs = new String[]{MY_ACCOUNT_NAME};
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
////            return TODO;
//            return -111;
//        }
//        Cursor cursor = activity.getContentResolver().query(
//                CalendarContract.Calendars.CONTENT_URI, projection, selection, selArgs, null);
//        if (cursor.moveToFirst()) {
//            return cursor.getLong(0);
//        }
//        return -1;
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                ContentResolver cr = this.getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(CalendarContract.Events.TITLE, "Event to be added");
                cv.put(CalendarContract.Events.DESCRIPTION, "this is the first event");
                cv.put(CalendarContract.Events.EVENT_LOCATION, "Nitdgp room no 12");
                cv.put(CalendarContract.Events.DTSTART, Calendar.getInstance().getTimeInMillis());
                cv.put(CalendarContract.Events.DTEND, Calendar.getInstance().getTimeInMillis() + 60 * 60 * 1000);
                cv.put(CalendarContract.Events.CALENDAR_ID, 2);
                cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);
                syncCalendar(getAc,"1");
                Toast.makeText(this, "Task is added", Toast.LENGTH_LONG).show();
                break;

        }
    }

//    You can sync your event after adding it by this function,It's worked for me(in API 8 and later):

    public static void syncCalendar(Context context, String calendarId) {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        values.put(CalendarContract.Calendars.VISIBLE, 1);

        cr.update(
                ContentUris.withAppendedId(getCalendarUri(),
                        Long.parseLong(calendarId)), values, null, null);
    }
}