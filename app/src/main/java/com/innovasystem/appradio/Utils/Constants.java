package com.innovasystem.appradio.Utils;

public class Constants {

    public static final String serverDomain = "http://etcruz.pythonanywhere.com";
    //public static final String serverDomain = "http://192.168.1.15:8000";

    //Uri para servicios de logueo
    public static final String uriLogIn = "/api/rest-auth/login/";
    public static final String uriRegister = "/api/rest-auth/register/";
    public static final String uriLogOut = "/api/rest-auth/logout/";
    public static final String uriResetPass = "/api/rest-auth/password/reset/";
    public static final String uriHora= "/api/hora_actual/";
    public static final String uriEmisoras= "/api/emisoras/";
    public static final String uriSegmentos= "/api/segmentos";
    public static final String uripreguntas= "/api/preguntasEncuesta/%d";
    public static final String urialternativas= "/api/alternativaspregunta/%d";
    public static final String uriSegmentosEmisora= "/api/%d/segmentos";
    public static final String uriSegmentosConcurso= "/api/segmentos/%d/encuestas/1";
    public static final String uriSegmentosEncuesta= "/api/segmentos/%d/encuestas/0";
    public static final String uriSegmentosDelDia= "/api/segmentos/today";
    public static final String uriSegmentosDelDiaEmisora= "/api/emisoras/%d/segmentos/today";
    public static final String uriTelefonosEmisora= "/api/emisoras/%d/telefonos";
    public static final String uriRedesEmisora= "/api/emisoras/%d/redes_sociales";
    public static final String uriLocutoresSegmento= "/api/segmentos/%d/locutores";
    public static final String uriImagenesSegmento= "/api/imagenes/%d";
    public static final String uriVideosSegmento= "/api/videos/%d";
    public static final String uriFavoritos= "/api/favoritos/%s";
    public static final String uriAgregarFavoritos= "/api/favoritos_create/";
    public static final String uriAgregarFavoritosFalse= "/api/favoritos_create/%d/%s";
    public static final String uriQuitarFavoritosFalse= "/api/favoritos/delete/%d/%s";
    public static final String uriPasswordReset= "/password_reset/";
    public static final String USER_AGENT = "Mozilla/5.0";
}
