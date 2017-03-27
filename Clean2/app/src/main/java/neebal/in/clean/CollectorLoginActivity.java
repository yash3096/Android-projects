package neebal.in.clean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CollectorLoginActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    Spinner arena;
    ArrayList<String> arenaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_login);
        arena= (Spinner) findViewById(R.id.spinner_arena);
        arenaList=new ArrayList<String>();
        arenaList.add("");
        arenaList.add("Borivali");
        arenaList.add("Kandivali");
        arenaList.add("Bandra");
        arenaList.add("Ghatkopar");
        adapter=new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,arenaList);
        arena.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loggedin_menu, menu);
        return true;
    }
    public void onSubmit(MenuItem menuItem)
    {
        Intent intent=new Intent(this,DonorListActivity.class);
        intent.putExtra("Arena",arena.getSelectedItem().toString());
        startActivity(intent);
    }
}
