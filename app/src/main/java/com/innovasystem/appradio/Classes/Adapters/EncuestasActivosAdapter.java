package com.innovasystem.appradio.Classes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.innovasystem.appradio.Classes.Models.Alternativa;
import com.innovasystem.appradio.Classes.Models.Encuesta;
import com.innovasystem.appradio.Classes.Models.Pregunta;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.R;

import java.util.List;

public class EncuestasActivosAdapter extends RecyclerView.Adapter<EncuestasActivosAdapter.ViewHolder> {
    Context context;
    List<List<Pregunta>> preguntas;
    List<List<Alternativa>> alternativas;

    public EncuestasActivosAdapter(Context c, List<List<Pregunta>> listadoPreguntasNEncu,List<List<Alternativa>> listadoAlternativas){
        this.context= c;
        this.preguntas=listadoPreguntasNEncu;
        this.alternativas=listadoAlternativas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_encuesta_items, parent, false);
        EncuestasActivosAdapter.ViewHolder vh = new EncuestasActivosAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EncuestasActivosAdapter.ViewHolder vholder, int i) {
        Pregunta preguntasXencuesta=preguntas.get(0).get(i);
        List<Alternativa> alternativass=alternativas.get(i);
        vholder.tv_tituloEncuesta.setText(preguntasXencuesta.getContenido());
        int amount=alternativass.size();
        switch (amount){
            case 1:
                vholder.opcion1.setText(alternativas.get(i).get(0).getContenido());
                break;
            case 2:
                vholder.opcion2.setVisibility(View.VISIBLE);
                vholder.opcion1.setText(alternativas.get(i).get(0).getContenido());
                vholder.opcion2.setText(alternativas.get(i).get(1).getContenido());
                break;
            case 3:
                vholder.opcion2.setVisibility(View.VISIBLE);
                vholder.opcion3.setVisibility(View.VISIBLE);
                vholder.opcion1.setText(alternativas.get(i).get(0).getContenido());
                vholder.opcion2.setText(alternativas.get(i).get(1).getContenido());
                vholder.opcion3.setText(alternativas.get(i).get(2).getContenido());
                break;
            case 4:
                vholder.opcion2.setVisibility(View.VISIBLE);
                vholder.opcion3.setVisibility(View.VISIBLE);
                vholder.opcion4.setVisibility(View.VISIBLE);
                vholder.opcion1.setText(alternativas.get(i).get(0).getContenido());
                vholder.opcion2.setText(alternativas.get(i).get(1).getContenido());
                vholder.opcion3.setText(alternativas.get(i).get(2).getContenido());
                vholder.opcion4.setText(alternativas.get(i).get(3).getContenido());
                break;
            case 5:
                vholder.opcion2.setVisibility(View.VISIBLE);
                vholder.opcion3.setVisibility(View.VISIBLE);
                vholder.opcion4.setVisibility(View.VISIBLE);
                vholder.opcion5.setVisibility(View.VISIBLE);
                vholder.opcion1.setText(alternativas.get(i).get(0).getContenido());
                vholder.opcion2.setText(alternativas.get(i).get(1).getContenido());
                vholder.opcion3.setText(alternativas.get(i).get(2).getContenido());
                vholder.opcion4.setText(alternativas.get(i).get(3).getContenido());
                vholder.opcion5.setText(alternativas.get(i).get(4).getContenido());
                break;

            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return preguntas.get(0).size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_tituloEncuesta;
        RadioButton opcion1,opcion2,opcion3,opcion4,opcion5;
        ImageButton btn_votar;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            opcion1= itemView.findViewById(R.id.radio1);
            opcion2= itemView.findViewById(R.id.radio2);
            opcion3= itemView.findViewById(R.id.radio3);
            opcion4= itemView.findViewById(R.id.radio4);
            opcion5= itemView.findViewById(R.id.radio5);
            tv_tituloEncuesta= itemView.findViewById(R.id.tv_titulo_encuesta);

        }
    }
}
