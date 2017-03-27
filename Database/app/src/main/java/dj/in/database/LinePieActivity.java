package dj.in.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LinePieActivity extends AppCompatActivity {
    private LineChart lineChart;
    private PieChart pieChart;
    private ArrayList<Entry> entries;
    private LineDataSet lineDataSet;
    private PieDataSet pieDataSet;
    private LineData lineData;
    private PieData pieData;
    private ArrayList<String> labels;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_graph,menu);
        return true;
    }
    public void onNext(MenuItem menuItem)
    {
     Intent intent = new Intent(this,ChartsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_pie);
        lineChart= (LineChart) findViewById(R.id.c_line);
        pieChart= (PieChart) findViewById(R.id.c_pie);
        entries=new ArrayList<Entry>();
        entries.add(new Entry(4f,0));
        entries.add(new Entry(8f,1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f,4));
        entries.add(new Entry(9f,5));
        labels = new ArrayList<String>();
        lineDataSet=new LineDataSet(entries,"no. of calls");
        pieDataSet=new PieDataSet(entries,"no. of calls");
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        lineData=new LineData(labels,lineDataSet);
        pieData=new PieData(labels,pieDataSet);
        lineChart.setData(lineData);
        pieChart.setData(pieData);
        lineChart.setDescription("number of times Alice called Bob");
        pieChart.setDescription("number of times Alice called Bob");
        lineChart.animateXY(1000, 1500);
        pieChart.animateXY(1500, 1000);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
    }
}
