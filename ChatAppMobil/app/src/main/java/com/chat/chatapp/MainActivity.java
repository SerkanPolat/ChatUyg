package com.chat.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.25:8000");
        } catch (URISyntaxException e) {}
    }


    static String KullaniciAdi;
    public Bilgi MesajBilgileri;
    public KanalListesiEmitterListener kanalListesiEmitterListener;
    KanalKontrolcusu kanalKontrolcusu;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txt = findViewById(R.id.textview);
        kanalKontrolcusu = new KanalKontrolcusu();
        MesajBilgileri = new Bilgi();
        findViewById(R.id.knl1Button).setOnClickListener(kanalKontrolcusu);
        findViewById(R.id.knl2Button).setOnClickListener(kanalKontrolcusu);
        findViewById(R.id.knl3Button).setOnClickListener(kanalKontrolcusu);
        final EditText mesaj = findViewById(R.id.edtGonder);
        findViewById(R.id.btnGiris).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mSocket.connected()){

                    mSocket.connect();

                    mSocket.on("KanalListesi", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    JSONArray array = (JSONArray) args[0];

                                        try {
                                            ((Button)(findViewById(R.id.knl1Button))).setText(array.get(0).toString());

                                            ((Button)(findViewById(R.id.knl2Button))).setText(array.get(1).toString());

                                            ((Button)(findViewById(R.id.knl3Button))).setText(array.get(2).toString());


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    mSocket.off("KanalListesi");
                                }
                            });
                        }
                    });
                    mSocket.on("androidSohbet", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    MesajBilgileri.kullaniciAdi = args[0].toString();
                                    MesajBilgileri.mesaj = args[1].toString();
                                    System.out.println("Android Sohbet Tetikleniyor.");
                                    txt.setText(txt.getText().toString()+"\n"+MesajBilgileri.kullaniciAdi+" : "+MesajBilgileri.mesaj);
                                }
                            });
                        }
                    });
                    findViewById(R.id.constKarsilama).setVisibility(View.INVISIBLE);
                    findViewById(R.id.constMesajlasma).setVisibility(View.VISIBLE);
                    KullaniciAdi = ((TextView)findViewById(R.id.KulAd)).getText().toString();
                }
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mesaj.getText().toString().length()!=0){

                    try {

                        JSONObject sendData = new JSONObject();
                        sendData.put("kulAd",KullaniciAdi);
                        sendData.put("mesaj",mesaj.getText().toString());
                        mSocket.emit("Veri",sendData);
                        mesaj.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
