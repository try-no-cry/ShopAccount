package com.example.om.shopaccount;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements list_main_activity_adapter.onDeleteClicked, ProductEntry.addMoreClicked {
CalendarView cvCalendar;
private int lastTop=0;
    ArrayList<single_list_listView_java_class> main_list=new ArrayList<>();
    list_main_activity_adapter myAdapterforMainActivity;
final static private int REQUEST_CODE=2;
ListView listViewTodaysData;
TextView tvTotalPriceMainLayoutBottom;
    DatabaseReference databaseAddedProducts=FirebaseDatabase.getInstance().getReference("AddedProducts");
private String day,monthName,yearName;
 private boolean EDITABLE=false;
String todayDate;
TextView tvdateOfToday;


public void parallax(final View v){
    final Rect rect=new Rect();
    v.getLocalVisibleRect(rect);

    if(rect.top!=lastTop){
        v.post(new Runnable() {
            @Override
            public void run() {
                v.setY((float)(rect.top/2));
            }
        });
    }


}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            cvCalendar=(CalendarView) findViewById(R.id.cvCalendar);
        }
        listViewTodaysData=findViewById(R.id.listViewTodaysData);
        tvTotalPriceMainLayoutBottom=findViewById(R.id.tvTotalPriceMainLayoutBottom);
        tvdateOfToday=findViewById(R.id.tvdateOfToday);

        Date c = Calendar.getInstance().getTime();


        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        String list[]=currentDateTimeString.split("-");
         day=list[0];
         switch(list[1]){
             case "Jan":monthName="1";break;
             case "Feb":monthName="2";break;
             case "Mar":monthName="3";break;
             case "Apr":monthName="4";break;
             case "May":monthName="5";break;
             case "Jun":monthName="6";break;
             case "Jul":monthName="7";break;
             case "Aug":monthName="8";break;
             case "Sep":monthName="9";break;
             case "Oct":monthName="10";break;
             case "Nov":monthName="11";break;
             case "Dec":monthName="12";break;

         }
         yearName=list[2];
         todayDate=day+"-"+monthName+"-"+yearName;
         tvdateOfToday.setText(todayDate);
        showListInMainActivity(todayDate);










//
//        listViewTodaysData.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
////                parallax(cvCalendar);
//                if(listViewTodaysData.getFirstVisiblePosition()==0){
//                    View firstChild=listViewTodaysData.getChildAt(0);
//                    int topY=0;
//                    if(firstChild!=null){
//                        topY=firstChild.getTop();
//                    }
//
//                    int headerTopY=cvCalendar.getTop();
//
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                parallax(cvCalendar);
//            }
//        });


        // perform setOnDateChangeListener event on CalendarView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
    // add code here
                day=String.valueOf(dayOfMonth);
                monthName=String.valueOf(month);
                yearName=String.valueOf(year);


                todayDate=day+"-"+monthName+"-"+yearName;
                tvdateOfToday.setText(todayDate);

                    showListInMainActivity(todayDate);

                if(EDITABLE) {
                    Intent intent = new Intent(getApplicationContext(), Alert_window.class);
                    intent.putExtra("day", dayOfMonth);
                    intent.putExtra("month", month);
                    intent.putExtra("year", year);
                    startActivityForResult(intent, REQUEST_CODE);
                }


                }
            });
        }
    }

    //after returning from another activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQUEST_CODE){
            //means we came from Alert_window activity
            if(resultCode==3){

            }
            else if(resultCode!=RESULT_CANCELED){

//                Bundle bundle=data.getExtras();
//                main_list = (ArrayList<single_list_listView_java_class>) bundle.getSerializable("buffer");
//
                showListInMainActivity(todayDate);
            }
         }

        super.onActivityResult(requestCode, resultCode, data);
    }




    private void showListInMainActivity(final String todayDate) {


        databaseAddedProducts.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                main_list.clear();
                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){


                    if(productSnapshot.getKey().toString().trim().equals(todayDate)){

                        for(DataSnapshot todaysProduct: productSnapshot.getChildren()) {
                            String name = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemName();
                            String price = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemPrice();
                            String quantity = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemQuantity();

                            main_list.add(new single_list_listView_java_class(name, price, quantity));

                        }
                        if(main_list.size()!=0){
                            myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, main_list);
                            listViewTodaysData.setAdapter(myAdapterforMainActivity);

                            //setting price total of each day
                            double price=0;
                            for(int i=0;i<main_list.size();i++){

                                price +=Double.parseDouble(main_list.get(i).getItemPrice())*Double.parseDouble(main_list.get(i).getItemQuantity());


                            }

                            tvTotalPriceMainLayoutBottom.setText((String.valueOf(price)));
                        }
                    }
                }

                if(main_list.size()==0){
                   ArrayList<single_list_listView_java_class> lis=new ArrayList<>();
                    lis.add(new single_list_listView_java_class("No Items Selected","","1"));
                    myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, lis);
                    listViewTodaysData.setAdapter(myAdapterforMainActivity);
                    tvTotalPriceMainLayoutBottom.setText("");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_layout, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.addNewProduct:addNewProduct(); break;
            case R.id.edit_mode: if(EDITABLE){
                                        EDITABLE=false;
                                        item.setIcon(R.drawable.edit_mode_off);
                                        Toast.makeText(this,"View Mode",Toast.LENGTH_SHORT).show();
                                                }
                                else {
                                         EDITABLE = true;
                                         item.setIcon(R.drawable.edit_mode_on);
                                         Toast.makeText(this,"Click a date to add product",Toast.LENGTH_SHORT).show();

                                                 }
                                break;
            case R.id.deleteProduct:Toast.makeText(this,"Product Deleted",Toast.LENGTH_SHORT).show(); break;
        }

        return true;

    }

    private void addNewProduct() {

        ProductEntry exampleDialog = new ProductEntry();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("Add New Product");
//
//
//        View v= LayoutInflater.from(this).inflate(R.layout.add_new_product_dialog,null,false);
//
//        builder.setView(v);
//
//        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(etProductName.getText().toString().trim().length()!=0 && etProductPrice.getText().toString().trim().length()!=0) {
//                    String id = databaseProducts.push().getKey();
//                    single_list_item_Class_alert var = new single_list_item_Class_alert(etProductName.getText().toString().trim(), etProductPrice.getText().toString().trim());
//                    databaseProducts.child(id).setValue(var);
//                }
//                else Toast.makeText(getApplicationContext(),"Please fill the inputs ",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setCancelable(true);
//        builder.show();



    }


    @Override
    public void onDeleteClickedListener(final int index) {
        if(index!=0 || main_list.size()!=1)
        {
            double cp = Double.parseDouble(tvTotalPriceMainLayoutBottom.getText().toString());
            cp -= Double.parseDouble(main_list.get(index).getItemPrice());
            tvTotalPriceMainLayoutBottom.setText(String.valueOf(cp));
        }
//
        ArrayList<single_list_listView_java_class> lis;

        Log.d("array", main_list.toString());

        if(main_list.size()!=0) {

            final String name=main_list.get(index).getItemName().toString().trim();
            final String quantity=main_list.get(index).getItemQuantity().toString().trim();
            databaseAddedProducts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot datesSnapshot:dataSnapshot.getChildren()){
                        for(DataSnapshot productSnapshot: datesSnapshot.getChildren()){
                            if(productSnapshot.getValue(single_list_listView_java_class.class).getItemName()==name &&
                                    productSnapshot.getValue(single_list_listView_java_class.class).getItemQuantity()==quantity){
                                productSnapshot.getRef().removeValue();
                                main_list.remove(index);
                                myAdapterforMainActivity.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



//
//            lis=new ArrayList<>(main_list);
//            main_list.clear();
//            main_list.addAll(lis);
//            myAdapterforMainActivity = new list_main_activity_adapter(this, main_list);
//            listViewTodaysData.setAdapter(myAdapterforMainActivity);



        }else{
            tvTotalPriceMainLayoutBottom.setText("");
            lis=new ArrayList<>();
            lis.add(new single_list_listView_java_class("No Items Selected","","1"));
            myAdapterforMainActivity = new list_main_activity_adapter(this, lis);
            listViewTodaysData.setAdapter(myAdapterforMainActivity);
        }

    }


    @Override
    public void addMoreClickedListener() {
        addNewProduct();
    }
}







//









