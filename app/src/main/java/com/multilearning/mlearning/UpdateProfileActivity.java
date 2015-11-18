package com.multilearning.mlearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UpdateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.cancel_button) {

        }
        if(v.getId() == R.id.update_button) {
           // startActivity(new Intent(UpdateProfile.this, ActivityRegister.class));
        }
    }
}
