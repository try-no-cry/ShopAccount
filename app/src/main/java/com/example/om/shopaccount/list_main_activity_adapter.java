package com.example.om.shopaccount;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class list_main_activity_adapter extends BaseAdapter {

    ArrayList<single_list_listView_java_class> list;
    Context context;
    boolean first=true;
    onDeleteClicked varDeleteClicked;

    public interface onDeleteClicked{
        public void onDeleteClickedListener(int index);
    }

    public list_main_activity_adapter(Context context,ArrayList<single_list_listView_java_class>  list){


        this.context=context;
        this.list=list;
        varDeleteClicked=(onDeleteClicked)context; //idk what is this but it works with this
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
        single_list_listView_java_class item=list.get(i);
        if(view == null)
            view= LayoutInflater.from(context).inflate(R.layout.single_list_item_alertbox, null,false);
        TextView tvItemName_alert,tvItemPrice_alert;
        ImageButton ibDeleteBtn_alert;
        EditText etQuantity;
        tvItemName_alert=view.findViewById(R.id.tvItemName_alert);
        tvItemPrice_alert=view.findViewById(R.id.tvItemPrice_alert);
        ibDeleteBtn_alert=view.findViewById(R.id.ibDeleteBtn_alert);
        etQuantity=view.findViewById(R.id.etQuantity);
        view.setTag((int)i);
        final int index=i;
    if(item.getItemPrice()!="") {
        ibDeleteBtn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(index), Toast.LENGTH_SHORT).show();
                varDeleteClicked.onDeleteClickedListener(index);
            }
        });


        ibDeleteBtn_alert.setImageResource(R.drawable.ic_delete_sweep_black_24dp);
        tvItemPrice_alert.setText("₹" + item.getItemPrice().trim());
        etQuantity.setText(item.getItemQuantity());

    }
    else{

        ibDeleteBtn_alert.setImageResource(R.drawable.ic_hourglass_empty_black_24dp);
        etQuantity.setVisibility(View.INVISIBLE);
    }
        tvItemName_alert.setText(item.getItemName().trim());
        tvItemPrice_alert.setText("" + item.getItemPrice().trim());


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
