package com.example.om.shopaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class selectCustomer extends AppCompatActivity {
//show the customers list
    ListView listOfCustomers;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);

        listOfCustomers=findViewById(R.id.listOfCustomers);

        list.add("Abhay Tiwari bldg.6");
        list.add("Kulkarni bldg.5");

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listOfCustomers.setAdapter(adapter);

        listOfCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity for this particular customer
            }
        });


    }
}
