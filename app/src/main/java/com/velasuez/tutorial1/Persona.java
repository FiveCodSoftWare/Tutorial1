package com.velasuez.tutorial1;

import java.io.Serializable;

public class Persona  implements Serializable {

    private  int codigo;
    private String nombre;
    private String apellidos;
    private String edad;

    public Persona() {
    }


    public Persona(int codigo, String nombre, String apellidos, String edad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
