package neebal.in.clean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingPhoneCallReceiver extends BroadcastReceiver {
    public IncomingPhoneCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       /* Toast.makeText(context,"Charger connected",Toast.LENGTH_LONG).show();
       */ // an Intent broadcast.
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if(state.equals(TelephonyManager.EXTRA_INCOMING_NUMBER))
        {
            String phone=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context,"Incoming Phone Call from "+phone,Toast.LENGTH_LONG).show();
            System.out.println("Incoming Phone Call from "+phone);
        }
       }
}
