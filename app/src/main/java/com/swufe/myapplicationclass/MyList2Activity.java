package com.swufe.myapplicationclass;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.swufe.myapplicationclass.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyList2Activity extends ListActivity implements Runnable{ //继承列表父类，整个页面是一个列表

    private final String TAG="Rate";

    Handler handler;
    private ArrayList<HashMap<String,String>>listItems;//声明数据项，存放文字、图片信息等
    //List,Map是接口；ArryList,HashMap是实现类
    private SimpleAdapter listItemAdapter;//声明适配器(系统自带的)，处理数据和控件对应关系

    //列表内容只有一项时：里面存放String字符串；
    //列表内容有多项时：  存放Map<Key键，Value值>,需要定义类型，比如Map<String,String>
    //TextView控件显示数据时，需要明确数据和控件的对应关系

@Override
    protected void onCreate(Bundle savedInstanceState) {//无误【
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_*list2); 继承列表父类后需注释掉

        initListView();//调用initListView方法
        this.setListAdapter(listItemAdapter);//应用适配器listItemAdapter

        Thread t = new Thread(this);//t为子线程
        t.start();        //】


    handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 7) {
                List<HashMap<String,String>> list2=(List<HashMap<String, String>>) msg.obj;
                listItemAdapter = new SimpleAdapter(this, list2, // listItems数据源
                        R.layout.list_item, // ListItem的XML布局实现
                        new String[] { "ItemTitle", "ItemDetail" },
                        new int[] { R.id.itemTitle, R.id.itemDetail }
                );
                setListAdapter(listItemAdapter);


            }
            super.handleMessage(msg);
        }
    };
    }




    //自定义方法，创立数据项并放入布局列表中
    private void initListView(){

        //创立数据项：
        listItems=new ArrayList<HashMap<String, String>>();//定义listItem,初始化，分配内存空间
        for(int i=0;i<10;i++){
            HashMap<String,String>map=new HashMap<String, String>();   //创建对象map
            map.put("ItemTitle","Rate:"+i);//放入标题文字，k1是ItemTitle，v（值）是"Rate:"+i
            map.put("ItemDetail","detail:"+i);//放入详情描述，k2,v
            listItems.add(map);//把map对象添加到列表里
        }

        //生成适配器Adapter的Item和动态数组对应的元素：
        listItemAdapter=new SimpleAdapter(      //定义适配器：SimpleAdapter 把布局和数据联系起来
                this,//上下文对象，用于管理生命周期
                listItems,  //创建好的数据源
                R.layout.list_item,  //ListItem的XML布局
                //需要将activity_my_list2.xml重命名为list_item.xml(右键-Refactor-Rename)

                new String[]{"ItemTiltle","ItemDetail"},//数据K1,K2
                new int[]{R.id.itemTitle,R.id.itemDetail}//布局控件
                //确定数据和布局的对应关系：
                //数据项listItems中的ItemTiltle、ItemDetail会分别放入布局list_item中的itemTitle控件和itemDetail控件
        );
    }


    @Override
    public void run() {
        List<HashMap<String,String>> retList= new ArrayList<HashMap<String, String>>();
        Document doc = null;

        try {
            Thread.sleep(3000);
            String url = "http://www.usd-cny.com/bankofchina.htm";
            doc = Jsoup.connect(url).get();//把网页给这个包解析
            Log.i(TAG, "run: " + doc.title());//TAG，获得当前网页的title

            Elements tables = doc.getElementsByTag("table");

            Elements table2 = tables.get(1);

            Elements tds = table2.getElementsByTag("td");

            for(int i=0;i<tds.size();i+=8){//原网页每行8个元素，想提取同一列就要每隔8个提取一次
                Element td1 = tds.get(i);//td1为第一列数据
                Element td2 = tds.get(i+5);//td2为刘列数据，再第一列上加5

                String str1 = td1.text();
                String val = td2.text();

                Log.i(TAG, "run: " + str1 + "==>" + val);//看提取出了什么数据
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", str1); // 标题文字
                map.put("ItemDetail", val); // 详情描述
                retList.add(map);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();

    }

        Message msg = handler.obtainMessage(5);
        //msg.what = 5;//what用于整数类型，用于数据比对，类似快递寄件的电话号码
        msg.obj = retList;//obj类型可以传输所有数据;  放入bundle,带回
        handler.sendMessage(msg);//handler把msg放入msg队列中去
}}
