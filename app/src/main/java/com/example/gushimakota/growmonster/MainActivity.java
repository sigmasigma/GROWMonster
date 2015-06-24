package com.example.gushimakota.growmonster;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Context;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {
    Calendar calendar_now;
    ImageView monster;
    TextView text;
    Bitmap bitImage;
    Resources resM;
    int hungry,muscle,tired,stress,year,day,hour,min,id;
    boolean init;
    long time;
    SharedPreferences prefer;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monster=(ImageView)findViewById(R.id.monster);

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



        bitImage = BitmapFactory.decodeResource(resM, prefer.getInt("mons_id", R.drawable.form1));
        monster.setImageDrawable(null);
        monster.setImageBitmap(null);
        monster.setImageBitmap(bitImage);
    }

    public void change_id(){

    }

    public void init_mons(){
        resM = getResources();
        prefer = getSharedPreferences("Ref", MODE_PRIVATE);
        editor = prefer.edit();
        id = R.drawable.egg;
        hungry = 3;
        muscle = 0;
        stress = 0;
        tired = 0;
        editor.putInt("mons_id",id);
        editor.putInt("hungry",hungry);
        editor.putInt("muscle",muscle);
        editor.putInt("stress",stress);
        editor.putInt("tired",tired);
        editor.putInt("year",year);
        editor.putInt("day",day);
        editor.putInt("hour",hour);
        editor.putInt("min",min);

        editor.commit();

    }

    public void food(View v){
        hungry+=5;
        if(hungry>10){
            stress++;
        }
        editor = prefer.edit();
        editor.putInt("hungry",hungry);
        editor.putInt("stress", stress);
        editor.commit();
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
        editor.commit();
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
