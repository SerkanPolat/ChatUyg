package com.chat.chatapp;

import android.view.View;
import android.widget.Button;

public class KanalKontrolcusu implements View.OnClickListener {



    @Override
    public void onClick(View v) {
        Button btn = (Button)v;

        System.out.println(btn.getTag().toString());

    }
}
