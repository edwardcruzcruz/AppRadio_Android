package com.innovasystem.appradio.Classes.Models;

public class UserChat {
    protected String username;
    protected String mensaje;
    protected String hora;

    public UserChat(String username, String mensaje, String hora) {
        this.username = username;
        this.mensaje = mensaje;
        this.hora = hora;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String imagen) {
        this.username = imagen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String last_name) {
        this.mensaje = last_name;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String fecha_nac) {
        this.hora = fecha_nac;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "Nombre='" + username + '\'' +
                ", Mensaje='" + mensaje + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
