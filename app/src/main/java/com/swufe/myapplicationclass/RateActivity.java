package com.swufe.myapplicationclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    EditText rmb; //输入
    TextView show;//输出

    //onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引入布局页面activity_rate
        setContentView(R.layout.activity_rate);

        //在onCreate方法中获取对控件的引用
        rmb = (EditText) findViewById(R.id.rmb);  //获取引用+强制转换
        show = (TextView) findViewById(R.id.show);
    }

     //onClick方法，参数为View时，作为按钮事件处理，点击控件时调用。控件加上android:onClick="onClick"
     public void onClick(View btn)        {
         // 首先获取用户输入，然后根据不同币种计算出不同的结果，如果用户没有输入内容，则给出提示

        //获取输入
        String str = rmb.getText().toString();//取出的一定是文本类型

        float r = 0;//定义在外面，之后会用到
        if(str.length()>0){//文本类型不能为空
        r = Float.parseFloat(str);//文本类型转为可计算的数字类型Float
        }else{
        //用户没有输入内容,给出提示信息
        Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        //文本，显示时间长短
        return;
        }

        //计算
         // 完成对按钮的事件处理，因为三个按钮都由一个方法处理，需要在方法中区分事件来源，可以通过btn.getId()来进行判断。
        if(btn.getId()==R.id.dollar){  //当用户点击美元时
        show.setText(String.valueOf(r*1/6.7f));//加f,使double的小数转为float类型
            //输出，转换类型,float到String
        }else if(btn.getId()==R.id.euro){
        show.setText(String.valueOf(r*1/11.0f));//整数相除还是整数，0...取整还是0
                                                //加.0变成小数，加f转换
        }else{
        show.setText(String.valueOf(r*500f));
        }

}  }