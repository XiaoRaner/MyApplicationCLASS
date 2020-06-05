package com.swufe.myapplicationclass;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RateListActivity extends ListActivity implements Runnable{//父类发生改变

    //   private String[] list_data = {"one","tow","three","four"};//第一个list_data是计划显示在列表中的数据项，该数据项可以是一个List列表，也可以是一个String[]数组
    String data[]= {"wait..."};
    //int msgWhat = 3;//msgWhat是消息标识
    Handler handler;//handler是用于接收子线程的消息处理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);//因为其父类ListActicity中已经有默认的布局，这里不需要再加载布局文件，去掉语句

        List<String> list1=new ArrayList<String>();//定义了1个列表
        for(int i=1;i<100;i++){
            list1.add("item"+i);//可以往里面添加99个数据
        }

        ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,data);
        setListAdapter(adapter);

        Thread t=new Thread(this);
        t.start();                   //开启线程

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                   if(msg.what==5){ //数据获取
                       List<String> list2=(List<String>)msg.obj;//拆包
                       //构造一个ArrayAdapter用于处理数据和页面的关联
                       ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);//引入adapter,作为沟通数据和列表的桥梁
                       //<String>是泛型，和数据的类型相同
                       //内含3个对象：this,布局，数据
                       //android.R.layout.simple_list_item_1是一种安卓平台提供的布局
                       setListAdapter(adapter);//父类提供的方法，使当前界面由adapter来管理
                  }
                super.handleMessage(msg);
                   }
        };

    }


    @Override
    public void run() {//多线程的方法

        // 获取网络数据，放入list带回到主线程中

        List<String> retList=new ArrayList<String>();//定义变量retList
        Document doc=null;
        try{
            Thread.sleep(1000);
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();//把网页给这个包解析
            Log.i(TAG, "run: " + doc.title());//TAG，获得当前网页的title

            Elements tables = doc.getElementsByTag("table");
            /*for(Element table: tables){
                Log.i(TAG,"run:table["+i+"]=" +table);
                i++;
            }*/

            Element table1 = tables.get(0);

            Elements tds = table1.getElementsByTag("td");//从table1中获得所需数据所在的 td 集合
            for(int i=0;i<tds.size();i+=6){//原网页每行6个元素，想提取同一列就要每隔6个提取一次
                Element td1 = tds.get(i);//td1为第一列数据
                Element td2 = tds.get(i+5);

                String str1 = td1.text();
                String val = td2.text();

                Log.i(TAG,"run:"+str1 + "==>" + val);
                retList.add(str1+"==>"+val);


            }

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }


        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);//获得数据
        msg.obj = retList;//存放数据给obj
        handler.sendMessage(msg);//发送消息

    }
}
