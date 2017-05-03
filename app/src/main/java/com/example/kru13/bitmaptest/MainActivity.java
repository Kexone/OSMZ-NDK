package com.example.kru13.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native  char getMd5(String textToHash);

    private Md5Hash md5h;
    private String textToHash;
    private String textMd5;
    private EditText textElement;
    TextView javaEst;
    TextView cppEst;
    private final static int UPDATE_CPP = 0;
    private final static int UPDATE_JAVA = 1;
    private final Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            final int what = msg.what;
            long something = msg.arg1;
            switch(what) {
                case UPDATE_JAVA: javaEst.setText(something + " ms"); break;
                case UPDATE_CPP: cppEst.setText(something + " ms"); break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void buttonClick(View v) {
        javaEst = (TextView)findViewById(R.id.textViewSpeedJava);
        cppEst = (TextView)findViewById(R.id.textViewSpeedCpp);
        textElement = (EditText)findViewById(R.id.editText);
        textToHash = textElement.getText().toString();
        Log.d("NDK md5 hash","Starting ...");
        md5h = new Md5Hash();
        textMd5 = md5h.createMd5(textToHash);
        new Thread(new Runnable() {
            public void run() {
                Log.d("MD5 JAVA", "Starting cracking... " );
                long startTime = System.currentTimeMillis();
                while(!Objects.equals(textMd5, md5h.createMd5(md5h.generate())));
                long stopTime = System.currentTimeMillis();
                long javaElapsedTime = stopTime - startTime;
                Log.d("MD5 JAVA", "Java Finished: " + javaElapsedTime + "ms");
                Message msg = Message.obtain(myHandler);
                msg.what = UPDATE_JAVA;
                msg.arg1 = ((int) javaElapsedTime);
                myHandler.sendMessage(msg);
                Log.d("MD5 CPP", "Starting cracking... " );
                startTime = System.currentTimeMillis();
                char neco = getMd5(textMd5);
                Log.d("NECO", neco + "");
                stopTime = System.currentTimeMillis();
                long cppElapsedTime = stopTime - startTime;
                Log.d("MD5 CPP", "Java Finished: " + cppElapsedTime + "ms");
                msg = Message.obtain();
                msg.what = UPDATE_CPP;
                msg.arg1 = ((int) cppElapsedTime);
                myHandler.sendMessage(msg);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }
}
