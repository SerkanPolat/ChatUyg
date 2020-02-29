package com.chat.chatapp;

import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;

public class MesajlasmaEmitterListener implements Emitter.Listener {

    private Bilgi MesajBilgileri;
    private TextView txt;

    public MesajlasmaEmitterListener(TextView txt){
        this.txt = txt;
        MesajBilgileri = new Bilgi();
    }


    //Gelen Mesaj Bilgileri Ilk Elemanda Yazili args[0]
    @Override
    public void call(Object... args) {



    }

}
