package in.neebal.firstandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompletedReciever extends BroadcastReceiver {
    public BootCompletedReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"BOOT COMPLETED",Toast.LENGTH_SHORT).show();
      Intent intent1=new Intent(context,DownloadIntentService.class);
        context.startService(intent1);
    }
}
