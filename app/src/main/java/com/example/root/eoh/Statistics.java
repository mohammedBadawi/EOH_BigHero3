package com.example.root.eoh;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Statistics extends AppCompatActivity {


    GraphView graphView1 , graphView2 , graphView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        graphView1 = (GraphView) findViewById(R.id.graph1);
        graphView2 = (GraphView) findViewById(R.id.graph2);
        graphView3 = (GraphView) findViewById(R.id.graph3);

        graphView1.setTitle("Productivity");
        graphView2.setTitle("Employees Efficiency");
        graphView3.setTitle("Productivity");


        graphView1.getViewport().setYAxisBoundsManual(true);
        graphView1.getViewport().setMinY(0);
        graphView1.getViewport().setMaxY(10);

        graphView1.getViewport().setXAxisBoundsManual(true);
        graphView1.getViewport().setMinX(0);
        graphView1.getViewport().setMaxX(12);

        // enable scaling and scrolling
        graphView1.getViewport().setScalable(true);
        graphView1.getViewport().setScalableY(true);


        graphView2.getViewport().setYAxisBoundsManual(true);
        graphView2.getViewport().setMinY(0);
        graphView2.getViewport().setMaxY(10);

        graphView2.getViewport().setXAxisBoundsManual(true);
        graphView2.getViewport().setMinX(0);
        graphView2.getViewport().setMaxX(12);

        // enable scaling and scrolling
        graphView2.getViewport().setScalable(true);
        graphView2.getViewport().setScalableY(true);



        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(1, 1),
                new DataPoint(2, 5),
                new DataPoint(3, 3),
                new DataPoint(4, 4),
                new DataPoint(5, 7),
                new DataPoint(6, 1),
                new DataPoint(7, 8),
                new DataPoint(8, 7),
                new DataPoint(9, 4),
                new DataPoint(10, 1),
                new DataPoint(11, 3),
                new DataPoint(12, 6),

        });
        graphView1.addSeries(series1);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(1, 1),
                new DataPoint(2, 5),
                new DataPoint(3, 6),
                new DataPoint(4, 4),
                new DataPoint(5, 3),
                new DataPoint(6, 2),
                new DataPoint(7, 1),
                new DataPoint(8, 2),
                new DataPoint(9, 3),
                new DataPoint(10, 4),
                new DataPoint(11, 5),
                new DataPoint(12, 6),

        });
        graphView2.addSeries(series2);


        BarGraphSeries<DataPoint> series3 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graphView3.addSeries(series3);

        // styling
        series3.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series3.setSpacing(50);

        // draw values on top
        series3.setDrawValuesOnTop(true);
        series3.setValuesOnTopColor(Color.RED);
        //series.setValuesOnTopSize(50);


    }
}
