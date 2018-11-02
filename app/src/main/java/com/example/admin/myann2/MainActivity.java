package com.example.admin.myann2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.myanniation.build.MyT;
import com.example.libanniation.Myanniaton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // MyanniationInject.inject(MainActivity.this);//反射调用 废弃
        MyT.Mytoast(this);//直接调用
        setContentView(R.layout.activity_main);

    }

    @Myanniaton(myvalue="哈哈哈哈")
    public void test(){

    }

}
