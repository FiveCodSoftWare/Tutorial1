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

public class EditarActivity extends AppCompatActivity {

    private PersonaAdapter personaAdapter;

    private TextInputEditText txtCodigoEditar, txtNombreEditar, txtApellidosEditar, txtEdadEditar;
    private Persona persona;


    private Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        getSupportActionBar().hide();
        txtCodigoEditar = findViewById(R.id.txtCodigoEditar);
        txtNombreEditar = findViewById(R.id.txtNombreEditar);
        txtApellidosEditar = findViewById(R.id.txtApellidosEditar);
        txtEdadEditar = findViewById(R.id.txtEdadEditar);
        btnEditar = findViewById(R.id.btnEditarPersona);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDatos(v);
            }
        });

        persona = (Persona) getIntent().getSerializableExtra("persona");
        llenarDatos();


    }

    // primero llenamos las cajas de texto con los datos para que sean editados

    private void llenarDatos() {
        txtCodigoEditar.setText(String.valueOf(persona.getCodigo()));
        txtCodigoEditar.setEnabled(false);
        txtNombreEditar.setText(persona.getNombre());
        txtApellidosEditar.setText(persona.getApellidos());
        txtEdadEditar.setText(persona.getEdad());

    }


    private void editarDatos(View v) {

        SqlLite sqlLite = new SqlLite(this, "persona", null, 1);

        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();
        Integer codigo = Integer.parseInt(txtCodigoEditar.getText().toString());
        String nombre = txtNombreEditar.getText().toString();
        String apellidos = txtApellidosEditar.getText().toString();
        String edad = txtEdadEditar.getText().toString();

        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("edad", edad);

        sqLiteDatabase.update("persona", values, "codigo=" + codigo, null);
        sqLiteDatabase.close();
        finish();
        Toast.makeText(this, "Datos Editados", Toast.LENGTH_SHORT).show();


// los datos si se editan //falta refrescar
        //en este caso vamos a volver a mostrar el activity

        Intent intent = new Intent(EditarActivity.this, MostrarActivity.class);
        startActivity(intent);

    }


}
