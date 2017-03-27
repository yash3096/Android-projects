package in.neebal.firstandroidapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {
    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        System.out.println("On create of DownloadService");
    }
//start service calls onStartCommand....just like activity ka onStart
    // sercvice happens on UI thread so v wont be able to interact with feedback edittext
    // to do so make sysout statement run on background thread creating new thread
    //so even if app gets killed service keep running
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //long running dummy download operation
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i++)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Downloaded" + (i+1) +"%");
                }
                //to destroy the service implicitly called calls onDestroy
                stopSelf();
            }
        }).start();

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("On destroy of Downloadservice");
    }
}
