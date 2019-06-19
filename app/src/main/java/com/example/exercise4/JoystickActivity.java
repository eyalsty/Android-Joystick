package com.example.exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class JoystickActivity extends AppCompatActivity  implements JoystickView.JoystickListener  {

    TcpClient myClient;

    //function to create the JoystickActivity Layout, starts a joystick view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);

        //receiving ip and port from login view and creates new TcpClient
        Intent intent=getIntent();
        String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port_int",0);
        myClient = new TcpClient(ip,port);
    }

    /*function being called after Joystick was moved, receiving the X and Y values
    normalized between -1 and 1. also the Joystick id
     */
    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        String elevator_path = "set controls/flight/elevator " + yPercent + "\r\n";
        String aileron_path = "set controls/flight/aileron " + xPercent + "\r\n";

        myClient.sendMessage(elevator_path);
        myClient.sendMessage(aileron_path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myClient.stopClient();
        Log.d("JoystickActivity","inside JoystickActivity onDestroy()");

    }
}
