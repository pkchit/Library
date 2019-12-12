package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button loginIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginIn= (Button)findViewById(R.id.main_login);
        signUp = (Button)findViewById(R.id.main_signup);
    }
}
