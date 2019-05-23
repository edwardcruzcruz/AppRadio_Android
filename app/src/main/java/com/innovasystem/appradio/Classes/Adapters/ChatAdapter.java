package com.innovasystem.appradio.Classes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.innovasystem.appradio.Classes.Models.Encuesta;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.Classes.Models.UserChat;
import com.innovasystem.appradio.Classes.SessionConfig;
import com.innovasystem.appradio.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<UserChat> chats;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    Context context;

    public ChatAdapter(Context c, List<UserChat> usuarios){
        this.context= c;
        this.chats= usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(i==MSG_TYPE_RIGHT){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            ChatAdapter.ViewHolder vh = new ChatAdapter.ViewHolder(v);
            return vh;
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            ChatAdapter.ViewHolder vh = new ChatAdapter.ViewHolder(v);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder vholder, int i) {
        UserChat chat =chats.get(i);
        if(!chats.get(i).getUsername().equals(SessionConfig.getSessionConfig(context).getValue(SessionConfig.userEmail))){
            vholder.Nombre.setText(chat.getUsername());
        }
        vholder.Mensaje.setText(chat.getMensaje());
        vholder.hora.setText(chat.getHora());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView Nombre,Mensaje,hora;
        ImageButton btn_ver;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Nombre= itemView.findViewById(R.id.usuario);
            Mensaje= itemView.findViewById(R.id.mensaje_usuario);
            hora= itemView.findViewById(R.id.hora_msg_usuario);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getUsername().equals(SessionConfig.getSessionConfig(context).getValue(SessionConfig.userEmail))){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
