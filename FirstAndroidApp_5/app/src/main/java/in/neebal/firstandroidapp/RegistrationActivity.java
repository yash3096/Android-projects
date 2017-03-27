package in.neebal.firstandroidapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import in.neebal.firstandroidapp.core.Constants;

public class RegistrationActivity extends AppCompatActivity {
    private boolean threadrun;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private RadioButton Male,Female;
    private Spinner districtsSpinner;
    private ArrayList<String> districts;
    private Switch recieveSMSSwitch;
    SharedPreferences sharedPreferences;
     RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_rel);
        //get reference from view elements
        recieveSMSSwitch=(Switch)findViewById(R.id.smsalert);
        usernameEditText=(EditText)findViewById(R.id.et_username);
        passwordEditText=(EditText)findViewById(R.id.et_password);
        Male=(RadioButton)findViewById(R.id.gender_male);
        Female=(RadioButton)findViewById(R.id.gender_female);
        districtsSpinner=(Spinner)findViewById(R.id.spn_districts);
        //craete data set
        districts=new ArrayList<String>();
        districts.add("");
        districts.add("Otisburg");
        districts.add("Burnley");
        //create adapter and associate the data set to the adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,districts);
        //set adapter on the spinner
        districtsSpinner.setAdapter(adapter);
        sharedPreferences=getSharedPreferences("MyAppPrefs",MODE_PRIVATE);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_registration);
        //get Intent method gets the intent which called d activity and get String extra
        // gets d extra sent by previous activity with INTENT
        usernameEditText.setText(getIntent().getStringExtra(Constants.EXTRA_USERNAME));

      }
    public void onRegister(MenuItem mi)
    {
        //get values from view elements
        String u=usernameEditText.getText().toString();
        String p=passwordEditText.getText().toString();
        String g=null;
        String a=(String)districtsSpinner.getSelectedItem();
        String t=null;
        if(recieveSMSSwitch.isChecked())
        {
            //switch is on
            t="Yes";
        }
        else
        t="No";
        if (Male.isChecked())
            g="Male";
        else
        g="Female";
        g="Username: "+u+" Password: "+p+"Gender: "+g+"District: "+a+" Alert: "+t;
        Toast toast=Toast.makeText(this,g,Toast.LENGTH_LONG);
        toast.show();
        usernameEditText.setText("");
        passwordEditText.setText("");
        districtsSpinner.setSelection(0);
        recieveSMSSwitch.setChecked(false);
        Male.setChecked(true);
    }
    @Override
    protected void onStart() {
        super.onStart();
        int colorToApply = sharedPreferences.getInt("Color", Color.WHITE);
        relativeLayout.setBackgroundColor(colorToApply);
        /*threadrun=false;
       // System.out.println("On Start of RegistrationActivity");
        Thread t=new Thread(new EvenJob());
        t.start();*/
    }

    class EvenJob implements Runnable
    {
        //long running operation
        @Override

        public void run() {
            /*for(int i=0;i<=100;i+=2)
            {if(threadrun)
                break;
                System.out.print(i+"\t");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("On Resume of RegistrationActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        threadrun=true;
        System.out.println("On Pause of RegistrationActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("On Stop of RegistrationActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       System.out.println("On Destroy of RegistrationActivity");
    }
    public void onContacts(MenuItem mi)
    {
        Intent intent=new Intent(this,SuperHeroesContactsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_registration, menu);
        return true;
    }

    public void onThoughts(MenuItem mi)
    {
        Intent intent=new Intent(this,ThoughtActivity.class);
        startActivity(intent);
    }
    public void onSettings(MenuItem view)
    {
        Intent in=new Intent(this,SettingsActivity.class);
        startActivity(in);
    }
    public void onAdd(MenuItem view)
    {
        Intent intent=new Intent(this,NewFindingsActivity.class);
        startActivity(intent);
    }
}
