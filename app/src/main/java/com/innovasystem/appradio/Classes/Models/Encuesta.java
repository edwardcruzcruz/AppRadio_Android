package com.innovasystem.appradio.Classes.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Encuesta implements Parcelable,Comparable<Encuesta>{
    private Long id;
    private String titulo;
    private String fecha_inicio;
    private String hora_fin;
    private String dia_fin;
    private String activo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha_inicio() {
        return fecha_inicio.substring(0,9)+" "+fecha_inicio.substring(11,18);
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getDia_fin() {
        return dia_fin;
    }

    public void setDia_fin(String dia_fin) {
        this.dia_fin = dia_fin;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", hora_fin='" + hora_fin + '\'' +
                ", dia_fin=" + dia_fin +
                ", activo='" + activo +
                '}';
    }
    public Encuesta(){

    }
    public Encuesta(Parcel in){
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        titulo = in.readString();
        fecha_inicio = in.readString();
        hora_fin = in.readString();
        dia_fin = in.readString();
        activo=in.readString();
    }
    public static final Parcelable.Creator<Emisora> CREATOR = new Parcelable.Creator<Emisora>() {
        @Override
        public Emisora createFromParcel(Parcel in) {
            return new Emisora(in);
        }

        @Override
        public Emisora[] newArray(int size) {
            return new Emisora[size];
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
        parcel.writeString(titulo);
        parcel.writeString(fecha_inicio);
        parcel.writeString(hora_fin);
        parcel.writeString(dia_fin);
        parcel.writeString(activo);
    }

    @Override
    public int compareTo(@NonNull Encuesta encuesta) {
        return this.getTitulo().compareTo(encuesta.getTitulo());
    }
}
