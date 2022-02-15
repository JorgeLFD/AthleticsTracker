package com.example.athleticstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity implements Serializable {

    //private final String TITULO_PANTALLA = "INICIO SESIÓN";
    private EditText editTextMail;
    private EditText editTextContrasenia;
    private Button btnAcceder;
    private Button btnRegistrar;
    private FirebaseFirestore mDatabase;
    private Atleta atleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mDatabase = FirebaseFirestore.getInstance();
        inicializarComponentes();
        registroUsuarios();
        iniciarSesion();
    }

    private void inicializarComponentes() {
        this.editTextMail = (EditText) findViewById(R.id.editTextTextEmail);
        this.editTextContrasenia = (EditText) findViewById(R.id.editTextTextContrasenia);
        this.btnAcceder = (Button) findViewById(R.id.btnAcceder);
        this.btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
    }

    private void registroUsuarios(){
        this.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailUsuario = editTextMail.getText().toString();
                //Si los campos mail y contraseña no están vacios
                if (!editTextMail.getText().toString().isEmpty() && !editTextContrasenia.getText().toString().isEmpty()){

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mailUsuario,editTextContrasenia.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                atleta = new Atleta();
                                atleta.setNombre("Gervasio");
                                atleta.setApellidos("Rrodiguez");
                                atleta.setEmail(mailUsuario);
                                atleta.setFechaNac(new Date());
                                atleta.addRegistro(new Registro("10:03:02", "Plasencia", new Date()));
                                atleta.addRegistro(new Registro("10:10:12", "Navalmoral", new Date()));
                                atleta.addRegistro(new Registro("10:25:18", "Cáceres", new Date()));
                                /*Map<String, Object> docData = new HashMap<>();
                                docData.put("nombre", "Gervasio");
                                docData.put("apellidos", "Rodrigañez");
                                docData.put("club", "Plasencia");
                                docData.put("fechaNacimiento", new Timestamp(new Date()));
                                docData.put("registros", Arrays.asList(registro1, registro2, registro3));*/

                                mDatabase.collection("users").document(mailUsuario).set(atleta);
                                Toast.makeText(getApplicationContext(), "Hola"+mailUsuario, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Registro1Activity.class);
                                intent.putExtra("mailUsuario",mailUsuario);
                                startActivity(intent);
                            }else{
                                mostrarAlertaRegistroUsuario();
                            }
                        }
                    });

                }
            }
        });
    }

    private void iniciarSesion(){
        this.btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailUsuario = editTextMail.getText().toString();
                //Si los campos mail y contraseña no están vacios
                if (!editTextMail.getText().toString().isEmpty() && !editTextContrasenia.getText().toString().isEmpty()){

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mailUsuario,editTextContrasenia.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                /*Registro registro4 = new Registro("9:25:18", "Coria", new Timestamp(new Date()));
                                mDatabase.collection("users").document(mailUsuario).update("registros", FieldValue.arrayUnion(registro4));*/

                                mDatabase.collection("users").document(mailUsuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        try {
                                            Atleta at1 = documentSnapshot.toObject(Atleta.class);
                                            Intent intent = new Intent(getApplicationContext(), Registro1Activity.class);
                                            intent.putExtra("mailUsuario", mailUsuario);
                                            intent.putExtra("atleta", at1);
                                            startActivity(intent);
                                        }catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                mostrarAlertaRegistroUsuario();
                            }
                        }
                    });

                }
            }
        });
    }

    private void mostrarAlertaRegistroUsuario(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error en el inicio de sesión");
        builder.setMessage("Ha habido un error al iniciar sesión con este mail y contraseña");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}