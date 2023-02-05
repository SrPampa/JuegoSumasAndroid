package com.example.sums;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_mPunt;

    //creo una variable para usar un número aleatorioa para que al entrar en la app cada vez salga un personaje en la pantalla de inicio
    int num_aleatorio = (int) (Math.random() * 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pongo el icono en el título de la app (tambíén he creado un asset con el icono para el resto de cosas)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //enlazo los elementos con el xml
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        iv_personaje = (ImageView) findViewById(R.id.imageView_personaje);
        tv_mPunt = (TextView) findViewById(R.id.textView_mejorPuntuacion);

        //variable para la ruta de la img
        int id;
        switch (num_aleatorio) {
            case 0:
                id = getResources().getIdentifier("transportador", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 1:
                id = getResources().getIdentifier("escuadra", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 2:
                id = getResources().getIdentifier("cartabon", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 3:
                id = getResources().getIdentifier("rotu", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 4:
                id = getResources().getIdentifier("goma", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            default:

        }

        //conexion con sqlite
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor consulta = db.rawQuery("select * from score where puntuacion = (select max(puntuacion) from score)", null);
        if(consulta.moveToFirst()){
            String temp_alias = consulta.getString(0);
            String temp_puntuacion = consulta.getString(1);
            tv_mPunt.setText("Best Score: "+ temp_puntuacion+ " de "+temp_alias);
            db.close();
        } else {
            db.close();
        }
    }

    public void play(View view) {

        String nombre = et_nombre.getText().toString();

        if (!nombre.equals("")) {

            Intent intent = new Intent(this, Nivel1.class);
            intent.putExtra("player", nombre);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Primero debes escribir tu alias", Toast.LENGTH_SHORT).show();
            //redirijo al jugador al et donde peude escribir su alias
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);


        }
    }
    //sobrescribo el botón de ir hacia atrás para que no se pueda salir ni retroceder en los niveles
    @Override
    public void onBackPressed(){

    }


}