package com.example.exercise4;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient {

    public static final String TAG = TcpClient.class.getSimpleName();
    public String SERVER_IP; //server IP address
    public int SERVER_PORT;
    private Socket mySocket;
    // used to send messages
    private PrintWriter mBufferOut;

    /**
     * Constructor of the class. receive ip and port as parameters
     */
    public TcpClient(String ip, int port) {
        SERVER_IP = ip;
        SERVER_PORT = port;
        establishConnection();
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(final String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBufferOut != null) {
                    Log.d(TAG, "Sending: " + message);
                    mBufferOut.println(message);
                    mBufferOut.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient() {

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mBufferOut = null;
        try {
            mySocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishConnection() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //here you must put your computer's IP address.
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    InetSocketAddress socketAddr = new InetSocketAddress(serverAddr, SERVER_PORT);

                    mySocket = new Socket();
                    mySocket.connect(socketAddr, 3000);

                    Log.d("TCP Client", "C: Connecting...");

                    //sends the message to the server
                    mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream())), true);

                } catch (Exception e) {
                    Log.e("TCP", "C: Error", e);
                    stopClient();
                }
            }
        }).start();
    }
}