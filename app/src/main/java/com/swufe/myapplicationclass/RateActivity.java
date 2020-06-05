package com.swufe.myapplicationclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    private final String TAG="Rate";//?
    private float dollarRate=0.1f;
    private float euroRate=0.2f;
    private float wonRate=0.3f;    //变量值，不能再直接输入数字

    EditText rmb; //输入
    TextView show;//输出

    //onCreate方法1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引入布局页面activity_rate
        setContentView(R.layout.activity_rate);

        //在onCreate方法中获取对控件的引用
        rmb = (EditText) findViewById(R.id.rmb);  //获取引用+强制转换
        show = (TextView) findViewById(R.id.show);
    }

     //onClick方法2，参数为View时，作为按钮事件处理。点击控件时调用。控件加上android:onClick="onClick"
     public void onClick(View btn)        {
         // 首先获取用户输入，然后根据不同币种计算出不同的结果，如果用户没有输入内容，则给出提示

        //获取输入
        String str = rmb.getText().toString();// 取出的一定是文本类型

        float r = 0;//定义在外面，之后会用到
        if(str.length()>0){//文本类型不能为空
        r = Float.parseFloat(str);//将文本类型转为可计算的数字类型Float
        }else{
        //用户没有输入内容,给出提示信息
        Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        //文本，显示时间长短
        return;
        }

        //计算
         // 完成对按钮的事件处理，因为三个按钮都由一个方法处理，需要在方法中区分事件来源，可以通过btn.getId()来进行判断。
        if(btn.getId()==R.id.dollar){  //当用户点击美元时
       // show.setText(String.valueOf(r*1/6.7f));//加f,使double的小数转为float类型
            //输出，转换类型,float到String
            show.setText(String.format("%.2f",r*dollarRate));
        }else if(btn.getId()==R.id.euro){
            //show.setText(String.valueOf(r*1/11.0f));//整数相除还是整数，0...取整还是0
                                                //加.0变成小数，加f转换
            show.setText(String.format("%.2f",r*euroRate));
        }else{
        //show.setText(String.valueOf(r*500f));
            show.setText(String.format("%.2f",r*wonRate));
        }
}

    //打开新页面的方法3
    public void openOne(View btn){

       /* //打开一个页面Activity
        Intent config = new Intent(this,ConfigActivity.class);//调用Intent对象。参数：从哪个窗口打开，要打开的窗口名字
        //Intent web=new Intent(Intent.)    //谷歌：android点击按钮打开浏览器网页

        config.putExtra("dollar_rate_key",dollarRate);//把参数从本界面传递到下个界面。在此三个汇率作为参数
        config.putExtra("euro_rate_key",euroRate);//数据标签，数据值
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG, "openOne: dollarRate=" + dollarRate);//输出TAG，调试时看数据有没有传过去
        Log.i(TAG, "openOne: euroRate=" + euroRate);
        Log.i(TAG, "openOne: wonRate=" + wonRate);

        //startActivity(config); //打开窗口（可以穿数据过去）
        startActivityForResult(config,1);//打开窗口，还可以带回数据（窗口对象，一个整数）*/

        openConfig();


    }

    //代码被提取成一个方法，在方法3和方法6中使用。避免大段重复代码
    private void openConfig() {
        //打开一个页面Activity
        Intent config = new Intent(this, ConfigActivity.class);//调用Intent对象。参数：从哪个窗口打开，要打开的窗口名字
        //Intent web=new Intent(Intent.)    //谷歌：android点击按钮打开浏览器网页

        config.putExtra("dollar_rate_key", dollarRate);//把参数从本界面传递到下个界面。在此三个汇率作为参数
        config.putExtra("euro_rate_key", euroRate);//数据标签，数据值
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne: dollarRate=" + dollarRate);//输出TAG，调试时看数据有没有传过去
        Log.i(TAG, "openOne: euroRate=" + euroRate);
        Log.i(TAG, "openOne: wonRate=" + wonRate);

        //startActivity(config); //打开窗口（可以穿数据过去）
        startActivityForResult(config, 1);//打开窗口，还可以带回数据（窗口对象，一个整数）
    }

    //处理带回的新数据的方法4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //（请求编码（区分是谁返回的数据），响应编码（区分返回的数据是什么），）

        if(requestCode==1 && resultCode==2){

           //准备拆分打包回来的数据
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);//按新数据标签取出新数据，赋值给当前变量
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);

            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //添加菜单的方法5
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);//调用菜单文件
        return true;
    }

    //菜单事件处理方法6
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){  //通过菜单按钮id确认是该按钮
            openConfig();

        }
        return super.onOptionsItemSelected(item);
    }








}