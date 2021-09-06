package com.imakerstudio.JH;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.imakerstudio.pandaui.EditText.FilterEditText;

public class MainActivity extends AppCompatActivity {
    private FilterEditText mEdit;
    private Button testButton;
    private String[] alertArray = {
        "Can not be empty",
        "Must be float"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mEdit = findViewById(R.id.test);
        testButton = findViewById(R.id.btn);

        mEdit.setInputFloat(1.33, 30.33, 2);
        mEdit.setAlertTip("Must be 5 ~ 30");
        mEdit.setAlertArray(alertArray);
        Log.i("Test", mEdit.getAlertArray(0));


        testButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdit.setAlert(!mEdit.getAlert());
            }
        });

    }
}