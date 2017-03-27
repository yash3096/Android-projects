package neebal.in.clean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
    }
    public void onDonor(View view)
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
    public void onAbout(MenuItem menuItem)
    {
        Intent intent=new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void onCollector(View view)
    {
        startActivity(new Intent(this,CollectorLoginActivity.class));
    }
}
