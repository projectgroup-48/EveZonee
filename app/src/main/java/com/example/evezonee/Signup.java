package com.example.evezonee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://evezonee-fd60e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText users = findViewById(R.id.user);
        final EditText emails = findViewById(R.id.email);
        final EditText phones = findViewById(R.id.phone);
        final EditText passw = findViewById(R.id.password);
        final Button sign = findViewById(R.id.signup);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = users.getText().toString();
                String email = emails.getText().toString();
                String phone = phones.getText().toString();
                String pass = passw.getText().toString();

                if(username.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter the details!",Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if phone number is not registered before
                            if(snapshot.hasChild(phone)){
                                Toast.makeText(getApplicationContext(),"Phone no is already registered!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                //sending data to firebase & phone number is taken as unique
                                databaseReference.child("users").child(phone).child("Username").setValue(username);
                                databaseReference.child("users").child(phone).child("Email").setValue(email);
                                databaseReference.child("users").child(phone).child("Password").setValue(pass);

                                Toast.makeText(getApplicationContext(),"User registered successfully!",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
}