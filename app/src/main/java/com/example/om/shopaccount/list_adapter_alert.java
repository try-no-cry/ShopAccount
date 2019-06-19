package com.example.om.shopaccount;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class list_adapter_alert extends BaseAdapter {

    ArrayList<single_list_listView_java_class> list;
    Context context;
boolean first=true;
    onDeleteClicked varDeleteClicked;

    public interface onDeleteClicked{
        public void onDeleteClickedListener(int index);
    }

    onQuatityChanged varQuantityChanged;
    public interface onQuatityChanged{
        public void onQuatityChangedClickListener(int value);
    }


    public list_adapter_alert(Context context,ArrayList<single_list_listView_java_class>  list){


        this.context=context;
        this.list=list;
        varDeleteClicked=(onDeleteClicked)context; //idk what is this but it works with this
        varQuantityChanged=(onQuatityChanged)context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final single_list_listView_java_class item=list.get(i);
        if(view == null)
            view= LayoutInflater.from(context).inflate(R.layout.single_list_item_alertbox, null,false);
        TextView tvItemName_alert,tvItemPrice_alert;
        ImageButton ibDeleteBtn_alert;
        final EditText etQuantity;

        tvItemName_alert=view.findViewById(R.id.tvItemName_alert);
        tvItemPrice_alert=view.findViewById(R.id.tvItemPrice_alert);
        ibDeleteBtn_alert=view.findViewById(R.id.ibDeleteBtn_alert);
        etQuantity=view.findViewById(R.id.etQuantity);
        view.setTag((int)i);
        final int index=i;
        if(first) {
            varDeleteClicked.onDeleteClickedListener(0);
            first=false;
        }
        ibDeleteBtn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,String.valueOf(index),Toast.LENGTH_SHORT).show();
                varDeleteClicked.onDeleteClickedListener(index);
            }
        });

        ibDeleteBtn_alert.setImageResource(R.drawable.ic_delete_sweep_black_24dp);

        tvItemName_alert.setText(item.getItemName().trim());
        tvItemPrice_alert.setText("₹" + item.getItemPrice().trim());
//        etQuantity.setText(item.getItemQuantity());
        etQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    etQuantity.setHint("");

                }else if(!hasFocus && etQuantity.getText().toString().trim().length()==0){
                    etQuantity.setHint("1");
                }else if(!hasFocus && etQuantity.getText().toString().trim().length()!=0){
                    item.setItemQuantity(etQuantity.getText().toString().trim());
                    Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show();

                }



            }
        });
        etQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:{
                        Toast.makeText(context,"textview"+" "+v.getText().toString(),Toast.LENGTH_LONG).show();
                        v.setText(v.getText());
                        return true;
                    }
                    case EditorInfo.IME_ACTION_NEXT:
                    case EditorInfo.IME_ACTION_PREVIOUS:


                }
                return false;
            }
        });




        return view;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
