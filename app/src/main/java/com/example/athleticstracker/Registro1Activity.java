package com.example.athleticstracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Registro1Activity extends AppCompatActivity {

    private TextView textViewUsuario;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);
        obtenerDatos();
    }

    private void obtenerDatos(){
        this.textViewUsuario = (TextView) findViewById(R.id.textViewUsuario);
        bundle = getIntent().getExtras();
        textViewUsuario.setText(bundle.getString("mailUsuario"));
        try {
            Atleta atleta = (Atleta) getIntent().getSerializableExtra("atleta");
            String nombre = atleta.getNombre();
            Toast.makeText(getApplicationContext(), "hola "+nombre, Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //mostrarDatosAtleta(atleta);

    }

    private void mostrarDatosAtleta(Atleta atleta){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Datos del atleta");
        builder.setMessage("Nombre: "+atleta.getNombre()+" Apellidos: "+atleta.getApellidos());
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}