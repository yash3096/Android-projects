package neebal.in.clean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import core.Customer;
import db.CustomerdbAdapter;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,username,password,address,number;
    CustomerdbAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        adapter=new CustomerdbAdapter(this);
        name= (EditText) findViewById(R.id.et_name);
        username= (EditText) findViewById(R.id.et_username);
        password= (EditText) findViewById(R.id.et_password);
        address= (EditText) findViewById(R.id.et_address);
        number= (EditText) findViewById(R.id.et_number);
    }
    public void onRegister(View view)
    {
        //save the data to database
        String n=name.getText().toString();
        String u=username.getText().toString();
        String p=password.getText().toString();
        String add=address.getText().toString();
        String num=number.getText().toString();
        if(!(n.equals("")||u.equals("")||p.equals("")||add.equals("")||num.equals("")))
        {
            if(adapter.insertDonor(u,p,n,add,num))
            {
                Toast.makeText(this,"Thank You For Registering With GreenCiti",Toast.LENGTH_LONG).show();
                name.setText("");
                username.setText("");
                password.setText("");
                address.setText("");
                Intent intent=new Intent(this,LoginActivity.class);
                finish();
            }
            else {
                Toast.makeText(this,"Sorry Some Error Occurred Please Fill Again",Toast.LENGTH_LONG).show();
            }
        }
        else Toast.makeText(this,"All data must be filled compulsorily",Toast.LENGTH_LONG).show();
    }
}
