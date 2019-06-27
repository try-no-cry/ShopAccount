package com.example.om.shopaccount;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DeleteProduct extends AppCompatActivity implements single_del_list_adapter.onDeleteClicked{
ListView listOfItemsToDelete;
ArrayList<single_list_item_Class_alert> list=new ArrayList<>();
single_del_list_adapter myAdapter;
String uid,sUid;
DatabaseReference   databaseShopkeeper= FirebaseDatabase.getInstance().getReference("Shopkeepers");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        uid=getIntent().getStringExtra("uid");
        sUid=getIntent().getStringExtra("sUid");

        listOfItemsToDelete=findViewById(R.id.listOFItemsToDelete);


        databaseShopkeeper.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for( DataSnapshot shopkeepersSnapshot: dataSnapshot.getChildren() ){

                    if (shopkeepersSnapshot.getKey().toString().equals(sUid)) {
                        for (DataSnapshot products:shopkeepersSnapshot.getChildren()) {
                            if (products.getKey().toString().equals("Products")) {

                                for (DataSnapshot actualProd : products.getChildren()) {

                                    String name = Objects.requireNonNull(actualProd.getValue(single_list_item_Class_alert.class)).getItemName();
                                    String price = Objects.requireNonNull(actualProd.getValue(single_list_item_Class_alert.class)).getItemPrice();

                                    list.add(new single_list_item_Class_alert(name, price));

                                }
                                if(list.size()!=0){
                                myAdapter=new single_del_list_adapter(DeleteProduct.this,list);
                                listOfItemsToDelete.setAdapter(myAdapter);



                                break;}
                            }

                        }
                        if(list.size()==0) {
                            ArrayList<single_list_item_Class_alert> les = new ArrayList<>();
                            les.add(new single_list_item_Class_alert("No Products Added", ""));
                            myAdapter = new single_del_list_adapter(DeleteProduct.this, les);
                            listOfItemsToDelete.setAdapter(myAdapter);
                        }

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

    @Override
    public void onDeleteClickedListener(final int index) {

final String pName=list.get(index).getItemName();
final String pPrice=list.get(index).getItemPrice();

        databaseShopkeeper.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for( DataSnapshot shopkeepersSnapshot: dataSnapshot.getChildren() ){

                    if (shopkeepersSnapshot.getKey().toString().equals(sUid)) {
                        for (DataSnapshot products:shopkeepersSnapshot.getChildren()) {
                            if (products.getKey().toString().equals("Products")) {

                                for (DataSnapshot actualProd : products.getChildren()) {

                                    if(actualProd.getValue(single_list_item_Class_alert.class).getItemName().equals(pName) && actualProd.getValue(single_list_item_Class_alert.class).getItemPrice().equals(pPrice)){

                                        actualProd.getRef().removeValue();
                                        list.remove(index);
                                        myAdapter.notifyDataSetChanged();
                                    }

                                }
                            }
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
