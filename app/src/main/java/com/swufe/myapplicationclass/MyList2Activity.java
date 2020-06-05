package com.swufe.myapplicationclass;
//页面列表，每一行列表项由2个含数据的控件组成
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import static android.content.ContentValues.TAG;

public class MyList2Activity extends ListActivity implements AdapterView.OnItemClickListener{ //继承列表父类，整个页面是一个列表

    Handler handler;
    private ArrayList<HashMap<String,String>>listItems;//声明数据项，存放文字、图片信息等
    //List,Map是接口；ArryList,HashMap是实现类
    private SimpleAdapter listItemAdapter;//声明适配器，处理数据和控件对应关系

    //列表内容只有一项时：里面存放String字符串；
    //列表内容有多项时：  存放Map<Key键，Value值>,需要定义类型，比如Map<String,String>
    //TextView控件显示数据时，需要明确数据和控件的对应关系

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_*list2); 继承列表父类后需注释掉

        initListView();//调用initListView方法
        this.setListAdapter(listItemAdapter);//应用适配器listItemAdapter

        getListView().setOnItemClickListener(this);//监听

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

        //生成适配器的Item和动态数组对应的元素：
        listItemAdapter=new SimpleAdapter(      //定义适配器：SimpleAdapter 把布局和数据联系起来
                this,//上下文对象，用于管理生命周期
                listItems,  //创建好的数据源
                R.layout.list_item,  //ListItem的XML布局实现
                //需要将activity_my_list2.xml重命名为list_item.xml(右键-Refactor-Rename)

                new String[]{"ItemTiltle","ItemDetail"},//数据K1,K2
                new int[]{R.id.itemTitle,R.id.itemDetail}//布局控件
                //确定数据和布局的对应关系：
                //数据项listItems中的ItemTiltle、ItemDetail会分别放入布局list_item中的itemTitle控件和itemDetail控件
        );
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "onItemClick: parent=" + parent);
        Log.i(TAG, "onItemClick: view=" + view);
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: id=" + id);

        //从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);

        //从View中获取选中数据
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);

        //打开新的页面，传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);

    }
}

