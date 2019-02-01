package com.example.root.eoh.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.eoh.MyData;
import com.example.root.eoh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_Activity extends AppCompatActivity {
    String name;
    String profile_url;
    String rate;
    List<String> skills;
    List<String> tasks;
    TextView tv_name,tv_rate;
    LinearLayout lv_skills;
    LinearLayout lv_tasks;
    ArrayAdapter adapter_skills;
    ArrayAdapter adapter_tasks;
    FloatingActionButton add_task;
    EditText txtTask;
    CircleImageView profileIMG;

    DatabaseReference reference;
    CalendarView calendarView;
    GraphView graphViewEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_);

        final String id = getIntent().getStringExtra("id").toString();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        tv_name = (TextView) findViewById(R.id.tv_employee_name);
        tv_rate = (TextView) findViewById(R.id.tv_rate);
        lv_skills = (LinearLayout)findViewById(R.id.lv_skills);
        lv_tasks = (LinearLayout)findViewById(R.id.lv_tasks);
        add_task = (FloatingActionButton) findViewById(R.id.fb_add_task);
        graphViewEmployee = (GraphView) findViewById(R.id.graph_employee);
        profileIMG = (CircleImageView) findViewById(R.id.iv_profile);

        skills = new ArrayList<>();
        tasks = new ArrayList<>();




        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tv_name.setText(dataSnapshot.child("name").getValue().toString());
                tv_rate.setText(dataSnapshot.child("rat").getValue().toString());
                Picasso.with(Employee_Activity.this).load(dataSnapshot.child("img").getValue().toString()).into(profileIMG);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // getting skills

        reference.child(id).child("skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                skills.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    addNewMessage(snapshot.getKey().toString() + " " + snapshot.getValue().toString() , lv_skills);

                }

                /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Employee_Activity.this , android.R.layout.simple_list_item_1 , skills);
                lv_skills.setAdapter(arrayAdapter);
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog , null);
                txtTask = (EditText) v.findViewById(R.id.txt_task);
                calendarView = (CalendarView) v.findViewById(R.id.calender);

                AlertDialog.Builder builder = new AlertDialog.Builder(Employee_Activity.this);
                builder.setView(v);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Date date = Calendar.getInstance().getTime();
                        HashMap<String , String> map = new HashMap<>();
                        map.put("name" , txtTask.getText().toString());
                        map.put("sub_date" , String.valueOf(date));
                        map.put("dead_line" , String.valueOf(calendarView.getDate()));

                        reference.child(id).child("tasks").push().setValue(map);




                    }
                });
                builder.show();




            }
        });


        // getting tasks

        reference.child(id).child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    addNewMessage(snapshot.child("name").getValue().toString() + "\n" + snapshot.child("dead_line").getValue().toString() , lv_tasks);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });


        //init Graph
        graphViewEmployee.setTitle("Productivity");

        graphViewEmployee.getViewport().setYAxisBoundsManual(true);
        graphViewEmployee.getViewport().setMinY(0);
        graphViewEmployee.getViewport().setMaxY(10);

        graphViewEmployee.getViewport().setXAxisBoundsManual(true);
        graphViewEmployee.getViewport().setMinX(0);
        graphViewEmployee.getViewport().setMaxX(12);

        graphViewEmployee.getViewport().setScalable(true);
        graphViewEmployee.getViewport().setScalableY(true);

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
        graphViewEmployee.addSeries(series1);






    }



    public void addNewMessage(String message , LinearLayout linearLayout){
        TextView textView = new TextView(Employee_Activity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setTextSize(18);
        textView.setPadding(20,20,20,20);

        textView.setLayoutParams(lp);

        textView.setBackgroundResource(R.drawable.message_reciever);
        lp.gravity = Gravity.LEFT;
        textView.setTextColor(Color.parseColor("#ffffff"));

        linearLayout.addView(textView);

        }




}
