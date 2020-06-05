package com.swufe.myapplicationclass;
//页面列表，每一行列表项由2个含数据的控件组成
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import com.swufe.myapplicationclass.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MyList2Activity extends ListActivity implements Runnable{ //继承列表父类，整个页面是一个列表

    Handler handler;
    private ArrayList<HashMap<String,String>>listItems;//声明数据项，存放文字、图片信息等
    //List,Map是接口；ArryList,HashMap是实现类
    private SimpleAdapter listItemAdapter;//声明适配器(系统自带的)，处理数据和控件对应关系

    //列表内容只有一项时：里面存放String字符串；
    //列表内容有多项时：  存放Map<Key键，Value值>,需要定义类型，比如Map<String,String>
    //TextView控件显示数据时，需要明确数据和控件的对应关系

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_*list2); 继承列表父类后需注释掉

        initListView();//调用initListView方法
        this.setListAdapter(listItemAdapter);//应用适配器listItemAdapter

        Thread t = new Thread(this);//t为子线程
        t.start();





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

    }
}
