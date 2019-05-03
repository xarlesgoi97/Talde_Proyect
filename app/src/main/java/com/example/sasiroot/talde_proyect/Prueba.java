package com.example.sasiroot.talde_proyect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Prueba extends AppCompatActivity {
    //FIREBASE
    public static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;


    //botones
    private Button btnSignIn;
    private Button btnSignOut;

    private TextView textStatus;
    private TextView textDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);




    //instanciar objetos
        this.btnSignIn = this.findViewById(R.id.btnSignIn);
        this.btnSignOut = this.findViewById(R.id.btnSignOut);
        this.textStatus = this.findViewById(R.id.textStatus);
        this.textDetail = this.findViewById(R.id.textDetail);
        this.mAuth = FirebaseAuth.getInstance();

    //autentificadores Google, facebook
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());
    //Funciones de botones

        //sign up
        this.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and launch sign-in intent

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
                updateUI(mAuth.getCurrentUser());


            }


        });
        this.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and launch sign-in intent
                FirebaseAuth.getInstance().signOut();
                updateUI(mAuth.getCurrentUser());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                event();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private void event() {
        Intent i = new Intent(this,EventActivity.class);
        i.putExtra("user", mAuth.getCurrentUser());
        //i.putExtra("user_photo", mAuth.getCurrentUser().getPhotoUrl().toString());

        startActivityForResult(i,RESULT_OK);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
        if (user != null) {
//            textStatus.setText(user.getEmail());
//            textDetail.setText(user.getUid());
            event();
        } else {
            textStatus.setText("signed out");
            textDetail.setText(null);


        }
    }




}
