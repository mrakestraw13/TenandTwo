package com.ringer.miles.tenandtwo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

@TargetApi(18)
public class MainActivity extends Activity implements LocationListener{

    //private SensorManager mSensorManager;
    private float speed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       LocationManager mSensorManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mSensorManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);

        this.onLocationChanged(null);
        checkSpeed(speed);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {

        TextView txt = (TextView)this.findViewById(R.id.textView);
        if(location == null){
            txt.setText("-.- m/s");
        }else{
            float nCurrentSpeed = location.getSpeed();
            txt.setText(nCurrentSpeed + " m/s");
            speed = nCurrentSpeed;

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void highSpeedNotification(){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Speed threshold reached.")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Speed threshold reached.")
                .setContentText("You are going over 20m/s text and call have been disabled")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
    private void checkSpeed(float s){
        if(s > 20) highSpeedNotification();
    }
}