package com.example.gushimakota.growmonster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    Calendar calendar_now;
    ImageView monster;
    TextView text;
    Bitmap bitImage;
    Resources resM;
    int hungry,muscle,tired,stress,year,day,hour,min,id,state;
    boolean init;
    long time;
    SharedPreferences prefer;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monster=(ImageView)findViewById(R.id.monster);
        text=(TextView)findViewById(R.id.textView);

        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();

        init = prefer.getBoolean("init", false);

        calendar_now = Calendar.getInstance();
        year = calendar_now.get(Calendar.YEAR);
        day = calendar_now.get(Calendar.DAY_OF_YEAR);
        hour = calendar_now.get(Calendar.HOUR);
        min = calendar_now.get(Calendar.MINUTE);
        if(!init){
            init_mons();
        }
        time = ((year - prefer.getInt("year",year))*8760 +
                (day-prefer.getInt("day", day))*24+
                (hour - prefer.getInt("hour", hour)))*60 +
                (min-prefer.getInt("min",min));

        switch (prefer.getInt("state",-1)){
            case 0:
                change_id_1();
        }


        bitImage = BitmapFactory.decodeResource(resM, prefer.getInt("mons_id", R.drawable.egg));
        monster.setImageDrawable(null);
        monster.setImageBitmap(null);
        monster.setImageBitmap(bitImage);
//        text.setText(String.valueOf(time) + "," + String.valueOf(init)+",id="+String.valueOf(id));
    }

    public void change_id_1(){
                if(time>100){

                    id = R.drawable.grave;
                    state =-1;
                    editor.putInt("mons_id", id);
                    editor.putInt("state",state);
                    editor.apply();
                }else if(time>5){

                }

    }

    //タマゴに戻す初期化
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
        editor.putBoolean("init",true);
        editor.apply();
        setTime();
    }

    public void setTime(){
        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();
        editor.putInt("year",year);
        editor.putInt("day",day);
        editor.putInt("hour",hour);
        editor.putInt("min", min);
        editor.apply();
    }

    //リセット
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

    public void food(View v){
        hungry+=5;
        if(hungry>10){
            stress++;
        }
        editor = prefer.edit();
        editor.putInt("hungry",hungry);
        editor.putInt("stress", stress);
        editor.apply();
        //text.setText(String.valueOf(prefer.getInt("hungry",-1)));
//        resM = getResources();
//        if(bitImage!=null){
//            bitImage.recycle();
//            bitImage = null;
//            monster.setImageDrawable(null);
//            monster.setImageBitmap(null);
//        }
//        bitImage = BitmapFactory.decodeResource(resM, R.drawable.form2);
//        monster.setImageBitmap(bitImage);
    }

    public void sport(View v){
        hungry-=3;
        if(hungry>10){
            stress+=2;
        }
        editor = prefer.edit();
        editor.putInt("hungry",hungry);
        editor.putInt("stress", stress);
        editor.apply();
        //text.setText(String.valueOf(prefer.getInt("hungry",-1)));

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
