package com.innovasystem.appradio.Classes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.Encuesta;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.R;

import java.util.List;

public class ConcursosActivosAdapter extends RecyclerView.Adapter<ConcursosActivosAdapter.ViewHolder> {
    List<Encuesta> encuestas_dataset;
    List<Segmento> Segmentos;
    Context context;

    public ConcursosActivosAdapter(Context c, List<Encuesta> lista_encuestas, List<Segmento> lista_segmentos){
        this.context= c;
        this.encuestas_dataset= lista_encuestas;
        this.Segmentos= lista_segmentos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_concurso_items, parent, false);
        ConcursosActivosAdapter.ViewHolder vh = new ConcursosActivosAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ConcursosActivosAdapter.ViewHolder vholder, int i) {
        Encuesta en =encuestas_dataset.get(i);
        Segmento seg= Segmentos.get(i);
        vholder.tv_Concurso.setText(en.getTitulo());
        vholder.radio.setText(seg.getEmisora().getNombre());
        vholder.segmento.setText(seg.getNombre());
    }

    @Override
    public int getItemCount() {
        return Segmentos.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView segmento,radio,tv_Concurso;
        ImageButton btn_ver;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            segmento= itemView.findViewById(R.id.tv_Concursosegmento);
            radio= itemView.findViewById(R.id.tv_ConcursoEmisora);
            tv_Concurso= itemView.findViewById(R.id.tv_Concursoitem_Concurso);

        }
    }
}
