package com.velasuez.tutorial1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity implements IAxiliarPersona {

    RecyclerView idrecyclerview;
    ArrayList<Persona> personaArrayList;
    SqlLite sqlLite;

    private PersonaAdapter personaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        idrecyclerview = findViewById(R.id.idrecyclerview);
        personaArrayList = new ArrayList<>();
        sqlLite = new SqlLite(this, "persona", null, 1);
        personaAdapter = new PersonaAdapter(this, personaArrayList);

        RecyclerView recyclerView = findViewById(R.id.idrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(personaAdapter);
        mostrarDatos();


    }

    public void mostrarDatos() {
        SQLiteDatabase sqLiteDatabase = sqlLite.getReadableDatabase();
        Persona persona = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM persona", null);
        while (cursor.moveToNext()) {
            persona = new Persona();
            persona.setCodigo(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setApellidos(cursor.getString(2));
            persona.setEdad(cursor.getString(3));
            personaAdapter.agregarPersona(persona);
        }
    }


    @Override
    public void OpcionEditar(Persona persona) {
        Intent intent = new Intent(MostrarActivity.this, EditarActivity.class);
        intent.putExtra("persona", persona);
        startActivity(intent);
    }

    @Override
    public void OpcionEliminar(final Persona persona) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Mensaje");
        alerta.setMessage("Esta seguro que desea Eliminar? " + persona.getNombre() + " " + persona.getApellidos());
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPersona(persona);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();

    }

    private void eliminarPersona(Persona persona) {

        SqlLite sqlLite = new SqlLite(this, "persona", null, 1);
        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();
        String codigo = String.valueOf(persona.getCodigo());
        if(!codigo.isEmpty()){
            sqLiteDatabase.delete("persona","codigo="+codigo,null);
            Toast.makeText(this, "Se Elimino Correctamente", Toast.LENGTH_SHORT).show();
            personaAdapter.eliminarPersona(persona);
            sqLiteDatabase.close();
        }else{
            Toast.makeText(this, "No se ha podido eliminar ", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.item_buscar,menu);
       MenuItem buscars=menu.findItem(R.id.idbuscar);
       SearchView searchView=(SearchView)MenuItemCompat.getActionView(buscars);
       buscar(searchView);
       return true;

    }

    private void buscar(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(personaAdapter!=null)
                    personaAdapter.getFilter().filter((newText));
                    return true;
                }
        });
    }


}
