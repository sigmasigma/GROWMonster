package com.example.gushimakota.growmonster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Handler;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    //フィールド宣言
    private final Handler handler = new Handler();
    Calendar calendar_now;
    ImageView monster;
    Resources resM;
    int hungry,muscle,tired,stress,year,day,hour,min,id,state,animationFlag=0;
    Button btn_sport,btn_food,btn_reset;
    boolean init;
    long time;
    SharedPreferences prefer;
    Editor editor;
    private final Runnable func= new Runnable() {
        @Override
        public void run() {
            //ここに実行したい処理を記述
            animation();
            btn_sport.setVisibility(View.VISIBLE);
            btn_food.setVisibility(View.VISIBLE);
            btn_reset.setVisibility(View.GONE);
        }
    };

    //起動時メソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初期化
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //宣言
        monster=(ImageView)findViewById(R.id.monster);
        //text=(TextView)findViewById(R.id.textView);
        btn_sport = (Button)findViewById(R.id.sport);
        btn_food = (Button)findViewById(R.id.food);
        btn_reset = (Button)findViewById(R.id.reset);

        //SharedPreferenceの設定
        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();

        //初回起動時のチェック
        init = prefer.getBoolean("init", false);

        //現在時刻の代入
        calendar_now = Calendar.getInstance();
        year = calendar_now.get(Calendar.YEAR);
        day = calendar_now.get(Calendar.DAY_OF_YEAR);
        hour = calendar_now.get(Calendar.HOUR);
        min = calendar_now.get(Calendar.MINUTE);

        //初回起動時の初期化
        if(!init){
            init_mons();
        }

        //前回起動時刻と現在時刻との差
        time = ((year - prefer.getInt("year",year))*8760 +
                (day-prefer.getInt("day", day))*24 +
                (hour - prefer.getInt("hour", hour)))*60 +
                (min-prefer.getInt("min",min));

        //前回の状態の取得
        state = prefer.getInt("state",-1);

        //状態によって形態を変える条件が違うので分岐
        switch (state){
            case 0:
                change_id_0();
                break;
            case 1:
                change_id_1();
                break;
            case 2:
                change_id_2();
                break;
            case 3:
                change_id_3();
                break;
            case 4:
                change_id_4();
                break;
        }

        //デバッグ用文字列
//        text.setText("state=" + String.valueOf(prefer.getInt("state", -1)) + ",time=" + String.valueOf(time));

        if(state == 0){
            egg();
        }else if(state == -1){
            dead();
        }else{
            //リセットボタン不可視化
            btn_reset.setVisibility(View.GONE);
        }
        animation();
    }

    //タマゴ状態でのボタンの不可視化
    public void egg(){
        btn_sport.setVisibility(View.GONE);
        btn_food.setVisibility(View.GONE);
        btn_reset.setVisibility(View.GONE);
    }

    //dead状態でのボタンの不可視化、リセットボタンの可視化
    public void dead(){
        btn_sport.setVisibility(View.GONE);
        btn_food.setVisibility(View.GONE);
        btn_reset.setVisibility(View.VISIBLE);
    }

    //----------------進化の実装ココカラ---------------------
    public void change_id_0(){
                if(time>10){
                    state =-1;
//                    id = R.drawable.grave;
                    id = R.drawable.form_animgrave;
                    editor.putInt("mons_id", id);
                    editor.putInt("state", state);
                    editor.apply();
                    setTime();
                    return;
                }else if(time>5){
//                    id = R.drawable.form4;
                    id = R.drawable.form_anim3;
                    state =4;
                    editor.putInt("mons_id", id);
                    editor.putInt("state", state);
                    editor.apply();
                    setTime();
                    return;
                }else if(time>0){
//                    id = R.drawable.form1;
                    id = R.drawable.form_anim1;
                    state =1;
                    editor.putInt("mons_id", id);
                    editor.putInt("state",state);
                    editor.apply();
                    setTime();
                    return;
                }
    }

    public void change_id_1(){
        if(time>5){
//            id = R.drawable.grave;
//            id = R.drawable.form_animgrave;
            state =-1;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            setTime();
            return;
        }else if(time>0){
//            id = R.drawable.form4;
//            id = R.drawable.form_anim2;
            state =2;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            setTime();
            return;
        }
    }

    public void change_id_2(){
        if(time>10){
            state =-1;
//                    id = R.drawable.grave;
//            id = R.drawable.form_animgrave;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            setTime();
            return;
        }else if(time>0&&hungry<=5){
//                    id = R.drawable.form4;
            id = R.drawable.form_anim4;
            state =4;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            setTime();
            return;
        }else if(time>0&&hungry>5){
//                    id = R.drawable.form1;
            id = R.drawable.form_anim3;
            state =3;
            editor.putInt("mons_id", id);
            editor.putInt("state",state);
            editor.apply();
            setTime();
            return;
        }
    }

    public void change_id_3(){
        if(time>2){
            state =-1;
//                    id = R.drawable.grave;
            id = R.drawable.form_animgrave;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            setTime();
            return;
        }
    }

    public void change_id_4(){
        if(time>2){
//            id = R.drawable.grave;
            id = R.drawable.form_animgrave;
            state =-1;
            editor.putInt("mons_id", id);
            editor.putInt("state", state);
            editor.apply();
            return;
        }
    }

    //----------------進化の実装ココマデ---------------------

    //アニメーションの表示
    public void animation() {
//        super.onWindowFocusChanged(hasFocus);

        ImageView img = (ImageView)findViewById(R.id.monster);
        // AnimationDrawableのXMLリソースを指定
        switch (state){
            case 0:
                img.setBackgroundResource(R.drawable.form_anim0);
                break;
            case 1:
                img.setBackgroundResource(R.drawable.form_anim1);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.form_anim2);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.form_anim3);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.form_anim4);
                break;
            case -1:
                img.setBackgroundResource(R.drawable.form_animgrave);
                break;
        }

        // AnimationDrawableを取得
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        // アニメーションの開始
        frameAnimation.start();
    }

    //初期化時の諸設定
    public void init_mons(){
        id = R.drawable.egg;
        hungry = 3;
        muscle = 0;
        stress = 0;
        tired = 0;
        state = 0;
        editor.putInt("state",state);
        editor.putInt("mons_id",id);
        editor.putInt("hungry",hungry);
        editor.putInt("muscle",muscle);
        editor.putInt("stress",stress);
        editor.putInt("tired",tired);
        editor.putBoolean("init", true);
        editor.apply();
        setTime();
    }

    //時間を取得する
    public void setTime(){
        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();
        editor.putInt("year", year);
        editor.putInt("day", day);
        editor.putInt("hour", hour);
        editor.putInt("min", min);
        editor.apply();
    }

    //リセットボタン時の挙動
    public void reset(View v){
        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();
        init = false;
        editor.putBoolean("init", false);
        editor.apply();

        //アクティビティの再起動
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    //ご飯ボタン時の挙動
    public void food(View v){
        hungry+=5;
        if(hungry>10){
            stress++;
        }
        editor = prefer.edit();
        editor.putInt("hungry", hungry);
        editor.putInt("stress", stress);
        editor.apply();
        ImageView img = (ImageView)findViewById(R.id.monster);
        btn_sport.setVisibility(View.GONE);
        btn_food.setVisibility(View.GONE);
        btn_reset.setVisibility(View.GONE);
        switch(state){
            case 1:
                img.setBackgroundResource(R.drawable.form_eat1);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.form_eat2);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.form_eat3);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.form_eat4);
                break;
        }
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
        handler.postDelayed(func, 4000);
    }

    //運動ボタン時の挙動
    public void sport(View v){
        hungry-=3;
        if(hungry>10){
            stress+=2;
        }
        editor = prefer.edit();
        editor.putInt("hungry",hungry);
        editor.putInt("stress", stress);
        editor.apply();
        ImageView img = (ImageView)findViewById(R.id.monster);
        btn_sport.setVisibility(View.GONE);
        btn_food.setVisibility(View.GONE);
        btn_reset.setVisibility(View.GONE);
        switch(state){
            case 1:
                img.setBackgroundResource(R.drawable.form_sport1);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.form_sport2);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.form_sport3);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.form_sport4);
                break;
        }
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
        handler.postDelayed(func, 4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
