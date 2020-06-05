package com.swufe.myapplicationclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {
public final String TAG="ConfigActivity";//定义TAG,该页面可用TAG

    EditText dollarText;//获取三个控件，使获取的数据显示在控件中
    EditText euroText;
    EditText wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        //通过intent对象向调用页面返回数据，保存到Bundle或放入到Extra
        Intent intent = getIntent();//得到数据
        /*Bundle bdl = new Bundle();
        //bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuroi);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);*/

        float dollar2 = intent.getFloatExtra("dollar_rate_key", 0.0f);//数据标签（通过标签本身取出数据），数据默认值
        float euro2 = intent.getFloatExtra("euro_rate_key", 0.0f);
        float won2 = intent.getFloatExtra("won_rate_key", 0.0f);

        Log.i(TAG, "onCreate: dollar2=" + dollar2);//加TAG，看有没有取出数据
        Log.i(TAG, "onCreate: euro2=" + euro2);
        Log.i(TAG, "onCreate: won2=" + won2);

        dollarText = (EditText) findViewById(R.id.dollar_rate);//获取控件
        euroText = (EditText) findViewById(R.id.euro_rate);
        wonText = (EditText) findViewById(R.id.won_rate);


        dollarText.setText(String.valueOf(dollar2));//显示数据到控件
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));


        //setResult(2,intent);

//返回到调用页面
        //    finish();
        // }
    }
    //方法，保存数据
    public void save(View btn){
        Log.i(TAG,"save:");

    }


}
