package com.example.om.shopaccount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProductEntry extends AppCompatDialogFragment {

 private EditText etProductName,etProductPrice;
    DatabaseReference databaseProducts;
    Button btnAddMore;

    addMoreClicked varAddMoreClickedLilstener;
public interface addMoreClicked{
    public void addMoreClickedListener();

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        databaseProducts= FirebaseDatabase.getInstance().getReference("Products");

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.add_new_product_dialog,null,false);
        builder.setView(v);

        etProductName=v.findViewById(R.id.etProductName);
        etProductPrice=v.findViewById(R.id.etProductPrice);
        btnAddMore=v.findViewById(R.id.btnAddMore);

        builder.setTitle("Add New Product");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                if(etProductName.getText().toString().trim().length()!=0 && etProductPrice.getText().toString().trim().length()!=0) {
                    String id = databaseProducts.push().getKey();
                    single_list_item_Class_alert var = new single_list_item_Class_alert(etProductName.getText().toString().trim(), etProductPrice.getText().toString().trim());
                    databaseProducts.child(id).setValue(var);
                }
                else Toast.makeText(getActivity(),"Please fill the inputs ",Toast.LENGTH_SHORT).show();
            }
        });




        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                add function so that it can call itself recursively NOT WORKIN ...NOT ADDING TO THE

                varAddMoreClickedLilstener.addMoreClickedListener();
            }
        });

       return builder.create();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        varAddMoreClickedLilstener=(addMoreClicked) context;

    }
}
