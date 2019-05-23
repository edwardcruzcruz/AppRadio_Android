package com.innovasystem.appradio.Classes.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovasystem.appradio.Activities.HomeActivity;
import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.Classes.RestServices;
import com.innovasystem.appradio.Classes.SessionConfig;
import com.innovasystem.appradio.Fragments.ChatFragment;
import com.innovasystem.appradio.Fragments.SegmentoInfoFragment;
import com.innovasystem.appradio.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmisoraHomeAdapter extends  RecyclerView.Adapter<EmisoraHomeAdapter.ViewHolder> {
    public Map<Emisora,Segmento> emisoras_dataset;
    public List<Emisora> emisoras_keys;
    public List<Segmento> favoritos;
    Context context;

    public EmisoraHomeAdapter(Map<Emisora,Segmento> mapa_emisoras,List<Segmento> favoritos,Context c){
        this.emisoras_dataset= mapa_emisoras;
        this.emisoras_keys= new ArrayList<>(this.emisoras_dataset.keySet());
        this.context= c;
        this.favoritos= favoritos;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_radio, tv_frecuencia,tv_segmento,tv_horario,tv_txtfavorito;
        ImageButton btn_chat,btn_addfav,btn_deletefav;
        ImageView img_segmento,portada;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_radio= itemView.findViewById(R.id.tv_hitem_radio);
            this.portada=itemView.findViewById(R.id.logo);
            this.tv_frecuencia= itemView.findViewById(R.id.tv_hitem_frecuencia);
            this.tv_segmento= itemView.findViewById(R.id.tv_hitem_prog_actual);
            this.tv_horario= itemView.findViewById(R.id.tv_hitem_horario);
            this.btn_chat= itemView.findViewById(R.id.btn_hitem_chat);
            this.btn_addfav= itemView.findViewById(R.id.btn_hitem_favorito);
            this.btn_deletefav= itemView.findViewById(R.id.btn_favorito_deshacer);
            this.img_segmento= itemView.findViewById(R.id.img_hitem_segmento);
            this.tv_txtfavorito= itemView.findViewById(R.id.tv_hitem_favorito);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_home_emisora_item,parent,false);
        EmisoraHomeAdapter.ViewHolder vh= new EmisoraHomeAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Emisora emisora= this.emisoras_keys.get(i);
        viewHolder.tv_radio.setText(emisora.getNombre());
        viewHolder.tv_frecuencia.setText(emisora.getFrecuencia_dial());
        if(emisora.getNombre().equals("Radio Diblu")){
            viewHolder.portada.setBackground(context.getDrawable(R.drawable.icono_diblu));
        }
        if(emisora.getNombre().equals("Radio Caravana")){
            viewHolder.portada.setBackground(context.getDrawable(R.drawable.logo_radio_caravana1));
            //viewHolder.portada.requestLayout();
            //viewHolder.portada.getLayoutParams().width=170;
        }
        if(this.emisoras_dataset.get(emisora) != null) {
            viewHolder.tv_segmento.setText(this.emisoras_dataset.get(emisora).getNombre());
            viewHolder.tv_horario.setText(String.format("%s - %s",
                    this.emisoras_dataset.get(emisora).getHorarios()[0].getFecha_inicio(),
                    this.emisoras_dataset.get(emisora).getHorarios()[0].getFecha_fin())
            );

            //CODIGO PARA COLOCAR EL FONDO DE LAS TARJETAS DEL HOME
            /*Picasso.with(this.context)
                    .load(this.emisoras_dataset.get(emisora).getImagen())
                    .placeholder(R.drawable.radio_placeholder2)
                    .fit()
                    .centerInside()
                    //.resize(viewHolder.img_segmento.getMaxWidth(),viewHolder.img_segmento.getMaxHeight())
                    //.centerInside()
                    .into(viewHolder.img_segmento);
            */

            if(favoritos!=null && favoritos.contains(this.emisoras_dataset.get(emisora))){
                viewHolder.tv_txtfavorito.setText("• Programa Favorito •");
                viewHolder.btn_addfav.setClickable(false);
                //aquiii revisar el tamaño _________________________________________________________________________
                viewHolder.btn_addfav.setImageResource(R.drawable.heart1);
                //viewHolder.btn_deletefav.setVisibility(View.VISIBLE);
            }
        }
        else{
            viewHolder.tv_segmento.setText("No hay información disponible");
            viewHolder.tv_horario.setVisibility(View.INVISIBLE);
            viewHolder.btn_addfav.setVisibility(View.INVISIBLE);
            viewHolder.tv_txtfavorito.setVisibility(View.INVISIBLE);
            System.out.println("img w: "+viewHolder.img_segmento.getLayoutParams().width);
            System.out.println("img h: "+viewHolder.img_segmento.getLayoutParams().height);
            /*Picasso.with(this.context)
                    .load(emisora.getLogotipo())
                    .placeholder(R.drawable.radio_placeholder2)
                    .fit()
                    //.centerInside()
                    .into(viewHolder.img_segmento);
             */
        }


        viewHolder.btn_chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Emisora emisora= emisoras_keys.get(i);
                Segmento seg=emisoras_dataset.get(emisora);
                if(seg!=null){
                    Bundle args= new Bundle();
                    args.putParcelable("emisora",emisora);
                    args.putParcelable("segmento",seg);
                    ChatFragment fragment= new ChatFragment();
                    fragment.setArguments(args);
                    ((HomeActivity) context).changeFragment(fragment,R.id.frame_container,true);
                    Toast.makeText(context, "Iniciando chat", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No se puede iniciar el chat sin ningun programa activo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.btn_addfav.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                    if(viewHolder.tv_txtfavorito.getText().equals("AGREGAR A FAVORITOS")) {
                        Toast.makeText(context, "agregando a favoritos", Toast.LENGTH_SHORT).show();
                        viewHolder.tv_txtfavorito.setText("• Programa Favorito •");
                        //viewHolder.btn_addfav.setClickable(false);
                        //viewHolder.btn_addfav.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                        viewHolder.btn_addfav.setImageResource(R.drawable.heart1);
                        RestServices.agregarFavorito(context, SessionConfig.getSessionConfig(context).getValue(SessionConfig.userEmail),emisoras_dataset.get(emisora).getId());
                    }
                    else{
                        Toast.makeText(context, "Quitando de favoritos", Toast.LENGTH_SHORT).show();
                        viewHolder.tv_txtfavorito.setText("AGREGAR A FAVORITOS");
                        //viewHolder.btn_addfav.setClickable(false);
                        //viewHolder.btn_addfav.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                        viewHolder.btn_addfav.setImageResource(R.drawable.heart2);
                        RestServices.quitarFavorito(context, SessionConfig.getSessionConfig(context).getValue(SessionConfig.userEmail),emisoras_dataset.get(emisora).getId());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return emisoras_dataset.size();
    }


}
