package com.example.root.eoh.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.root.eoh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Add_Task_Activity extends AppCompatActivity {
    CalendarView deadline;
    EditText name;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Button btn_Add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__task_);
        deadline=(CalendarView)findViewById(R.id.calendarView);
        name=(EditText)findViewById(R.id.et_task_name_added);
        btn_Add = (Button) findViewById(R.id.btn_add);
        final String date= String.valueOf(deadline.getDate());
        Calendar.getInstance();
        final String sub_date= String.valueOf(Calendar.DATE);


        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid().toString()).child("tasks");


        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String , String> map = new HashMap<>();
                map.put("task_name" , name.getText().toString());
                map.put("task_sub_date" , date);
                map.put("task_dead_line" , sub_date);
                reference.push().setValue(map);

            }
        });






    }
}
