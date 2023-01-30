package com.example.sums;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Nivel1 extends AppCompatActivity {

    private TextView tv_nombre, tv_puntuacion;
    private ImageView iv_Auno, iv_Ados, iv_vidas;
    private EditText et_respuesta;

    int score, numAletorio_uno, numAletorio_dos, resultado, vidas = 3;
    String nombre, score_text, vidas_txt;

    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);

        Toast.makeText(this, "Nivel 1 - Sumas básicas", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.textView_player);
        tv_puntuacion = (TextView) findViewById(R.id.textView_puntuacion);
        iv_Auno = (ImageView) findViewById(R.id.imageView_num1);
        iv_Ados = (ImageView) findViewById(R.id.imageView_num2);
        iv_vidas = (ImageView) findViewById(R.id.imageView_corazones);
        et_respuesta = (EditText) findViewById(R.id.editTextNumber);

        nombre = getIntent().getStringExtra("player");
        tv_nombre.setText("Player: "+nombre);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



    }
}