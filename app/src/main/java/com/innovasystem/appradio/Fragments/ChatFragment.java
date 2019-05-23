package com.innovasystem.appradio.Fragments;


import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.innovasystem.appradio.Classes.Adapters.ChatAdapter;
import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.Classes.Models.UserChat;
import com.innovasystem.appradio.Classes.SessionConfig;
import com.innovasystem.appradio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView radio_frecuencia,segmento;
    RecyclerView rv_chat;
    AsyncTask tarea,tarea1;
    Emisora em;
    Segmento seg;
    ImageButton SendButton;
    EditText TextoMsg;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_chat, container, false);
        em= getArguments().getParcelable("emisora");
        seg=getArguments().getParcelable("segmento");
        rv_chat= root.findViewById(R.id.recyclerView_chat);
        radio_frecuencia=root.findViewById(R.id.tv_radio_frecuencia);
        SendButton=root.findViewById(R.id.enviar_msg_chat);
        TextoMsg=root.findViewById(R.id.mensaje_actual);
        segmento=root.findViewById(R.id.tv_segmento_chat);
        segmento.setText(seg.getNombre());
        radio_frecuencia.setText(em.getNombre()+"-"+em.getFrecuencia_dial());

        rv_chat.setHasFixedSize(true);
        RecyclerView.LayoutManager lmanager= new LinearLayoutManager(getContext());
        rv_chat.setLayoutManager(lmanager);

        tarea=new RestFetchatTask().execute();
        return root;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        tarea.cancel(true);
        tarea1.cancel(true);
    }
    public final View.OnClickListener btn_send=new View.OnClickListener(){
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            if (TextoMsg.equals(null)) {
                Toast.makeText(getContext(), "No ha escrito nada", Toast.LENGTH_SHORT).show();
            }else{
                //tarea1=new RestFetchatTask().execute("Holaaaa Soy nuevoo");
            }
        }
    };

    private class RestFetchatTask extends AsyncTask<Void,Void,Void> {
        List<UserChat> usuarios;
        /*List<Integer> PosicionesSegmentosConcursosActivos;
        List<List<Encuesta>> encuestas;
        List<Encuesta> encuestas_resultado;
        List<Segmento> segmentos_resultado;*/

        @Override
        protected Void doInBackground(Void... voids) {
            usuarios=new ArrayList<>();
            //if(!strings[0].equals(null)){
                //usuarios.add(new UserChat(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.userEmail),strings[0],"just now"));
            //}else{
                usuarios.add(new UserChat("Edward","Hola a todos y bienvenidos a nuestra programación .....","10:00"));
                usuarios.add(new UserChat("Andres","Hola escucho todos los dias su programación ....","10:02"));
            usuarios.add(new UserChat("appradio","Hola soy nuevo ....","Just now"));
            //}
            /*encuestas=new ArrayList<>();
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
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            //progressBar.setVisibility(View.GONE);
            System.out.println("IMPRIMIENDO RESULTADO_______");
            if(usuarios == null){
                Toast.makeText(getContext(), "Ocurrio un error con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                //tv_mensaje.setVisibility(View.VISIBLE);
                return;
            }

            if(usuarios.size() == 0){
                Toast.makeText(getContext(), "Inicie Conversacion", Toast.LENGTH_SHORT).show();
                //tv_mensaje.setVisibility(View.VISIBLE);
                return;
            }
            /*encuestas_resultado=new ArrayList<>();
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
            }*/

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
            ChatAdapter chatAdapter=new ChatAdapter(getContext(),usuarios);
            rv_chat.setAdapter(chatAdapter);
            rv_chat.getAdapter().notifyDataSetChanged();
        }
    }
}

