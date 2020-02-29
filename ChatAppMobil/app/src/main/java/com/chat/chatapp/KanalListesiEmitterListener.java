package com.chat.chatapp;

import com.github.nkzawa.emitter.Emitter;

import com.github.nkzawa.socketio.client.Socket;

public class KanalListesiEmitterListener implements Emitter.Listener {

    public String[] KanalListesi;
    public Socket mSocket;

    public KanalListesiEmitterListener(Socket mSocket){

        this.mSocket = mSocket;
        KanalListesi = null;

    }


    @Override
    public void call(final Object... args) {

    }
}
