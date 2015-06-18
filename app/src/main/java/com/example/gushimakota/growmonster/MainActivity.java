package com.example.gushimakota.growmonster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    ImageView monster;
    private Bitmap bitImage;
    Button food,sport;
    Resources resM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monster=(ImageView)findViewById(R.id.monster);
        food=(Button)findViewById(R.id.food);
        sport=(Button)findViewById(R.id.sport);
        resM = getResources();
        bitImage = BitmapFactory.decodeResource(resM, R.drawable.form1);
        monster.setImageDrawable(null);
        monster.setImageBitmap(null);
        monster.setImageBitmap(bitImage);
    }

    public void food(View v){
        resM = getResources();
        if(bitImage!=null){
            bitImage.recycle();
            bitImage = null;
            monster.setImageDrawable(null);
            monster.setImageBitmap(null);
        }
        bitImage = BitmapFactory.decodeResource(resM, R.drawable.form2);
        monster.setImageBitmap(bitImage);
    }

    public void sport(View v){
        resM = getResources();
        if(bitImage!=null){
            bitImage.recycle();
            bitImage = null;
            monster.setImageDrawable(null);
            monster.setImageBitmap(null);
        }
        bitImage = BitmapFactory.decodeResource(resM, R.drawable.form4);
        monster.setImageBitmap(bitImage);
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
