package com.velasuez.tutorial1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextInputEditText txtCodigo, txtNombre, txtApellidos, txtEdad;

    private Button btnGuardar, btnMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        txtCodigo = (TextInputEditText) findViewById(R.id.txtCodigo);
        txtNombre = (TextInputEditText) findViewById(R.id.txtNombre);
        txtApellidos = (TextInputEditText) findViewById(R.id.txtApellidos);
        txtEdad = (TextInputEditText) findViewById(R.id.txtEdad);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(txtCodigo.getText().toString().equals("")|| txtNombre.getText().toString().equals("")||txtApellidos.getText().toString().equals("") || txtEdad.getText().toString().equals("")){
                 validarTextos();
             }else{
                 GuardarDatos(v);
                 limpiarTextos();
                 Toast.makeText(MainActivity.this, "Datos Registrados", Toast.LENGTH_SHORT).show();
             }
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MostrarActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Datos Mostrados", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void GuardarDatos(View v) {

        SqlLite sqlLite = new SqlLite(this, "persona", null, 1);
        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();

        int codigo = Integer.parseInt(txtCodigo.getText().toString());
        String nombre = txtNombre.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String edad = txtEdad.getText().toString();


        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("edad", edad);

        Long resultado = sqLiteDatabase.insert("persona", null, values);
        Toast.makeText(this, "Resultado: " + resultado, Toast.LENGTH_SHORT).show();
    }

    private void limpiarTextos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
    }

    public void validarTextos() {
        if (txtCodigo.getText().toString().equals("")) {
            txtCodigo.setText("Falta el codigo");
        }
        if (txtNombre.getText().toString().equals("")) {
            txtNombre.setText("Falta el Nombre");
        }
        if (txtApellidos.getText().toString().equals("")) {
            txtApellidos.setText("Falta el Apellidos");
        }
        if (txtEdad.getText().toString().equals("")) {
            txtEdad.setText("Falta el Edad");
        }
    }


}
