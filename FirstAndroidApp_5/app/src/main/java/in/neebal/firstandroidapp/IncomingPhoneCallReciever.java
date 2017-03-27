package in.neebal.firstandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.telephony.TelephonyManager;
import android.view.inputmethod.CompletionInfo;
import android.widget.Toast;

import javax.net.ssl.ManagerFactoryParameters;

public class IncomingPhoneCallReciever extends BroadcastReceiver {
    public IncomingPhoneCallReciever() {
    }
    //in case of call every time phone state changes onRecieve gets called like when we hangup pickup etc
    //so check the state by getting extra from intent using key TelephonyManager and if state is equal to incoming call do procedure
    @Override
    public void onReceive(Context context, Intent intent) {
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        //more than one action can be subscribed by the reciever but we need to check action to do task
        //check action by intent.getAction() if reciever takes more than 1 action
        //  and then to get data like phonenumber getExtra from intent which is usually string
        if(state.equals(TelephonyManager.EXTRA_INCOMING_NUMBER))
        {
            String phone=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context,"Incoming call from "+phone,Toast.LENGTH_LONG).show();
        }
    }
}
