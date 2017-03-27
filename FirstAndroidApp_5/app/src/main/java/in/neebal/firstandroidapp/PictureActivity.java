package in.neebal.firstandroidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final int REQUEST_CODE_CAMERA=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        //get id of imageview from the layout
        imageView= (ImageView) findViewById(R.id.iv_picture);
    }
    public void onCamera(View view)
    {
        Intent intent=new Intent();
        //Action here is not a constant in INTENT CLASS
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    //OnActivityResult is the method inherited from Context class called by Intent Resolver on call of startActivityForResult
    //request code is unique code which activity returns while returning result to caller activity
    // to identify which activity sent result in case of more than one activity ke liye check result code
    //getExtras send all the extras used when v dont know what extra to retrieve
    // Result code is predefined constants as a check whether result was produced or back button pressed instead
    //BitMap is the most basic unit in which u can represent an image
    // get takes extra String key as parameter from activity returning result i.e "data" which is returned from camera in this case
    //thus after getExtra calling "get" gives particular Extra thus v need key for that Extra i.e "data" in this case
    //get returns a Object which can be typecasted to Bitmap
    //third parameter is the data returned by activity to caller activity
    //setImageBitmap sets the image on the ImageView with retrieved object
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            if(requestCode==REQUEST_CODE_CAMERA)
            {
                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
