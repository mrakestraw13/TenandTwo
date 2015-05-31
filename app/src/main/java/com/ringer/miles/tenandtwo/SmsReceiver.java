package com.ringer.miles.tenandtwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.app.PendingIntent;
import android.widget.Toast;

/**
 * Created by Miles on 5/26/2015.
 */
public class SmsReceiver extends BroadcastReceiver {

    private String TAG = SmsReceiver.class.getSimpleName();
    public static String number = "";
    public static String sms = "";
    //public static String reply = "The person you are trying to reach is currently driving," +
          //  "They will get back to you ass soon as possible.";

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs;

        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                number = msgs[i].getOriginatingAddress();
                sms = msgs[i].getMessageBody();
            }


                PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent("SMS_Sent"), 0);
                SmsManager sm = SmsManager.getDefault();

                sm.sendTextMessage(number, null, MainActivity.userText.toString(), pi, null);

        }

    }
}
