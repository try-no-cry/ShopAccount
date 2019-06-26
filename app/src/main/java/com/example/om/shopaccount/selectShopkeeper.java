package com.example.om.shopaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class selectShopkeeper extends AppCompatActivity {
//show the shopkeepers list
 ListView listOfShopkeepers;
 DatabaseReference databaseUsers,databaseShopkeepers;

 ArrayList<String> list=new ArrayList<>();
 ArrayList<String> listID=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shopkeeper);
        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");
        databaseShopkeepers=FirebaseDatabase.getInstance().getReference("Shopkeepers");
        listOfShopkeepers=findViewById(R.id.listOfShopkeepers);

        final Intent intent=new Intent();
        final String uid=getIntent().getStringExtra("uid"); //grtting id of the user who came here


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot usersSnapshot:dataSnapshot.getChildren()){
                    if(usersSnapshot.getValue(NewUserInfoSignUp.class).getUserType().equals("shopkeeper")){
                        list.add(usersSnapshot.getValue(NewUserInfoSignUp.class).getName()+" "+usersSnapshot.getValue(NewUserInfoSignUp.class).getEmail());
                        listID.add(usersSnapshot.getKey());
                    }
                }

                ArrayAdapter adapter=new ArrayAdapter(selectShopkeeper.this,android.R.layout.simple_list_item_1,list);
                listOfShopkeepers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(list.size()==0){
            Toast.makeText(this,"No Shopkeeper is registered in the App. ",Toast.LENGTH_LONG).show();
//            finish();

        }



        listOfShopkeepers.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //when a shopkeeper is selected by this customer
                //the customer is added to that shopkeepers node


                     Intent intent=new Intent(selectShopkeeper.this,MainActivity.class);
                    intent.putExtra("sUid",listID.get(i));
                    intent.putExtra("uid",uid);

                    startActivity(intent);
            }
        });
    }
}
