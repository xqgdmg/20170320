package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.AESUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AesActivity extends AppCompatActivity {


    @Bind(R.id.textView2)
    TextView textView2;
    //密钥种子
    private String key = "0123456789ABCDEF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);
        ButterKnife.bind(this);


        String pass = "cai888888中国中国，壮丽的山河";

        String string = "密钥种子： " + key
                + "\n明文密码： " + pass;


        try {
            //加密
            String s = AESUtil.encrypt(key, pass);
            string = string + "\n\n\n\n加密后的密文:  " + s;

            //解密
            s = AESUtil.decrypt(key, s);
            string = string + "\n\n\n\n解密后得到:  " + s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        textView2.setText(string);

    }

}
