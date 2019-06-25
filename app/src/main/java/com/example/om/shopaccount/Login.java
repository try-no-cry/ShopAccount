package com.example.om.shopaccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements OnClickListener{
    EditText etEmailS,etPasswordS;
    Button btnLoginS,btnSignUpS;
    RadioButton rbShopS,rbCustrS;
    private FirebaseAuth mAuth;
    String uid;
ProgressDialog progressDialog;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //directly go its mainActivity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmailS=findViewById(R.id.etEmailS);
        etPasswordS=findViewById(R.id.etPasswordS);
        btnLoginS=findViewById(R.id.btnLoginS);
        rbShopS=findViewById(R.id.rbShopS);
        rbCustrS=findViewById(R.id.rbCustS);
        btnSignUpS=findViewById(R.id.btnSignUpS);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Login.this);


        btnSignUpS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });

        btnLoginS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email=etEmailS.getText().toString().trim();
                final String password=etPasswordS.getText().toString().trim();
                final String userType;
                if(rbCustrS.isChecked()){
                    userType="customer";
                }
                else userType="shopkeeper";

                if( email.length()==0 || password.length()==0 ){
                    Toast.makeText(getApplicationContext(), "Input Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password length must be greater than 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {

                    progressDialog.setMessage("Logging You In....");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {

                                        final DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference("Users");

                                        databaseUsers.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                              boolean isAvailable=false;
                                                for(DataSnapshot userSnapshot:dataSnapshot.getChildren()){
                                                    String thisUser=userSnapshot.getValue(NewUserInfoSignUp.class).getUserType().toString();
                                                    if(thisUser.trim().equals(userType)){
                                                        isAvailable=true;
                                                    }

                                                    if(userSnapshot.getValue(NewUserInfoSignUp.class).getEmail().equals(email)){
                                                        uid=userSnapshot.getKey().toString();
                                                        break;
                                                    }
                                                }

                                                if(isAvailable==false){
                                                    Toast.makeText(Login.this,"No such "+userType+" is registered.",Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                    return;
                                                }
                                                else{

//                                                   databaseUsers.addValueEventListener(new ValueEventListener() {
//                                                       @Override
//                                                       public void onDataChange(DataSnapshot dataSnapshot) {
//                                                           for(DataSnapshot myUser:dataSnapshot.getChildren()){
//                                                               if(myUser.getValue(NewUserInfoSignUp.class).getEmail().equals(email)){
//                                                                   uid=myUser.getKey();
//                                                               }
//                                                           }
//                                                       }

//                                                       @Override
//                                                       public void onCancelled(DatabaseError databaseError) {
//
//                                                       }
//                                                   });


                                                    if(userType.equals("shopkeeper")) {
                                                        startActivity(new Intent(Login.this, selectCustomer.class));
                                                    }
                                                    else {
                                                        Intent intent=new Intent(Login.this,selectShopkeeper.class);
                                                        intent.putExtra("uid",uid);// this is uid of the user

                                                        startActivity(intent);
                                                    }
                                                }


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                                    } else {

                                            Toast.makeText(Login.this,"Login Unsuccessful",Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });

                }

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
