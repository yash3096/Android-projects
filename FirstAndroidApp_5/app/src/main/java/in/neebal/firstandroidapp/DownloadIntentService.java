package in.neebal.firstandroidapp;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by root on 9/1/16.
 */
public class DownloadIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * name Used to name the worker thread, important only for debugging.
     */
    //No arg required by android studio to create object of Intent Service coz
    // it doesnt know we will create an overloaded constructor
    //name is the name of thread required by the super class consstructor
    public DownloadIntentService() {
        super("Download thread");
    }
        //this method gets called when startService called using intent
    //it will byDefault run on seperATE WORKER thread not on UI thread
    @Override
    protected void onHandleIntent(Intent intent) {

        for(int i=0;i<100;i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Downloaded" + (i + 1) + "%");
        }
    }
}
