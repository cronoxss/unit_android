package com.club.unit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private Switch switch_green;
    private Switch switch_donga;
    private Switch switch_city;
    private Switch switch_u_nion;
    private Switch switch_jamsil;
    private Switch switch_jungsan;
    private Switch switch_notice;
    private Switch switch_gallery;
    private Switch switch_total;
    private Button button_comment;



    private Handler mHandler = new Handler();
    private boolean mFlag = false;

    SharedPreferences settings_pref;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings_pref = getSharedPreferences("noti_settings", MODE_PRIVATE);
        ed = settings_pref.edit();


        //스플래시
        startActivity(new Intent(this, SplashActivity.class));

        switch_green = (Switch) findViewById(R.id.switch_green);
        switch_donga = (Switch) findViewById(R.id.switch_donga);
        switch_city = (Switch) findViewById(R.id.switch_city);
        switch_u_nion = (Switch) findViewById(R.id.switch_u_nion);
        switch_jamsil = (Switch) findViewById(R.id.switch_jamsil);
        switch_jungsan = (Switch) findViewById(R.id.switch_jungsan);
        switch_notice = (Switch) findViewById(R.id.switch_notice);
        switch_gallery = (Switch) findViewById(R.id.switch_gallery);
        switch_total = (Switch) findViewById(R.id.switch_total);
        button_comment = (Button) findViewById(R.id.button_comment);

        init_pref("green");
        init_pref("donga");
        init_pref("city");
        init_pref("u_nion");
        init_pref("jamsil");
        init_pref("jungsan");
        init_pref("notice");
        init_pref("gallery");
        init_pref("total");
        init_pref("comment");
        if(!settings_pref.contains("id")){
            ed.putString("id","");
            ed.commit();
        }

        switch_green.setChecked(settings_pref.getBoolean("green",TRUE));
        switch_donga.setChecked(settings_pref.getBoolean("donga",TRUE));
        switch_city.setChecked(settings_pref.getBoolean("city",TRUE));
        switch_u_nion.setChecked(settings_pref.getBoolean("u_nion",TRUE));
        switch_jamsil.setChecked(settings_pref.getBoolean("jamsil",TRUE));
        switch_jungsan.setChecked(settings_pref.getBoolean("jungsan",TRUE));
        switch_notice.setChecked(settings_pref.getBoolean("notice",TRUE));
        switch_gallery.setChecked(settings_pref.getBoolean("gallery",TRUE));
        switch_total.setChecked(settings_pref.getBoolean("total",TRUE));
        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        switch_green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("green",isChecked);
            }
        });
        switch_donga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("donga",isChecked);
            }
        });
        switch_city.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("city",isChecked);
            }
        });
        switch_u_nion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("u_nion",isChecked);
            }
        });
        switch_jamsil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("jamsil",isChecked);
            }
        });
        switch_jungsan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("jungsan",isChecked);
            }
        });
        switch_notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("notice",isChecked);
            }
        });
        switch_gallery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("gallery",isChecked);
            }
        });
        switch_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribe_save("total",isChecked);
            }
        });
    }
    private void showPopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("댓글 알림 설정하기");
        helpBuilder.setMessage("본인 ID를 입력하세요");

        final EditText input = new EditText(this);
        input.setSingleLine();
        input.setText(settings_pref.getString("id", ""));


        helpBuilder.setView(input);
        helpBuilder.setCancelable(false);
        helpBuilder.setPositiveButton("알림 설정",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        subscribe_save("comment",true);
                        ed.putString("id",input.getText().toString());
                        ed.commit();
                        Toast.makeText(getApplicationContext(), input.getText().toString()+"로 댓글 알림 설정", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        helpBuilder.setNegativeButton("알림 해제", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                subscribe_save("comment",false);
                ed.putString("id","");
                ed.commit();
                Toast.makeText(getApplicationContext(), "알림 해제", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }

    private void init_pref(String key) {
        //preference initialization
        if(!settings_pref.contains(key)){
            ed.putBoolean(key,FALSE);
            ed.commit();
        }
    }
    private void subscribe_save(String key, Boolean aBoolean) {
        if(aBoolean){
            FirebaseMessaging.getInstance().subscribeToTopic(key);
        }else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(key);
        }
        ed.putBoolean(key,aBoolean);
        ed.commit();
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if (mWebView.getOriginalUrl().equalsIgnoreCase(MAIN_URL)) {
//                if (!mFlag) {
//                    Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();    // 종료안내 toast 를 출력
//                    mFlag = true;
//                    mHandler.sendEmptyMessageDelayed(0, 2000);    // 2000ms 만큼 딜레이
//                    return false;
//                } else {
//                    // 앱 종료 code
//                    moveTaskToBack(true);
//                    finish();
//                    android.os.Process.killProcess(Process.myPid());
//                }
//            } else {
//                // 뒤로 가기 실행
//                if (mWebView.canGoBack()) {
//                    mWebView.goBack();
//                    return true;
//                }
//            }
//        }
//        return true;
//    }
//

}
