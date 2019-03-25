package com.example.docpat.Patient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.docpat.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HeartRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        GraphView graph = (GraphView) findViewById(R.id.graphHeartRate);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 65),
                new DataPoint(1, 58),
                new DataPoint(2, 75),
                new DataPoint(3, 78),
                new DataPoint(4, 76),
                new DataPoint(5, 78),
                new DataPoint(6, 80),
                new DataPoint(7, 88),
                new DataPoint(8, 82),
                new DataPoint(9, 80)
        });
        graph.addSeries(series);
    }
}
