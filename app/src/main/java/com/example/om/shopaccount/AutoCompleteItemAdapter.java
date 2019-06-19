package com.example.om.shopaccount;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteItemAdapter extends ArrayAdapter<single_list_item_Class_alert> {

    private ArrayList<single_list_item_Class_alert> listFull;
    Context context;



onDeleteClicked varDeleteClicked;
public interface onDeleteClicked{
    public void onDeleteClickedListener(int index);}


onItemClicked varItemClicked;
 public interface onItemClicked{
        public void onItemClickedListener(int index);
    }

    public AutoCompleteItemAdapter( Context context, ArrayList<single_list_item_Class_alert> list) {
        super(context,0,list);
        listFull=new ArrayList<>(list);
        this.context=context;
        varItemClicked=(onItemClicked) context;

       varDeleteClicked= (onDeleteClicked) context;


    }

    @NonNull
    @Override
    public Filter getFilter() {
        Log.d("filter", String.valueOf(itemFilter));
        return itemFilter;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View view, @NonNull ViewGroup parent) {
        single_list_item_Class_alert item=getItem(i);
        if(view == null)
            view= LayoutInflater.from(context).inflate(R.layout.single_list_auto_complete,parent,false);
        TextView tvItemName_alert,tvItemPrice_alert;


        tvItemName_alert=view.findViewById(R.id.tvItemName_autocomplete);
        tvItemPrice_alert=view.findViewById(R.id.tvItemPrice_autocomplete);


       final String name=item.getItemName().trim();
        view.setTag((int)i);

        final int index=i;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                varItemClicked.onItemClickedListener(index);

            }
        });



        tvItemName_alert.setText(name);
        tvItemPrice_alert.setText("₹" + item.getItemPrice().trim());


        return view;
    }



    ArrayList<single_list_item_Class_alert> suggestions;

    private Filter itemFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results=new FilterResults();


            suggestions=new ArrayList<>();

            if(charSequence==null || charSequence.length()==0) {

                suggestions.addAll(listFull);
            }
            else{
                String filterPattern=charSequence.toString().toLowerCase().trim();

                for(single_list_item_Class_alert item:listFull){
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values=suggestions;
            results.count=suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                clear();
                addAll((ArrayList)(filterResults.values));
                notifyDataSetChanged();

        }


        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((single_list_item_Class_alert)resultValue).getItemName();
        }
    };
}
