package com.example.athleticstracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Atleta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellidos;
    private String email;
    private Date fechaNac;
    private String club;
    private List<Registro> registros;

    public Atleta() {
        this.nombre = "";
        this.apellidos = "";
        this.email = "";
        this.fechaNac = null;
        this.club = "";
        this.registros = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void addRegistro(Registro registro){
        this.registros.add(registro);
    }


}
