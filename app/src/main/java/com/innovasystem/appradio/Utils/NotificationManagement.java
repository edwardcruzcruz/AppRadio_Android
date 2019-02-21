package com.innovasystem.appradio.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.innovasystem.appradio.R;

public class NotificationManagement {
    /*
    public static void notificarError(String titulo, String mensaje,Context context){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"APPRADIO_CHANNEL");
        mBuilder.setSmallIcon(R.drawable.radio_icon_48);
        mBuilder.setContentTitle(titulo);
        mBuilder.setContentText(mensaje);
        NotificationManager manager=  (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1,mBuilder.build());
    }*/
    public static NotificationCompat.Builder mBuilder;
    public static int mNotificationId=1;
    public static String channelId="my_channnel_01";


    private static int getSmallIcon() {
        return android.R.drawable.stat_notify_chat;
    }

    public static void notificarError(String titulo, String mensaje, Context context){
        NotificationManager mNotificationManager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mBuilder=new NotificationCompat.Builder(context,null);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="CursoApp";

            String descripcion="Notificacion de curso";
            int importance=NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel=new NotificationChannel(channelId,name,importance);
            mChannel.setDescription(descripcion);
            mChannel.enableLights(true);

            mChannel.setLightColor(Color.BLUE);
            // mChannel.enableVibration(true);
            // mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            mNotificationManager.createNotificationChannel(mChannel);
            mBuilder=new NotificationCompat.Builder(context,channelId);
        }

        mBuilder.setSmallIcon(getSmallIcon());
        mBuilder.setContentTitle(titulo);
        mBuilder.setContentText(mensaje);
        mNotificationManager.notify(mNotificationId,mBuilder.build());

    }
}
