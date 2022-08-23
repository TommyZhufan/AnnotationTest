package com.tommytest.annotationtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tommytest.annotationtest.R;
import com.tommytest.annotationtest.inject.InjectIntentParam;
import com.tommytest.annotationtest.inject.InjectOnClick;
import com.tommytest.annotationtest.inject.InjectUtils;
import com.tommytest.annotationtest.inject.InjectView;

public class InjectTestActivity extends AppCompatActivity {

    @InjectView(R.id.id_tv_inject)
    private TextView textViewInfo;

    @InjectIntentParam()
    private String name = "";

    @InjectIntentParam("TestAge")
    private int age = 0;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject_test);
        setupInjectAnnotation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setupInjectAnnotation() {
        InjectUtils.injectIntentParameters(this);
        InjectUtils.injectView(this);
        InjectUtils.injectOnClick(this);
    }

    private void setupView() {
        textViewInfo.setText("hello".concat(name)
                .concat(", your age is")
                .concat(String.valueOf(age)));
    }

    @InjectOnClick({R.id.btn_test1, R.id.btn_test2})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test1:
                onBtnTest1Click();
                break;
            case R.id.btn_test2:
                onBtnTest2Click();
                break;
            default:
                break;
        }
    }

    private void onBtnTest1Click() {
        Toast.makeText(this, "onBtnTest1Click", Toast.LENGTH_SHORT).show();
    }

    private void onBtnTest2Click() {
        Toast.makeText(this, "onBtnTest2Click", Toast.LENGTH_SHORT).show();
    }
}