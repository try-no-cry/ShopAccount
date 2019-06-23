package com.example.om.shopaccount;

import android.app.ProgressDialog;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText etNameR,etEmailR,etPasswordR;
    private Button btnSignUpR,btnLoginR;
    private RadioButton rbShopR,rbCustrR;
    private ProgressDialog progressDialog;
    private FirebaseAuth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNameR=findViewById(R.id.etNameR);
        etEmailR=findViewById(R.id.etEmailR);
        etPasswordR=findViewById(R.id.etPasswordR);
        btnSignUpR=findViewById(R.id.btnSignUpR);
        btnLoginR=findViewById(R.id.btnLoginR);
        rbShopR=findViewById(R.id.rbShopR);
        rbCustrR=findViewById(R.id.rbCustR);

        progressDialog=new ProgressDialog(this);

        rbShopR.setActivated(true);


        btnSignUpR.setOnClickListener(this);
        btnLoginR.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view==btnSignUpR){
            registerUser();
        }

        if(view==btnLoginR){
            //open login activity
        }
    }

    private void registerUser() {

        String name=etNameR.getText().toString().trim();
        String email=etEmailR.getText().toString().trim();
        String password=etPasswordR.getText().toString().trim();
        String userType;
        if(rbShopR.isSelected()){
            userType="shopkeeper";
        }
        else userType="customer";

        if(name.length()==0 || email.length()==0 || password.length()==0 ){
            Toast.makeText(this, "Input Details", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.setMessage("Registering...");
            progressDialog.show();

        }
    }
}
