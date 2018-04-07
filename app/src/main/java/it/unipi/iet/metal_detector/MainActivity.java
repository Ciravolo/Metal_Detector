package it.unipi.iet.metal_detector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor sensor;
    private TextView value;
    private Button button;
    private int flag;
    private LineChart lineChart;
    private LineData data;
    private ArrayList <Entry> entries = new ArrayList<>();
    private int time;
    private LineDataSet dataset = new LineDataSet(entries, "Induzione magnetica");
    private ArrayList <String> labels = new ArrayList<>();
    private Vibrator v;

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lineChart = (LineChart) findViewById(R.id.mango);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(sensor.TYPE_MAGNETIC_FIELD);
        value = (TextView) findViewById(R.id.textView);
        flag = 0;
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                flag = 1 - flag;
                showDetails(flag);
            }
        });
        time = 0;
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // get values for each axes X,Y,Z
            float magX = event.values[0];
            float magY = event.values[1];
            float magZ = event.values[2];

            int magnitude = (int) Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
            if(magnitude >= 150) {
                v.vibrate(500);
            }
            entries.add(new Entry(magnitude, time));
            labels.add(new String(Integer.toString(time)));
            data = new LineData(labels, dataset);
            try {
                lineChart.setData(data);
            } catch (Exception e){System.out.println(e.getMessage());}
             // set value on the screen
            value.setText(magnitude + " \u00B5Tesla");
            ++time;
        }
    }

    private void showDetails(int i) {
        if (i == 1) {
            onResume();
        }
        else {
            onPause();
        }
    }
}
