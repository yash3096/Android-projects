package dj.in.database;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS;

public class ChartsActivity extends AppCompatActivity {

    private ArrayList<BarEntry> entries;
    private ArrayList<Entry> super_entries;
    private BarData bardata;
    private ScatterData scatterData;
    private ArrayList<String> labels;
    private BarChart bchart;
    private ScatterDataSet scatterDataSet;
    private BarDataSet bardataset;
    private ScatterChart scatterChart;
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_image_menu,menu);
        return true;
    }
    public void onSave(MenuItem menuItem)
    {
        boolean status=bchart.saveToGallery("mychart"+System.currentTimeMillis(), 50);
        Toast.makeText(this,"Image saved to gallery:\t"+status,Toast.LENGTH_LONG).show();
        status=bchart.saveToPath("mychart"+System.currentTimeMillis(),"");
        Toast.makeText(this,"Image saved to sd card:\t"+status,Toast.LENGTH_LONG).show();
    }

  */  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        bchart= (BarChart) findViewById(R.id.c_bar);
        scatterChart= (ScatterChart) findViewById(R.id.c_scanner_plot);
        entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        super_entries=new ArrayList<Entry>();
        super_entries.add(entries.get(0));
        super_entries.add(entries.get(1));
        super_entries.add(entries.get(2));
        super_entries.add(entries.get(3));
        super_entries.add(entries.get(4));
        super_entries.add(entries.get(5));
        bardataset = new BarDataSet(entries, "no. of Calls");
        scatterDataSet=new ScatterDataSet(super_entries,"no. of Calls");
        labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        bardata = new BarData(labels, bardataset);
        scatterData=new ScatterData(labels,scatterDataSet);
        bchart.setData(bardata);
        scatterChart.setData(scatterData);
        bchart.animateXY(2000, 2000);
        scatterChart.animateXY(2000,2000);
        bchart.setDescription("number of times Alice called Bob");
        scatterChart.setDescription("number of times Alice called Bob");
        bardataset.setColors(COLORFUL_COLORS);
        scatterDataSet.setColors(COLORFUL_COLORS);
//        LimitLine line = new LimitLine(10f);

    }
}
