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
    ArrayList<String> listID=new ArrayList<>();
    ArrayList<String> listNewID=new ArrayList<>();
    DatabaseReference databaseShopkeepers,databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);


        final String uidOfShopkeeper=getIntent().getStringExtra("sUid");



        listOfCustomers=findViewById(R.id.listOfCustomers);
        databaseShopkeepers= FirebaseDatabase.getInstance().getReference("Shopkeepers");
        databaseUsers= FirebaseDatabase.getInstance().getReference("Users");

        databaseShopkeepers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                for(DataSnapshot usersSnapshot:dataSnapshot.getChildren()){
//
//                    if(usersSnapshot.getValue(NewUserInfoSignUp.class).getUserType().equals("customer")){
//                        list.add(usersSnapshot.getValue(NewUserInfoSignUp.class).getName()+" "+usersSnapshot.getValue(NewUserInfoSignUp.class).getEmail());
////                        listID.add(usersSnapshot.getKey());
//                    }
//                }
                for(DataSnapshot shopkeepersSnapshot: dataSnapshot.getChildren()){
                    if(shopkeepersSnapshot.getKey().toString().equals(uidOfShopkeeper)){
                        for(DataSnapshot customersSnap: shopkeepersSnapshot.getChildren()){
                            if(!customersSnap.getKey().toString().equals("Products")){
//                                list.add(customersSnap.getKey().toString());
                                listID.add(customersSnap.getKey().toString());
                            }
                        }

                        if(listID.size()!=0) {

                            databaseUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot users:dataSnapshot.getChildren()){
                                    for(int i=0;i<listID.size();i++){
                                        if(users.getKey().toString().equals(listID.get(i))){
                                            list.add(users.getValue(NewUserInfoSignUp.class).getName() +"  "+ users.getValue(NewUserInfoSignUp.class).getEmail());
                                            listNewID.add(listID.get(i));
                                            listID.remove(i);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }





                        break;
                    }

                }

                if(listID.size()==0){
                    Toast.makeText(selectCustomer.this,"No customer is linked with your shop. ",Toast.LENGTH_LONG).show();
//                    finish();
//                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






//        list.add("Abhay Tiwari bldg.6");
//        list.add("Kulkarni bldg.5");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listOfCustomers.setAdapter(adapter);

        listOfCustomers.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(selectCustomer.this,MainActivity.class);
                intent.putExtra("uid",listNewID.get(i));
                intent.putExtra("sUid",uidOfShopkeeper);

                startActivity(new Intent(selectCustomer.this,MainActivity.class));
            }
        });

    }
}
