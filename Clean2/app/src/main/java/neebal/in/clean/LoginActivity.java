package neebal.in.clean;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import core.Customer;
import db.CustomerdbAdapter;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private CustomerdbAdapter adapter;
    private Customer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= (EditText) findViewById(R.id.et_username);
        password= (EditText) findViewById(R.id.et_password);
        adapter= new CustomerdbAdapter(this);
    }
    public void onLogin(View view)
    {
         if((c=adapter.OnLoginAttempt(username.getText().toString().trim(),password.getText().toString().trim()))==null)
      {
          Toast.makeText(this,"Invalid Username or password",Toast.LENGTH_LONG).show();
          username.setText("");
          password.setText("");
      }
        else
         {
             Intent intent=new Intent(this,LoggedInActivity.class);
             intent.putExtra("name",c.getName());
             intent.putExtra("number",c.getNumber());
             startActivity(intent);
         }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }
    public void onRegister(MenuItem menuItem)
    {
    startActivity(new Intent(this,RegistrationActivity.class));
    }
}
