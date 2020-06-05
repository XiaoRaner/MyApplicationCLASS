package com.swufe.myapplicationclass;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RateListActivity extends ListActivity implements Runnable{//父类发生改变

    //   private String[] list_data = {"one","tow","three","four"};//第一个list_data是计划显示在列表中的数据项，该数据项可以是一个List列表，也可以是一个String[]数组
    String data[]= {"wait..."};
    //int msgWhat = 3;//msgWhat是消息标识
    Handler handler;//handler是用于接收子线程的消息处理

    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";//把日期数据放在SharedPreferences里，用于保存数据库中的汇率是哪一天的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);//因为其父类ListActicity中已经有默认的布局，这里不需要再加载布局文件，去掉语句

                   SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
                   logDate = sp.getString(DATE_SP_KEY, "");
                   Log.i("List","lastRateDateStr=" + logDate);

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
    public void run() {
        Log.i("List","run...");
        List<String> retList = new ArrayList<String>();
        Message msg = handler.obtainMessage();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);
        if(curDateStr.equals(logDate)){
            //如果相等，则不从网络中获取数据
            Log.i("run","日期相等，从数据库中获取数据");
            DBManager dbManager = new DBManager(RateListActivity.this);
            for(RateItem rateItem : dbManager.listAll()){
                retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
            }
        }else{
            Log.i("run","日期相等，从网络中获取在线数据");
            //获取网络数据
            try {
                List<RateItem> rateList = new ArrayList<RateItem>();
                URL url = new URL("http://www.usd-cny.com/bankofchina.htm");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                InputStream in = httpConn.getInputStream();
                String retStr = IOUtils.toString(in,"gb2312");//?

                //Log.i("WWW","retStr:" + retStr);
                //需要对获得的html字串进行解析，提取相应的汇率数据...

                Document doc = Jsoup.parse(retStr);
                Elements tables  = doc.getElementsByTag("table");

                Element retTable = tables.get(5);
                Elements tds = retTable.getElementsByTag("td");
                int tdSize = tds.size();
                for(int i=0;i<tdSize;i+=8){
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i+5);
                    //Log.i("www","td:" + td1.text() + "->" + td2.text());
                    float val = Float.parseFloat(td2.text());
                    val = 100/val;
                    retList.add(td1.text() + "->" + val);

                    RateItem rateItem = new RateItem(td1.text(),td2.text());
                    rateList.add(rateItem);
                }
                DBManager dbManager = new DBManager(RateListActivity.this);
                dbManager.deleteAll();
                Log.i("db","删除所有记录");
                dbManager.addAll(rateList);
                Log.i("db","添加新记录集");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //更新记录日期
            SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i("run","更新日期结束：" + curDateStr);
        }

        msg.obj = retList;
        msg.what = msg.what;//?
        handler.sendMessage(msg);
    }
}
