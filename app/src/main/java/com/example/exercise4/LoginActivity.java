package com.example.exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //creating the new Login layout activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("create","inside Login onCreate()");
        setContentView(R.layout.activity_login);
    }

    //function to be called when the "Connect" button being called
    public void connectClient (View view) {
        EditText editTextIp =(EditText)findViewById(R.id.ip);
        EditText editTextPort =(EditText)findViewById(R.id.port);

        String ip = editTextIp.getText().toString();
        int port = Integer.parseInt(editTextPort.getText().toString());

        Intent intent= new Intent(this,JoystickActivity.class);
        intent.putExtra("ip",ip);
        intent.putExtra("port_int",port);

        startActivity(intent);
    }

    //termintaes the function login activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy","inside Login OnDestroy()");
    }
}
