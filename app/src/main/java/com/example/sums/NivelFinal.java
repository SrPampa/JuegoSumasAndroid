package com.example.sums;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NivelFinal extends AppCompatActivity {
    private TextView tv_nombre, tv_puntuacion;
    private ImageView iv_Auno, iv_Ados, iv_vidas, iv_signo;
    private EditText et_respuesta;

    int score, numAletorio_uno, numAletorio_dos, resultado, vidas;
    String nombre, score_text, vidas_txt;

    String numero[] = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel_final);

        Toast.makeText(this, "Modo Infinito", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.textView_player);
        tv_puntuacion = (TextView) findViewById(R.id.textView_puntuacion);
        iv_Auno = (ImageView) findViewById(R.id.imageView_num1);
        iv_Ados = (ImageView) findViewById(R.id.imageView_num2);
        iv_vidas = (ImageView) findViewById(R.id.imageView_corazones);
        iv_signo = (ImageView) findViewById(R.id.imageView_signo);
        et_respuesta = (EditText) findViewById(R.id.editTextNumber);

        nombre = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Player: " + nombre);

        score_text = getIntent().getStringExtra("score");
        score = Integer.parseInt(score_text);
        tv_puntuacion.setText("Puntuación: " + score);

        vidas_txt = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(vidas_txt);
        if (vidas == 3) {
            iv_vidas.setImageResource(R.drawable.tresvidas);
        }
        if (vidas == 2) {
            iv_vidas.setImageResource(R.drawable.dosvidas);
        }
        if (vidas == 1) {
            iv_vidas.setImageResource(R.drawable.unavida);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        numAleatorio();
    }

    public void comparar(View view) {

        String respuesta = et_respuesta.getText().toString();
        if (!respuesta.equals("")) {
            int respuesta_jugador = Integer.parseInt(respuesta);
            if (resultado == respuesta_jugador) {
                score++;
                tv_puntuacion.setText("Puntuación: " + score);
            } else {
                vidas--;
                switch (vidas) {
                    case 2:
                        Toast.makeText(this, "Te quedan dos vidas", Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this, "Te quedan una vida", Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this, "Has perdido todas tus vidas. Fin de partida", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
            et_respuesta.setText("");
            numAleatorio();
            dbDatos();

        } else {
            Toast.makeText(this, "Escribe tu respuesta", Toast.LENGTH_SHORT).show();
        }

    }

    public void numAleatorio() {

        int operacion = (int) (Math.random() * 6);
        numAletorio_uno = (int) (Math.random() * 10);
        numAletorio_dos = (int) (Math.random() * 10);

        if (score % 5 == 0) { //cada 5 puntos añado una multiplicación en este modo infinito para añadir una dificultad más
            resultado = numAletorio_uno * numAletorio_dos;
            iv_signo.setImageResource(R.drawable.multiplicacion);
        } else {
            switch (operacion) { //con este switch controlo el porcentaje de veces que sale cada operación
                case 0:
                case 1:
                case 2:
                    resultado = numAletorio_uno - numAletorio_dos;
                    iv_signo.setImageResource(R.drawable.resta);
                    break; //para la resta 3 de cada 5 por la necesidad de utilizar la recursividad si el resultado es <0, que tenga mas posibilidades
                case 3:
                case 4:
                    resultado = numAletorio_uno + numAletorio_dos;
                    iv_signo.setImageResource(R.drawable.suma);
                    break; //2 de 5 para la suma
                case 5:
                    resultado = numAletorio_uno * numAletorio_dos;
                    iv_signo.setImageResource(R.drawable.multiplicacion);
                    break; // 1 de 5 para la multiplicación porque ya va a salir cada 5 niveles si o si
                default:

            }
        }

        if (resultado >= 0) { //con esto controlo que la resta no de negativo
            for (int i = 0; i < numero.length; i++) {
                int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                if (numAletorio_uno == i) {
                    iv_Auno.setImageResource(id);
                }
                if (numAletorio_dos == i) {
                    iv_Ados.setImageResource(id);
                }
            }
        } else {
            numAleatorio();
        }

    }

    public void dbDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor consulta = db.rawQuery("SELECT * FROM score WHERE puntuacion = (SELECT max(puntuacion) FROM score)", null);
        //hago una comprobación de si ha habido respuesta a la consulta
        if (consulta.moveToFirst()) {
            String temp_nombre = consulta.getString(0);
            String temp_puntuacion = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_puntuacion);

            if (score > bestScore) {
                ContentValues modificacion = new ContentValues();
                modificacion.put("alias", nombre);
                modificacion.put("puntuacion", score);

                db.update("score", modificacion, "puntuacion=" + bestScore, null);
            }
            db.close();
        } else {
            ContentValues insercion = new ContentValues();
            insercion.put("alias", nombre);
            insercion.put("puntuacion", score);

            db.insert("score", null, insercion);
            db.close();

        }
    }

    //deshabilito otra vez el boton de atrás para evitar que se salgan de la app hacia el inicio de forma intencional
    @Override
    public void onBackPressed() {
        //lo dejo vacío para que no haga nada
    }
}