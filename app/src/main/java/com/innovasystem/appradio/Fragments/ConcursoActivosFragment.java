package com.innovasystem.appradio.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.innovasystem.appradio.Classes.Adapters.ConcursosActivosAdapter;
import com.innovasystem.appradio.Classes.Adapters.SegmentosAdapter;
import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.Encuesta;
import com.innovasystem.appradio.Classes.Models.Horario;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.Classes.RestServices;
import com.innovasystem.appradio.Classes.SessionConfig;
import com.innovasystem.appradio.R;
import com.innovasystem.appradio.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*Fragmento que representa la ventana de seleccion de segmentos de la emisora */
public class ConcursoActivosFragment extends Fragment {

    /* Variables de referencia de las views de la ventana */
    RecyclerView rv_segmentos_concurso;
    ProgressBar progressBar;
    Emisora emisora;
    AsyncTask tarea;
    TextView tv_mensaje;
    public ConcursoActivosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_concursos_titulo, container, false);
        rv_segmentos_concurso= root.findViewById(R.id.rv_segmentos_concurso);
        tv_mensaje= root.findViewById(R.id.tv_mensaje_segmentos_concurso);
        progressBar= root.findViewById(R.id.progressBar_concurso);

        rv_segmentos_concurso.setHasFixedSize(true);
        RecyclerView.LayoutManager lmanager= new LinearLayoutManager(getContext());
        rv_segmentos_concurso.setLayoutManager(lmanager);

        emisora= EmisoraContentFragment.emisora;

        tarea=new RestFetchConcursoActivoTask().execute();
        //System.out.println("hey ------------------------------------------------------------------------------------------------------------"+nuevo.getStatus());
        return root;


    }

    @Override
    public void onDetach() {
        super.onDetach();
        tarea.cancel(true);
    }
    /* ====== Listeners de botones =======*/

    /**
     * Listener que maneja el evento del boton de reproduccion de emisora en la parte de seleccion
     * de segmentos
     */


    /**
     * Esta clase interna realiza el trabajo de detectar la conexion a internet
     * del telefono, si hay una conexion activa, entonces inicia el reproductor
     */
    private class DetectConnectionTask extends AsyncTask<Object,Object,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... objects) {
            return Utils.isActiveInternet(null);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result == true );
                //startMediaPlayer(emisora.getUrl_streaming());
            else{
                //NotificationManagement.notificarError("AppRadio - Error de Reproduccion","No se puede conectar al servidor de la radio",getActivity().getApplicationContext());
                Toast.makeText(getContext(), "No se puede reproducir la emisora debido a que no tiene internet," +
                        "revise su conexion", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Esta clase interna realiza el trabajo de extraer los segmentos de la emisora seleccionada en
     * el dia actual
     */
    private class RestFetchConcursoActivoTask extends AsyncTask<Void,Void,Void>{
        List<Segmento> Segmentos;
        List<Integer> PosicionesSegmentosConcursosActivos;
        List<List<Encuesta>> encuestas;
        List<Encuesta> encuestas_resultado;
        List<Segmento> segmentos_resultado;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            encuestas=new ArrayList<>();
            segmentos_resultado=new ArrayList<>();
            Segmentos=RestServices.consultarSegmentosToday(getActivity().getApplicationContext());
            for (int i = 0 ; i < Segmentos.size() ; i++){
                List<Encuesta> encuesta=RestServices.consultarConcursos(getActivity().getApplicationContext(),Segmentos.get(i).getId().intValue());
                if(encuesta.size()!=0){
                    for(int j = 0 ; j < encuesta.size() ; j++){
                        segmentos_resultado.add(Segmentos.get(i));
                    }
                    encuestas.add(encuesta);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            System.out.println("IMPRIMIENDO RESULTADO_______");
            if(encuestas == null){
                Toast.makeText(getContext(), "Ocurrio un error con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                tv_mensaje.setVisibility(View.VISIBLE);
                return;
            }

            if(encuestas.size() == 0){
                tv_mensaje.setVisibility(View.VISIBLE);
                return;
            }
            encuestas_resultado=new ArrayList<>();
            int cont=0;
            PosicionesSegmentosConcursosActivos=new ArrayList<>();
            for (int i = 0 ; i < encuestas.size() ; i++) {
                for (int j = 0; j < encuestas.get(i).size(); j++){

                    if(encuestas.get(i).get(j).getActivo().equals("A")){
                        PosicionesSegmentosConcursosActivos.add(cont);
                        encuestas_resultado.add(encuestas.get(i).get(j));
                    }
                    cont++;
                }
            }
            List<Segmento> copia=new ArrayList<>(segmentos_resultado);
            segmentos_resultado.clear();
            for(int i =0; i<PosicionesSegmentosConcursosActivos.size(); i++) {
                segmentos_resultado.add(copia.get(PosicionesSegmentosConcursosActivos.get(i)));
            }
            /*
            Map<Horario,Segmento> mapa_segmentos=new TreeMap<>();
            for (int i = 0; i < listaSegmentos.size(); i++) {
                Segmento segmento =  listaSegmentos.get(i);
                for (int j = 0; j < segmento.getHorarios().length; j++) {
                     Horario h = segmento.getHorarios()[j];
                     mapa_segmentos.put(h,segmento);
                }
            }

            for(Horario hor: mapa_segmentos.keySet()){
                System.out.println("-->horario: " + hor.getFecha_inicio() + " - " + hor.getFecha_fin());
            }
            */
            ConcursosActivosAdapter segmentoAdapter=new ConcursosActivosAdapter(getContext(),encuestas_resultado,segmentos_resultado);
            rv_segmentos_concurso.setAdapter(segmentoAdapter);
            rv_segmentos_concurso.getAdapter().notifyDataSetChanged();
        }
    }
}
