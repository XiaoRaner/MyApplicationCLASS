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

    }


    //保存数据的方法
    public void save(View btn){
        Log.i(TAG,"save:");

        float newDollar = Float.parseFloat(dollarText.getText().toString()); //获取新的值
        float newEuroi = Float.parseFloat(euroText.getText().toString());
        float newWon = Float.parseFloat(wonText.getText().toString());

        Log.i(TAG, "save: 获取到新的值");//加TAG，看新数据是否获取成功
        Log.i(TAG, "save: newDollar=" + newDollar);
        Log.i(TAG, "save: newEuroi=" + newEuroi);
        Log.i(TAG, "save: newWon=" + newWon);

        Intent intent = getIntent();       //获取intent对象，准备带回去
        Bundle bdl = new Bundle();   //数据保存到Bundle（或放入到Extra），相当于打包作用
        bdl.putFloat("key_dollar",newDollar);//新数据标签，新数据
        bdl.putFloat("key_euro",newEuroi);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);  //把打包好的放入intent
        setResult(2,intent);//数据带回（响应代码，intent）

        finish();//返回到调用页面

    }


}
