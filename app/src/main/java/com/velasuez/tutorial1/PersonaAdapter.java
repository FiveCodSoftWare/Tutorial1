package com.velasuez.tutorial1;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonaAdapter extends RecyclerView.Adapter<PersonaAdapter.personaView> implements Filterable {

    private List<Persona> personaList = new ArrayList<>();
    private Context context;

    private ArrayList<Persona> personaArrayList;


    private IAxiliarPersona iAxiliarPersona;


    public PersonaAdapter(IAxiliarPersona iAxiliarPersona, ArrayList<Persona> personaList) {
        this.iAxiliarPersona = iAxiliarPersona;
        this.personaList = personaList;
        this.personaArrayList = personaList;
    }

    @NonNull
    @Override
    public personaView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mostrar, viewGroup, false);
        return new personaView(view);

    }

    @Override
    public void onBindViewHolder(personaView personaView, int i) {
        Persona persona = personaList.get(i);
        personaView.txtcodigoMostrar.setText(String.valueOf(persona.getCodigo()));
        personaView.txtnombreMostrar.setText(persona.getNombre());
        personaView.txtapellidosMostrar.setText(persona.getApellidos());
        personaView.txtedadMostrar.setText(persona.getEdad());
        personaView.btnEditar.setOnClickListener(new eventoEditar(persona));
        personaView.btnEliminar.setOnClickListener(new eventoEliminar(persona));
    }


    @Override
    public int getItemCount() {
        return personaList.size();
    }


    public void agregarPersona(Persona persona) {
        personaList.add(persona);
        this.notifyDataSetChanged();
    }

    public void eliminarPersona(Persona persona) {
        personaList.remove(persona);
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String palabra = constraint.toString();

                if (palabra.isEmpty()) {
                    personaList = personaArrayList;
                } else {
                    ArrayList<Persona> filtrarLista = new ArrayList<>();
                    for (Persona persona : personaArrayList) {
                        if (persona.getNombre().toLowerCase().contains(constraint)) {
                            filtrarLista.add(persona);
                        }
                    }
                    personaList = filtrarLista;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = personaList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                personaList = (ArrayList<Persona>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class eventoEditar implements View.OnClickListener {

        private Persona persona;

        public eventoEditar(Persona persona) {
            this.persona = persona;
        }

        @Override
        public void onClick(View v) {
            iAxiliarPersona.OpcionEditar(persona);
        }
    }


    class eventoEliminar implements View.OnClickListener {
        private Persona persona;

        public eventoEliminar(Persona persona) {
            this.persona = persona;
        }

        @Override
        public void onClick(View v) {
            iAxiliarPersona.OpcionEliminar(persona);
        }
    }


    public class personaView extends RecyclerView.ViewHolder {
        private TextView txtcodigoMostrar, txtnombreMostrar, txtapellidosMostrar, txtedadMostrar;
        private Button btnEditar, btnEliminar;

        public personaView(@NonNull View itemView) {
            super(itemView);
            txtcodigoMostrar = itemView.findViewById(R.id.txtcodigoMostrar);
            txtnombreMostrar = itemView.findViewById(R.id.txtNombreMostrar);
            txtapellidosMostrar = itemView.findViewById(R.id.txtApellidosMostrar);
            txtedadMostrar = itemView.findViewById(R.id.txtEdadMostrar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

}
