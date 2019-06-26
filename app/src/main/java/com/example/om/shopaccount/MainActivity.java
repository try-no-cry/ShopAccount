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
    DatabaseReference databaseAddedProducts=FirebaseDatabase.getInstance().getReference("Shopkeepers");
    volatile int check=0;


    private String day,monthName,yearName;
 private boolean EDITABLE=false;
String todayDate;
TextView tvdateOfToday;
    String sUid,uid;


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



         sUid=getIntent().getStringExtra("shopkeepersID");
         uid=getIntent().getStringExtra("uid");
String aiseh=uid;

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
                monthName=String.valueOf(month+1);
                yearName=String.valueOf(year);


                todayDate=day+"-"+monthName+"-"+yearName;
                tvdateOfToday.setText(todayDate);

                    showListInMainActivity(todayDate);

                if(EDITABLE) {
                    Intent intent = new Intent(getApplicationContext(), Alert_window.class);
                    intent.putExtra("day", dayOfMonth);
                    intent.putExtra("month", month+1);
                    intent.putExtra("year", year);
                    intent.putExtra("uid",uid);// this is uid of the user
                    intent.putExtra("sUid",sUid);// this is uid of the user
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
                    for(DataSnapshot shopkeepersSnapshot: dataSnapshot.getChildren()) {

                        for (DataSnapshot customersSnapshot : shopkeepersSnapshot.getChildren()) {

                            if(customersSnapshot.getKey().toString().equals(uid)) {
                                    int countCHild= (int) customersSnapshot.getChildrenCount();
                                    int im=0;
                                for(DataSnapshot dateSnapshot : customersSnapshot.getChildren()) {
                                    im++;
                                    if(im==countCHild){
                                        check=1;
                                    }

                                    String keyss = dateSnapshot.getKey().toString().trim();
                                    String aisehi = keyss;
                                    boolean ans= keyss.equals(todayDate);
                                    if (ans) {

                                        for (DataSnapshot todaysProduct : dateSnapshot.getChildren()) {
                                            String name = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemName();
                                            String price = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemPrice();
                                            String quantity = Objects.requireNonNull(todaysProduct.getValue(single_list_listView_java_class.class)).getItemQuantity();

                                            main_list.add(new single_list_listView_java_class(name, price, quantity));

                                        }
                                        if (main_list.size() != 0) {
                                            myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, main_list);
                                            listViewTodaysData.setAdapter(myAdapterforMainActivity);

                                            //setting price total of each day
                                            double price = 0;
                                            for (int i = 0; i < main_list.size(); i++) {

                                                price += Double.parseDouble(main_list.get(i).getItemPrice()) * Double.parseDouble(main_list.get(i).getItemQuantity());


                                            }

                                            tvTotalPriceMainLayoutBottom.setText((String.valueOf(price)));
                                        }
    //                                    break;
                                    }
                                }
                                if(main_list.size()==0 && check==1){
                                    ArrayList<single_list_listView_java_class> lis=new ArrayList<>();
                                    lis.add(new single_list_listView_java_class("No Items Selected","","1"));
                                     myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, lis);
                                     listViewTodaysData.setAdapter(myAdapterforMainActivity);
                                     tvTotalPriceMainLayoutBottom.setText("");
                                }
                                check=0;
                                break;
                            }
                        }


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//        if(main_list.size()==0){
//            ArrayList<single_list_listView_java_class> lis=new ArrayList<>();
//            lis.add(new single_list_listView_java_class("No Items Selected","","1"));
//            myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, lis);
//            listViewTodaysData.setAdapter(myAdapterforMainActivity);
//            tvTotalPriceMainLayoutBottom.setText("");
//        }
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
        final ArrayList<single_list_listView_java_class> lis;

        Log.d("array", main_list.toString());

        if(main_list.size()!=0) {

            final String name=main_list.get(index).getItemName().toString().trim();
            final String quantity=main_list.get(index).getItemQuantity().toString().trim();

            databaseAddedProducts.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot shopkeepersSnapshot:dataSnapshot.getChildren()){

                        for(DataSnapshot customersSnapshot: shopkeepersSnapshot.getChildren()){

                            if(customersSnapshot.getKey().equals(uid)){
                            for(DataSnapshot datesSnapshot: customersSnapshot.getChildren()) {

                                for (DataSnapshot productSnapshot : datesSnapshot.getChildren()) {

                                    String paakaka = Objects.requireNonNull(productSnapshot.getValue(single_list_listView_java_class.class)).getItemName();
                                    boolean aisehgi = paakaka.equals(name) && productSnapshot.getValue(single_list_listView_java_class.class).getItemQuantity().equals(quantity);
                                    if (aisehgi) {

                                        productSnapshot.getRef().removeValue();
                                        main_list.remove(index);
                                        myAdapterforMainActivity.notifyDataSetChanged();

                                        if(main_list.size()==0){
                                            tvTotalPriceMainLayoutBottom.setText("");
                                          ArrayList<single_list_listView_java_class>  lis=new ArrayList<>();
                                            lis.add(new single_list_listView_java_class("No Items Selected","","1"));
                                            myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, lis);
                                            listViewTodaysData.setAdapter(myAdapterforMainActivity);
                                        }
//                                        if(main_list.size()!=0){
//
//                                            ArrayList<single_list_listView_java_class> lis=new ArrayList<>();
//                                            lis=main_list;
//                                            main_list=lis;
//                                        myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this,main_list);
//                                        listViewTodaysData.setAdapter(myAdapterforMainActivity);
//                                        }
//                                        else{
//
//                                        tvTotalPriceMainLayoutBottom.setText("");
//
//                                        lis.add(new single_list_listView_java_class("No Items Selected","","1"));
//                                        myAdapterforMainActivity = new list_main_activity_adapter(MainActivity.this, lis);
//                                        listViewTodaysData.setAdapter(myAdapterforMainActivity);
//                                       }
                                        return;
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









