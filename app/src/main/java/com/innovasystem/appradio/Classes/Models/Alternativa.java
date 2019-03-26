package com.innovasystem.appradio.Classes.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Alternativa implements Parcelable,Comparable<Alternativa>{
    private Long id;
    private String alternativa;
    private Long idPregunta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return alternativa;
    }

    public void setContenido(String contenido) {
        this.alternativa = contenido;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public String toString() {
        return "Alternativa{" +
                "id=" + id +
                ", contenido='" + alternativa + '\'' +
                ", idPregunta=" + idPregunta +
                '}';
    }
    public Alternativa(){

    }
    public Alternativa(Parcel in){
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        alternativa = in.readString();
        idPregunta = in.readLong();
    }
    public static final Creator<Alternativa> CREATOR = new Creator<Alternativa>() {
        @Override
        public Alternativa createFromParcel(Parcel in) {
            return new Alternativa(in);
        }

        @Override
        public Alternativa[] newArray(int size) {
            return new Alternativa[size];
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
        parcel.writeString(alternativa);
        parcel.writeLong(idPregunta);
    }

    @Override
    public int compareTo(@NonNull Alternativa encuesta) {
        return this.getContenido().compareTo(encuesta.getContenido());
    }
}
