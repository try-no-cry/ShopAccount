package com.example.om.shopaccount;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class single_del_list_adapter extends BaseAdapter {


    ArrayList<single_list_item_Class_alert> list;
    Context context;
    boolean first=true;

    onDeleteClicked varDeleteClicked;

    public interface onDeleteClicked{
        public void onDeleteClickedListener(int index);
    }



    public single_del_list_adapter(Context context, ArrayList<single_list_item_Class_alert>  list){


        this.context=context;
        this.list=list;
        varDeleteClicked=(single_del_list_adapter.onDeleteClicked)context; //idk what is this but it works with this

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
        final single_list_item_Class_alert item=list.get(i);
        if(view == null)
            view= LayoutInflater.from(context).inflate(R.layout.del_single_list_item, null,false);
        TextView tvItemName_Del,tvItemPrice_Del;
        ImageButton ibDel_Item;


        tvItemName_Del=view.findViewById(R.id.tvItemName_Del);
        tvItemPrice_Del=view.findViewById(R.id.tvItemPrice_Del);
        ibDel_Item=view.findViewById(R.id.ibDel_Item);


        final int index=i;

        ibDel_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,String.valueOf(index),Toast.LENGTH_SHORT).show();
                varDeleteClicked.onDeleteClickedListener(index);
            }
        });

        ibDel_Item.setImageResource(R.drawable.ic_delete_sweep_black_24dp);
        if(item.getItemPrice().equals("")){
            tvItemPrice_Del.setText("");
            ibDel_Item.setVisibility(View.GONE);
        }
        else {
            ibDel_Item.setVisibility(View.VISIBLE);
            tvItemPrice_Del.setText("₹" + item.getItemPrice().trim());
        }


        tvItemName_Del.setText(item.getItemName().trim());



        return view;
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
