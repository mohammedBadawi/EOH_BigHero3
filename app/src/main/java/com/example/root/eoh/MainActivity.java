package com.example.root.eoh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.eoh.user.Employee_Activity;
import com.example.root.eoh.user.Login;
import com.example.root.eoh.user.User;
import com.example.root.eoh.user.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private DatabaseReference reference2 , reference3;
    private String uid;
    private FirebaseUser user;
    private String MyIMG;
    private ListView listView;
    private List<User> list;
    private ProgressDialog progressDialog;
    private EditText txtSearch;
    private ImageButton btnSearch;
    FloatingActionButton fab;
    final List<User> usersSearch = new ArrayList<>();
    final List<String> rats = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_my_employee);
        txtSearch = (EditText) findViewById(R.id.txt_search);
        btnSearch = (ImageButton) findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Search(txtSearch.getText().toString());

            }
        });


        /*txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Search(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Search(String.valueOf(s));
            }
        });

        */



        list = new ArrayList<>();

        // init firebase

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference3 = FirebaseDatabase.getInstance().getReference().child("my_employee");



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , Statistics.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);








    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MainActivity.this , Employee_Activity.class);
            intent.putExtra("id" , mAuth.getCurrentUser().getUid().toString());
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            mAuth.signOut();
            startActivity(new Intent(MainActivity.this , Login.class));
            finish();

        } else if (id == R.id.nav_manage) {
            // All Managers
            startActivity(new Intent(MainActivity.this , Leaders.class));


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();

        user = mAuth.getCurrentUser();

        if(user == null){

            startActivity(new Intent(MainActivity.this , Login.class));
            finish();

        }else {

            uid = mAuth.getCurrentUser().getUid().toString();
            reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    MyData.img = dataSnapshot.child("img").getValue().toString();
                    MyData.name = dataSnapshot.child("name").getValue().toString();
                    MyData.myid = dataSnapshot.child("id").getValue().toString();
                    MyData.email = dataSnapshot.child("email").getValue().toString();
                    MyData.typeuser = dataSnapshot.child("user_type").getValue().toString();

                    MyIMG = dataSnapshot.child("img").getValue().toString();

                    if (dataSnapshot.child("user_type").getValue().toString().equals("leader")){
                        fab.setBackgroundResource(R.drawable.ic_graphic_eq_black_24dp);
                        Toast.makeText(MainActivity.this, "Leader", Toast.LENGTH_SHORT).show();
                    }else {
                        fab.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {



                }
            });


            // Getting My employee
            // getting my employees

            reference3.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren() == false){
                        Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    list.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        String id = snapshot.child("id").getValue().toString();
                        reference2 = FirebaseDatabase.getInstance().getReference().child("users").child(id);

                        reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User u = new User();
                                u.setId(dataSnapshot.child("id").getValue().toString());
                                u.setName(dataSnapshot.child("name").getValue().toString());
                                u.setImg(dataSnapshot.child("img").getValue().toString());

                                list.add(u);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    UsersAdapter adapter = new UsersAdapter(MainActivity.this , list);
                    listView.setAdapter(adapter);
                    listView.refreshDrawableState();
                    progressDialog.dismiss();

                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(MainActivity.this , Employee_Activity.class);
                    intent.putExtra("id" , list.get(i).getId());
                    startActivity(intent);

                }
            });




        }





    }




    public void Search(final String skill){



        reference3.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren() == false){
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                list.clear();

                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String id = snapshot.child("id").getValue().toString();

                    reference2 = FirebaseDatabase.getInstance().getReference().child("users").child(id);

                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child("skills").hasChild(skill)){
                                Toast.makeText(MainActivity.this, dataSnapshot.child("skills").child(skill).getValue().toString(), Toast.LENGTH_SHORT).show();
                                User u = new User();
                                String img = dataSnapshot.child("img").getValue().toString();
                                String name = dataSnapshot.child("name").getValue().toString();
                                u.setId(id);
                                u.setImg(img);
                                u.setName(name);
                                usersSearch.add(u);
                                rats.add(dataSnapshot.child("skills").child(skill).getValue().toString());


                            }else {
                                //Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                SortUsers(usersSearch , rats);

                UsersAdapter adapter = new UsersAdapter(MainActivity.this , usersSearch);
                listView.setAdapter(adapter);
                listView.refreshDrawableState();

                progressDialog.dismiss();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

    }



    private void SortUsers(List<User> usersSearch , List<String> rats) {

        for (int i = 0; i < rats.size() - 1; i++){

            int s1 = Integer.parseInt(rats.get(i + 1));
            int s2 = Integer.parseInt(rats.get(i));

            if (s2 < s1){
                Collections.swap(usersSearch , i , i + 1);
            }

        }


    }



}
