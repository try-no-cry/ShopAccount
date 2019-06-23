package com.example.om.shopaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Login extends AppCompatActivity {
    EditText etEmailS,etPasswordS;
    Button btnLoginS;
    RadioButton rbShopS,rbCustrS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmailS=findViewById(R.id.etEmailR);
        etPasswordS=findViewById(R.id.etPasswordR);
        btnLoginS=findViewById(R.id.btnLoginR);
        rbShopS=findViewById(R.id.rbShopR);
        rbCustrS=findViewById(R.id.rbCustR);
    }
}
