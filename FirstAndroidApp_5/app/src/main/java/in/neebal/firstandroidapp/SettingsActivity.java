package in.neebal.firstandroidapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ArrayList<String> colors;
    private Spinner colorSpinner;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        colors=new ArrayList<String>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");

        colorSpinner=(Spinner)findViewById(R.id.spin_Color);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,
                android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,colors);
        colorSpinner.setAdapter(adapter);
        relativeLayout= (RelativeLayout) findViewById(R.id.rl_color);
        //get reference of the shared prefenrence
        sharedPreferences=getSharedPreferences("MyAppPrefs",MODE_PRIVATE);
        //get any color that was saved or white if not found and set as the background
        int colorToApply=sharedPreferences.getInt("Color",Color.WHITE);
        relativeLayout.setBackgroundColor(colorToApply);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu_settings, menu);
        return true;
    }
    public void onSave(MenuItem menu)
    {
        String color=(String)colorSpinner.getSelectedItem();
      //  System.out.println(color);
        int appliedColor=-1;
        if(color.equals("Red"))
            appliedColor= Color.RED;
        else if(color.equals("Green"))
            appliedColor=Color.GREEN;
        else
            appliedColor=Color.BLUE;
        //apply the color toi the background
        relativeLayout.setBackgroundColor(appliedColor);
        //save the color in the Shared Preferance
        SharedPreferences.Editor editor=sharedPreferences.edit();
        // method putInt taking key and value as parameter to store in Shared preference
        editor.putInt("Color", appliedColor);
        editor.commit();
    }
}
