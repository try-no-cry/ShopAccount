package com.example.om.shopaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

public class Alert_window extends Activity implements AutoCompleteItemAdapter.onItemClicked,AutoCompleteItemAdapter.onDeleteClicked, list_adapter_alert.onDeleteClicked,list_adapter_alert.onQuatityChanged {
    TextView tvTitle;
    TextView tvTotalCost;
    AutoCompleteTextView autoCompleteTv;
    ListView alertListView;

    ArrayList<single_list_item_Class_alert> list;
    ArrayList<single_list_listView_java_class> listForListView;

    Button btnAdd,btnCancel;
    list_adapter_alert myadapterForList;
    AutoCompleteItemAdapter myAdapter;
    private final static int CANCEL=3;

    DatabaseReference databaseReference; 


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_window);

        tvTitle=findViewById(R.id.tvTitle);
        tvTotalCost=findViewById(R.id.tvTotalCost);
        tvTotalCost.setText("₹0");


        autoCompleteTv=findViewById(R.id.autoCompleteTv);
        alertListView=findViewById(R.id.alertListView);


        btnAdd=findViewById(R.id.btnAdd);
        btnCancel=findViewById(R.id.btnCancel);





        list=new ArrayList<>();
        listForListView=new ArrayList<>();

        list.add(new single_list_item_Class_alert("Gokul milk","30"));
        list.add(new single_list_item_Class_alert("Amul milk","23"));
        list.add(new single_list_item_Class_alert("Hajmola ","1"));
        list.add(new single_list_item_Class_alert("Candy","1"));
        list.add(new single_list_item_Class_alert("Mahananda","20"));
        list.add(new single_list_item_Class_alert("joke","0"));

        listForListView.add(new single_list_listView_java_class( "Product", "Price","1"));



         myAdapter=new AutoCompleteItemAdapter(this,list);
        autoCompleteTv.setAdapter(myAdapter);




        myadapterForList=new list_adapter_alert(this,listForListView);
        alertListView.setAdapter(myadapterForList);





        Bundle extras=getIntent().getExtras();

        int day=extras.getInt("day");
        int month=extras.getInt("month");
        int year=extras.getInt("year");
        tvTitle.setText(day+"/"+month+"/"+year);


//        DisplayMetrics displayMetrics=new DisplayMetrics();
//
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//        int height=displayMetrics.heightPixels;
//        int width=displayMetrics.widthPixels;

       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               Intent intent=new Intent();

               Bundle bundle=new Bundle();


               bundle.putSerializable("buffer", listForListView);
               intent.putExtras(bundle);
               setResult(2,intent);
               finish();//finishing activity
           }
       });

       btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             Intent intent=new Intent();

             setResult(3,intent);
                finish();
           }
       });

        autoCompleteTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    autoCompleteTv.showDropDown();

                }

            }
        });

        autoCompleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTv.showDropDown();
            }
        });


//        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

    }
/**********************copy paste for focus related**************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    autoCompleteTv.dismissDropDown(); //new added line by ABHAY

                    /*Some more linew by Abhay*/


                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    /**********************copy paste for focus related***********ends*************/

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onItemClickedListener(int index) {
            listForListView.add(new single_list_listView_java_class( list.get(index).getItemName(), list.get(index).getItemPrice(),"1"));
//           double currentCost=Double.parseDouble(tvTotalCost.getText().toString().trim());
//           double newC=currentCost + Double.parseDouble(listForListView.get(index).getItemPrice());
//           tvTotalCost.setText(String.valueOf(nC));
        myadapterForList.notifyDataSetChanged();
        closeKeyboard();

        Toast.makeText(getApplicationContext(), list.get(index).getItemName(),Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void closeKeyboard() {
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm= (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
            autoCompleteTv.dismissDropDown();
        }





    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onDeleteClickedListener(int index) {
//        double currentCost=Double.parseDouble(tvTotalCost.getText().toString().trim());
//        double newC=currentCost - Double.parseDouble(listForListView.get(index).getItemPrice())*Double.parseDouble(listForListView.get(index).getItemQuantity());
//        tvTotalCost.setText(String.valueOf(nC));
        listForListView.remove(index);

        openKeyboard();

        //closeKeyboard();



        myadapterForList.notifyDataSetChanged();


    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    void openKeyboard() {
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm= (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInputFromWindow(
                    view.getWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);

        }



    }


    @Override
    public void onQuatityChangedClickListener(int value) {
        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();

    }
}
