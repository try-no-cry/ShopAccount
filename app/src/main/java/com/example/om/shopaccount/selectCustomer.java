package com.example.om.shopaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class selectCustomer extends AppCompatActivity {
//show the customers list

    ListView listOfCustomers;
    ArrayList<String> list=new ArrayList<>();
//    ArrayList<String> listID=new ArrayList<>();
    DatabaseReference databaseCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);

        Intent intent=new Intent();
        String uidOfShopkeeper=intent.getStringExtra("uidOfShopkeeper");



        listOfCustomers=findViewById(R.id.listOfCustomers);
        databaseCustomers= FirebaseDatabase.getInstance().getReference("Users");

        databaseCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot usersSnapshot:dataSnapshot.getChildren()){
                    if(usersSnapshot.getValue(NewUserInfoSignUp.class).getUserType().equals("customer")){
                        list.add(usersSnapshot.getValue(NewUserInfoSignUp.class).getName()+" "+usersSnapshot.getValue(NewUserInfoSignUp.class).getEmail());
//                        listID.add(usersSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(list.size()==0){
            Toast.makeText(this,"No customer is linked with your shop. ",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
//        list.add("Abhay Tiwari bldg.6");
//        list.add("Kulkarni bldg.5");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listOfCustomers.setAdapter(adapter);

        listOfCustomers.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                startActivity(new Intent(selectCustomer.this,MainActivity.class));
            }
        });

    }
}
