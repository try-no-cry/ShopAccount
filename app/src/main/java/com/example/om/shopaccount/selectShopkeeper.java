package com.example.om.shopaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class selectShopkeeper extends AppCompatActivity {
//show the shopkeepers list
 ListView listOfShopkeepers;
 ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shopkeeper);

        listOfShopkeepers=findViewById(R.id.listOfShopkeepers);


        list.add("Kirana Store");
        list.add("Medical Store");

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listOfShopkeepers.setAdapter(adapter);

        listOfShopkeepers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity for this particular shopkeeper
            }
        });
    }
}
