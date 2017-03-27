package in.neebal.firstandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ThoughtActivity extends AppCompatActivity {

    private ArrayList<String> thoughts;
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private RelativeLayout relativeLayout;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onStart() {
        super.onStart();

        //System.out.println("On Start of ThoughtsActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
       // System.out.println("On Resume of ThoughtsActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
       // System.out.println("On Pause of ThoughtsActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
       // System.out.println("On Stop of ThoughtsActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.out.println("On Destroy of ThoughtsActivity");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought);
        thoughts=new ArrayList<String>();
        thoughts.add("Give a mask and he will become his true self");
        thoughts.add("why so serious?");
        thoughts.add("We stopped checking for monsters under our bed when we realised they were inside us");
        thoughts.add("I have learned that everone by side is not on our side");
        thoughts.add("Give a mask and he will become his true self");
        thoughts.add("why so serious?");
        thoughts.add("We stopped checking for monsters under our bed when we realised they were inside us");
        thoughts.add("I have learned that everone by side is not on our side");
        thoughts.add("Give a mask and he will become his true self");
        thoughts.add("why so serious?");
        thoughts.add("We stopped checking for monsters under our bed when we realised they were inside us");
        thoughts.add("I have learned that everone by side is not on our side");
        thoughts.add("Give a mask and he will become his true self");
        thoughts.add("why so serious?");
        thoughts.add("We stopped checking for monsters under our bed when we realised they were inside us");
        thoughts.add("I have learned that everone by side is not on our side");
        adapter=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,thoughts);
        listview=(ListView)findViewById(R.id.lv_thoughts);
        listview.setAdapter(adapter);
        sharedPreferences=getSharedPreferences("MyAppPrefs",MODE_PRIVATE);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_thoughts);

        int colorToApply = sharedPreferences.getInt("Color", Color.WHITE);
        relativeLayout.setBackgroundColor(colorToApply);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_camera,menu);
        return true;
    }
    public void onCameraClick(MenuItem menuItem)
    {
        Intent intent=new Intent(this,PictureActivity.class);
        startActivity(intent);
    }

}
