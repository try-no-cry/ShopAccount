package com.example.om.shopaccount;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements list_main_activity_adapter.onDeleteClicked {
CalendarView cvCalendar;
    ArrayList<single_list_listView_java_class> main_list;
    list_main_activity_adapter myAdapterforMainActivity;
final static private int REQUEST_CODE=2;
ListView listViewTodaysData;
TextView tvTotalPriceMainLayoutBottom;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            cvCalendar=(CalendarView) findViewById(R.id.cvCalendar);
        }
        listViewTodaysData=findViewById(R.id.listViewTodaysData);
        tvTotalPriceMainLayoutBottom=findViewById(R.id.tvTotalPriceMainLayoutBottom);













        // perform setOnDateChangeListener event on CalendarView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
    // add code here

                    Intent intent=new Intent(getApplicationContext(),Alert_window.class);
                    intent.putExtra("day",dayOfMonth);
                    intent.putExtra("month",month);
                    intent.putExtra("year",year);
                   startActivityForResult(intent,REQUEST_CODE);


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



                Bundle bundle=data.getExtras();
                main_list = (ArrayList<single_list_listView_java_class>) bundle.getSerializable("buffer");
            if(main_list.size()!=0){
               myAdapterforMainActivity = new list_main_activity_adapter(this, main_list);
                listViewTodaysData.setAdapter(myAdapterforMainActivity);
            int i;
            double price=0;
            for(i=0;i<main_list.size();i++){

                price +=Double.parseDouble(main_list.get(i).getItemPrice())*Double.parseDouble(main_list.get(i).getItemQuantity());


            }

            tvTotalPriceMainLayoutBottom.setText((String.valueOf(price)));
            }
            }


        }

        else{


        }




        super.onActivityResult(requestCode, resultCode, data);
    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_layout, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {


        Toast.makeText(getApplicationContext(),"New item added",Toast.LENGTH_SHORT).show();
        return true;

    }

    @Override
    public void onDeleteClickedListener(int index) {
        if(index!=0 || main_list.size()!=1)
        {
            double cp = Double.parseDouble(tvTotalPriceMainLayoutBottom.getText().toString());
            cp -= Double.parseDouble(main_list.get(index).getItemPrice());
            tvTotalPriceMainLayoutBottom.setText(String.valueOf(cp));
        }
        main_list.remove(index);
        ArrayList<single_list_listView_java_class> lis;

        Log.d("array", main_list.toString());

        if(main_list.size()!=0) {

            lis=new ArrayList<>(main_list);
            main_list.clear();
            main_list.addAll(lis);
            myAdapterforMainActivity = new list_main_activity_adapter(this, main_list);
            listViewTodaysData.setAdapter(myAdapterforMainActivity);



        }else{
            tvTotalPriceMainLayoutBottom.setText("");
            lis=new ArrayList<>();
            lis.add(new single_list_listView_java_class("No Items Selected","","1"));
            myAdapterforMainActivity = new list_main_activity_adapter(this, lis);
            listViewTodaysData.setAdapter(myAdapterforMainActivity);
        }

    }




    }







//









