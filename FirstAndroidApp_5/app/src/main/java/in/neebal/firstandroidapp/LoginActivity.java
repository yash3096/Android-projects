package in.neebal.firstandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import in.neebal.firstandroidapp.core.Constants;
import in.neebal.firstandroidapp.db.KnightDbAdapter;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //searches for id in the same layout file
        usernameEditText= (EditText) findViewById(R.id.et_username);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.login, menu);
        return true;
    }
    public void onRegister(MenuItem menuItem)
    {
        Intent intent=new Intent(this,RegistrationActivity.class);
        String username=usernameEditText.getText().toString();
        //sending WXTRA to the activity intended to be launched along with INTENT object
        intent.putExtra(Constants.EXTRA_USERNAME,username);
        startActivity(intent);
    }

}
