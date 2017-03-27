package neebal.in.clean;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import db.CustomerdbAdapter;

public class LoggedInActivity extends AppCompatActivity {
    CustomerdbAdapter adapter;
    EditText quantity,address;
    TextView welcome;
    RadioButton donate,sell;
    Spinner arenas,typesOfWaste;
    ArrayList<String> arenaList,types;
    ArrayAdapter<String> adapter1,adapter2;
    String name,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        welcome= (TextView) findViewById(R.id.tv_welcome);
        Intent intent=getIntent();
        address= (EditText) findViewById(R.id.et_address);
        name=intent.getStringExtra("name");
        number=intent.getStringExtra("number");
        welcome.setText(welcome.getText()+"\t"+name);
        adapter=new CustomerdbAdapter(this);
        types=new ArrayList<String>();
        arenaList=new ArrayList<String>();
        arenaList.add("");
        arenaList.add("Borivali");
        arenaList.add("Kandivali");
        arenaList.add("Bandra");
        arenaList.add("Ghatkopar");
        types=new ArrayList<String>();
        types.add("Dry");
        types.add("Wet");
        types.add("Recyclable");

        welcome= (TextView) findViewById(R.id.tv_welcome);
        quantity= (EditText) findViewById(R.id.et_quantity);
        sell= (RadioButton) findViewById(R.id.rb_sell);
        donate= (RadioButton) findViewById(R.id.rb_donate);
        typesOfWaste= (Spinner) findViewById(R.id.spinner_typeofwaste);
        arenas= (Spinner) findViewById(R.id.spinner_arena);
      adapter2=new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,arenaList);
      adapter1=new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,types);
        arenas.setAdapter(adapter2);
        typesOfWaste.setAdapter(adapter1);
    }
//retrieve name and points from table
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loggedin_menu, menu);
        return true;
    }
    public void onSubmit(MenuItem menuItem)
    {
        String arena=arenas.getSelectedItem().toString();
        String typesofwaste=typesOfWaste.getSelectedItem().toString();
       double x=0;
        try {
            x = Double.parseDouble(quantity.getText().toString());
       }catch(NumberFormatException e){}
        if(arena.equals("")||typesofwaste.equals(""))
            Toast.makeText(this,"All fields except quantity mandatory",Toast.LENGTH_LONG).show();
        else// if(adapter.insertWaste(typesofwaste,arena,address.getText().toString(),x,number,name))
        {
            ContentResolver resolver=getContentResolver();
            ContentValues values=new ContentValues();
            values.put(CustomerdbAdapter.COL_NAME,name);
            values.put(CustomerdbAdapter.COL_NUMBER,number);
            values.put(CustomerdbAdapter.COL_ARENA,arena);
            values.put(CustomerdbAdapter.COL_ADDRESS,address.getText().toString());
            values.put(CustomerdbAdapter.COL_WASTETYPE,typesofwaste);
            values.put(CustomerdbAdapter.COL_QUANTITY,Double.parseDouble(quantity.getText().toString()));
            String uri="content://neebal.in.clean.CleanDbConnectionHelper/Waste";
          Uri uriReturned=  resolver.insert(Uri.parse(uri),values);
            if(!donate.isChecked())
            {
                if(typesofwaste.equals("Recyclable"))
                    Toast.makeText(this,"You will recieve "+x*40+" Rupees",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"You will recieve "+x*30+" Rupees",Toast.LENGTH_LONG).show();
            }
            donate.setChecked(true);
            quantity.setText("");
            arenas.setSelection(0);
            typesOfWaste.setSelection(0);
            finish();
        }
    }
}
