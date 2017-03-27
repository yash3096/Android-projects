package dj.in.database;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {
    private ArrayList<Entry> comp1list,comp2list;
    private ArrayList<RadarDataSet> dataSets;
    private ArrayList<String> labels;
    private RadarChart chart;
    private  RadarData data;
   // private ScatterChart chart1;
    private RadarDataSet dataset_comp1,dataSet_comp2;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_graph,menu);
        return true;
    }
    public void onNext(MenuItem menuItem)
    {
        Intent intent = new Intent(this,LinePieActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        chart= (RadarChart) findViewById(R.id.rc_radar);
        comp1list=new ArrayList<Entry>();
        comp2list=new ArrayList<Entry>();
        comp1list.add(new Entry(4f,0));
        comp1list.add(new Entry(5f,1));
        comp1list.add(new Entry(2f,2));
        comp1list.add(new Entry(7f,3));
        comp1list.add(new Entry(6f,4));
        comp1list.add(new Entry(5f,5));
        comp2list.add(new Entry(1f,0));
        comp2list.add(new Entry(5f,1));
        comp2list.add(new Entry(6f,2));
        comp2list.add(new Entry(3f,3));
        comp2list.add(new Entry(4f,4));
        comp2list.add(new Entry(8f,5));
        dataset_comp1=new RadarDataSet(comp1list,"A");
        dataSet_comp2=new RadarDataSet(comp2list,"B");
        dataset_comp1.setColor(Color.GREEN);
        dataSet_comp2.setColor(Color.RED);
        dataset_comp1.setDrawFilled(true);
        dataSet_comp2.setDrawFilled(true);
        labels=new ArrayList<String >();
        labels.add("Communication");
        labels.add("Technical Knowledge");
        labels.add("Team Player");
        labels.add("Meeting Deadlines");
        labels.add("Problem Solving");
        labels.add("Punctuality");
        dataSets=new ArrayList<RadarDataSet>();
        dataSets.add(dataset_comp1);
        dataSets.add(dataSet_comp2);
        data=new RadarData(labels,dataSets);
        chart.setData(data);
        chart.setDescription("Employee-Skill Analysis (scale of 1-10)");
        chart.setWebLineWidth(0.5f);
        chart.setDescriptionColor(Color.RED);
        chart.invalidate();
        chart.animateXY(2000,2000);
        chart.animate();
    }
}
