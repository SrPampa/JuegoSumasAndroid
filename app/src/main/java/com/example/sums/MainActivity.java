package com.example.sums;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
                id = getResources().getIdentifier("escuadra", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 4:
                id = getResources().getIdentifier("cartabon", "drawable", getPackageName());
                iv_personaje.setImageResource(id);
                break;
            default:

        }
    }
}