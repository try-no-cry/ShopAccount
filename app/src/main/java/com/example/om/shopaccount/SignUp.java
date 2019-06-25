package com.example.om.shopaccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText etNameR,etEmailR,etPasswordR;
    private Button btnSignUpR,btnLoginR;
    private RadioButton rbShopR,rbCustrR;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference("Users");


    @Override
    protected void onStart() {
        super.onStart();

    }

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
        firebaseAuth=FirebaseAuth.getInstance();


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

        final String name=etNameR.getText().toString().trim();
        final String email=etEmailR.getText().toString().trim();
        final String password=etPasswordR.getText().toString().trim();
        final String userType;
        if(rbCustrR.isChecked()){
            userType="customer";
        }
        else userType="shopkeeper";

        if(name.length()==0 || email.length()==0 || password.length()==0 ){
            Toast.makeText(this, "Input Details", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.length()<6){
            Toast.makeText(this,"Password length must be greater than 6 characters",Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.setMessage("Registering...");
            progressDialog.show();

           firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                progressDialog.dismiss();

                                String uid=firebaseAuth.getUid();
                                NewUserInfoSignUp user=new NewUserInfoSignUp(name,email,password,userType);
                                databaseUsers.child(uid).setValue(user);


                                // Sign in success, update UI with the signed-in user's information


                                if(userType.equals("shopkeeper")){
                                    //shopkeeper registered
                                    //shopkeepers id is directly entered into the "Shopkeepers"

                                    DatabaseReference databaseShopkeepers=FirebaseDatabase.getInstance().getReference("Shopkeepers");

//                                    databaseShopkeepers.child(uid).child(name); //have a doubt in this line



                                    Intent intent=new Intent(getApplicationContext(),selectCustomer.class);
                                    intent.putExtra("uidOfShopkeeper",uid);
                                    startActivity(intent);

                                }else{
                                    //customer registered
                                    //customer id will be added after he selects a shopkeeper



                                    Intent intent=new Intent(getApplicationContext(),selectShopkeeper.class);
                                    intent.putExtra("uid",uid);// this is uid of the user

                                    startActivity(intent);


                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Registration Failed. Please Try Again.",Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });


        }
    }
}
