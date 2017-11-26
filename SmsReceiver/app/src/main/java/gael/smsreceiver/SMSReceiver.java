package gael.smsreceiver;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_RECEIVE_SMS)){
            Bundle bundle = intent.getExtras();
            Object[] pdus = null;
            if(bundle != null){
                pdus = (Object[])bundle.get("pdus");
            }

            final SmsMessage[] messages = new SmsMessage[pdus.length];

            for(int i = 0; i< pdus.length;i++){
                final String messageBody = messages[i].getMessageBody();
                final String expediteur = messages[i].getDisplayOriginatingAddress();

                Toast.makeText(context, "Expediteur : "+expediteur, Toast.LENGTH_LONG).show();
                Toast.makeText(context, "Message : "+messageBody, Toast.LENGTH_LONG).show();
            }
        }
    }
}
