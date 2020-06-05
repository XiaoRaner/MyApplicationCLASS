package com.swufe.myapplicationclass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class RateListActivity extends ListActivity {//父类发生改变

    //   private String[] list_data = {"one","tow","three","four"};//第一个list_data是计划显示在列表中的数据项，该数据项可以是一个List列表，也可以是一个String[]数组
    String data[]= {"one","tow","three","four"};
    //int msgWhat = 3;//msgWhat是消息标识
    Handler handler;//handler是用于接收子线程的消息处理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);//因为其父类ListActicity中已经有默认的布局，这里不需要再加载布局文件，去掉语句



        //构造一个ArrayAdapter用于处理数据和页面的关联
        ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,data);//引入adapter,作为沟通数据和列表的桥梁
                                             //<String>是泛型，和数据的类型相同
                                                      //内含3个对象：this,布局，数据
                                                                            //android.R.layout.simple_list_item_1是一种安卓平台提供的布局
        setListAdapter(adapter);//父类提供的方法，使当前界面由adapter来管理

    }








}
