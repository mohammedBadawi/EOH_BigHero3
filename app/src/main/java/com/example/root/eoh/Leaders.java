package com.example.root.eoh;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.root.eoh.user.Employee_Activity;
import com.example.root.eoh.user.User;
import com.example.root.eoh.user.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaders extends AppCompatActivity {


    private ListView listView;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String uid;
    private List<User> user;
    private DatabaseReference referenceEmp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);

        listView = (ListView) findViewById(R.id.list_leaders);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        referenceEmp = FirebaseDatabase.getInstance().getReference().child("my_employee");

        uid = mAuth.getCurrentUser().getUid().toString();
        user = new ArrayList<>();


        ///


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    User u = new User();
                    u.setType(snapshot.child("user_type").getValue().toString());

                    if (snapshot.child("user_type").getValue().toString().equals("leader")){
                        u.setName(snapshot.child("name").getValue().toString());
                        u.setId(snapshot.child("id").getValue().toString());
                        u.setImg(snapshot.child("img").getValue().toString());
                        user.add(u);
                    }

                }


                UsersAdapter adapter = new UsersAdapter(Leaders.this , user);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                User u = user.get(i);
                referenceEmp.child(u.getId()).child(uid).child("id").setValue(uid);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final User u = user.get(i);

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.feed_back , null);
                final EditText txtFeed = (EditText) v.findViewById(R.id.txt_feed);

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Leaders.this);
                builder.setView(v);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        HashMap<String , String> map = new HashMap<>();
                        map.put("message" , txtFeed.getText().toString());
                        reference.child(u.getId()).child("feedback").push().setValue(map);

                    }
                });
                builder.show();

                return true;
            }
        });

    }
}