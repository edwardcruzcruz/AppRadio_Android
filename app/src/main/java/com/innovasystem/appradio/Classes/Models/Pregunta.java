package com.innovasystem.appradio.Classes.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Pregunta implements Parcelable,Comparable<Pregunta>{
    private Long id;
    private String contenido;
    private String tipo;
    private String respuesta_c;
    private Long idEncuesta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRespuesta_c() {
        return respuesta_c;
    }

    public void setRespuesta_c(String respuesta_c) {
        this.respuesta_c = respuesta_c;
    }

    public Long getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Long idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", tipo='" + tipo + '\'' +
                ", respuesta='" + respuesta_c + '\'' +
                ", idEncuesta=" + idEncuesta +
                '}';
    }
    public Pregunta(){

    }
    public Pregunta(Parcel in){
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        contenido = in.readString();
        tipo = in.readString();
        respuesta_c = in.readString();
        idEncuesta = in.readLong();
    }
    public static final Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
        @Override
        public Pregunta createFromParcel(Parcel in) {
            return new Pregunta(in);
        }

        @Override
        public Pregunta[] newArray(int size) {
            return new Pregunta[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(contenido);
        parcel.writeString(tipo);
        parcel.writeString(respuesta_c);
        parcel.writeLong(idEncuesta);
    }

    @Override
    public int compareTo(@NonNull Pregunta encuesta) {
        return this.getContenido().compareTo(encuesta.getContenido());
    }
}
