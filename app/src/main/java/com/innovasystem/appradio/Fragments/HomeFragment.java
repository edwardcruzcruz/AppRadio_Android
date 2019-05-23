package com.innovasystem.appradio.Fragments;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.circularreveal.CircularRevealLinearLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.innovasystem.appradio.Classes.Adapters.EmisoraHomeAdapter;
import com.innovasystem.appradio.Classes.Adapters.ProgramacionAdapter;
import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.Fecha;
import com.innovasystem.appradio.Classes.Models.Horario;
import com.innovasystem.appradio.Classes.Models.Segmento;
import com.innovasystem.appradio.Classes.RestServices;
import com.innovasystem.appradio.Classes.SessionConfig;
import com.innovasystem.appradio.R;
import com.innovasystem.appradio.Services.RadioStreamService;
import com.innovasystem.appradio.Utils.NotificationManagement;
import com.innovasystem.appradio.Utils.Utils;
import com.wefika.horizontalpicker.HorizontalPicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //Variables de control
    private String streamingActual;
    public static boolean radion_on=true;
    public static boolean muted= false;
    static AsyncTask tarea1,tarea2,tarea3,tarea4,tarea5,tarea6,tarea7;
    String[] ciudades;
    String provinciaActual="";
    String EmisoraActual="";
    Snackbar snackMessage;

    //Layout views
    private RecyclerView rv_home;
    private HorizontalPicker ciudad_picker;
    private ListView listview_programacion;
    private ProgressBar progress_emisoras, progress_programacion;
    private ImageButton btn_apagar, btn_silenciar,btn_next,btn_prev;
    private TextView tv_mensaje_programacion;
    private TextView tv_mensaje_emisoras_envivo;


    public HomeFragment() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
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
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        rv_home= root.findViewById(R.id.rv_home);
        ciudad_picker= root.findViewById(R.id.ciudad_picker);
        listview_programacion= root.findViewById(R.id.listv_programas);
        progress_emisoras= root.findViewById(R.id.progressBar_emisoras);
        progress_programacion= root.findViewById(R.id.progressBar_programacion);
        btn_next=root.findViewById(R.id.btn_der);
        btn_apagar= root.findViewById(R.id.btn_apagar);
        btn_silenciar= root.findViewById(R.id.btn_mute);
        btn_prev=root.findViewById(R.id.iv_izq);
        tv_mensaje_programacion= root.findViewById(R.id.tv_mensaje_home);
        tv_mensaje_emisoras_envivo= root.findViewById(R.id.tv_mensaje_emisoras_envivo);

        tv_mensaje_emisoras_envivo.setVisibility(View.INVISIBLE);
        tv_mensaje_programacion.setVisibility(View.INVISIBLE);

        //inicializando tarea asincronica
        //Inicializacion de la cinta de ciudades
        consultaEmisora(null);
        //tarea3=new Consular_emisora().execute(null);
        //Extraccion de datos de emisoras y programacion
        tarea1=new RestFetchEmisoraHomeTask().execute();
        //System.out.println("heyyyy ---------------------------------------------------------------"+SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora));

        /*List<Emisora> emisoras= RestServices.consultarEmisoras(getContext(),null);
        ArrayList<String> ciudadesEmisoras= new ArrayList<String>();
        for(Emisora e: emisoras){
            if(!ciudadesEmisoras.contains(e.getProvincia().toUpperCase()))
                ciudadesEmisoras.add(e.getProvincia().toUpperCase());
        }

        ciudades= ciudadesEmisoras.toArray(new String[0]);//getResources().getStringArray(R.array.ciudades);
        System.out.println(ciudades);
        SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);//tomar alguna provincia
        ciudad_picker.setValues(ciudades);
        ciudad_picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected(){
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(getContext(), ciudad_picker.getValues()[index], Toast.LENGTH_SHORT).show();
                SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[index]);
                System.out.println("Provincia"+ciudades[index]);

                //SessionConfig.getSessionConfig(getContext()).provincia= ciudades[index];
                //SharedPreferences preferences= getContext().getSharedPreferences("session", MODE_PRIVATE);
                //SharedPreferences.Editor editor = preferences.edit();
                //editor.putString("provincia",ciudades[index]);
                //editor.apply();
                //editor.commit();
                new RestFetchEmisoraHomeTask().execute();
                new RestFetchProgramacionTask().execute();
            }
        });
        String provincia=SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia);
        //System.out.println("hey aca puede ser -----"+provincia+"--------\n");
        int indiceProvincia= Arrays.binarySearch(ciudades, provincia);
        if(indiceProvincia>=0) {
            ciudad_picker.setSelectedItem(indiceProvincia);
            provinciaActual= ciudades[indiceProvincia];
        }
        else{
            ciudad_picker.setSelectedItem(0);
            SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);
            provinciaActual= ciudades[0];
        }

        //Inicializacion de recyclerview para las tarjetas
        final CarouselLayoutManager lmanager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
        lmanager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        rv_home.setLayoutManager(lmanager);
        rv_home.setHasFixedSize(true);
        rv_home.addOnScrollListener(new CenterScrollListener());
        rv_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int itemPos= ((CarouselLayoutManager) recyclerView.getLayoutManager()).getCenterItemPosition();
                    Emisora em=((EmisoraHomeAdapter)recyclerView.getAdapter()).emisoras_keys.get(itemPos);
                    streamingActual= em.getUrl_streaming();
                    if(!streamingActual.equals(RadioStreamService.radioURL) && radion_on && !muted) {
                        Intent intent = new Intent();
                        intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                        intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.CHANGE_PLAYER_TRACK);
                        intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, streamingActual);
                        getActivity().sendBroadcast(intent);
                        Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        SnapHelper snapHelper= new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv_home);

        //Inicializacion de listeners de botones
        btn_apagar.setOnClickListener(btn_apagar_listener);
        btn_silenciar.setOnClickListener(btn_mute_listener);

        if(radion_on){
            btn_apagar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled_left));
        }

        if(muted){
            btn_silenciar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled));
        }

        //Extraccion de datos de emisoras y programacion
        new RestFetchEmisoraHomeTask().execute();
        new RestFetchProgramacionTask().execute();*/

        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tarea1.cancel(true);
        tarea2.cancel(true);
        //tarea3.cancel(true);
        //tarea4.cancel(true);
        //tarea6.cancel(true);
        //tarea7.cancel(true);
    }
    /*----Metodos Utilitarios-----*/

    /**
     * Este metodo permite iniciar el reproductor de streaming del servicio RadioStreaming
     * y realizar el proceso de reproduccion
     *
     * @param url La url del servicio de streaming
     * @return void
     */
    public void startMediaPlayer(String url) {
        Intent intent = new Intent();
        intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
        intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.PLAY_MEDIA_PLAYER);
        intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, url);
        if(getActivity()!=null)
            getActivity().sendBroadcast(intent);
    }
    public void consultaEmisora(final String provincia){
        tarea2=new AsyncTask<String,Void,List<Emisora>>() {
            @Override
            protected List<Emisora> doInBackground(String... strings) {
                List<Emisora> listaEmisoras=RestServices.consultarEmisoras(getContext(),provincia);
                return listaEmisoras;
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onPostExecute(List<Emisora> emisoras) {
                super.onPostExecute(emisoras);
                //List<Emisora> emisoras= RestServices.consultarEmisoras(getContext(),null);
                ArrayList<String> ciudadesEmisoras= new ArrayList<String>();
                for(Emisora e: emisoras){
                    if(!ciudadesEmisoras.contains(e.getProvincia().toUpperCase()))
                        ciudadesEmisoras.add(e.getProvincia().toUpperCase());
                }
                ciudades= ciudadesEmisoras.toArray(new String[0]);//getResources().getStringArray(R.array.ciudades);
                System.out.println(ciudades);
                SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);//tomar alguna provincia
                ciudad_picker.setValues(ciudades);
                ciudad_picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected(){
                    @Override
                    public void onItemSelected(int index) {
                       Toast.makeText(getContext(), ciudad_picker.getValues()[index], Toast.LENGTH_SHORT).show();
                        SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[index]);
                        System.out.println("Provincia"+ciudades[index]);

                        //SessionConfig.getSessionConfig(getContext()).provincia= ciudades[index];
                        //SharedPreferences preferences= getContext().getSharedPreferences("session", MODE_PRIVATE);
                        //SharedPreferences.Editor editor = preferences.edit();
                        //editor.putString("provincia",ciudades[index]);
                        //editor.apply();
                        //editor.commit();
                        new RestFetchEmisoraHomeTask().execute();
                        new RestFetchProgramacionTask().execute();
                    }
                });
                String provincia=SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia);
                //System.out.println("hey aca puede ser -----"+provincia+"--------\n");
                int indiceProvincia= Arrays.binarySearch(ciudades, provincia);
                if(indiceProvincia>=0) {
                    ciudad_picker.setSelectedItem(indiceProvincia);
                    provinciaActual= ciudades[indiceProvincia];
                }
                else{
                    ciudad_picker.setSelectedItem(0);
                    SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);
                    provinciaActual= ciudades[0];
                }

                //Inicializacion de recyclerview para las tarjetas
                //final CarouselLayoutManager lmanager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
                //lmanager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
                //rv_home.setLayoutManager(lmanager);
                rv_home.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                //rv_home.setHasFixedSize(true);
                //rv_home.addOnScrollListener(new CenterScrollListener());
                rv_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE){
                            //System.out.println("aqui esta la posicion o estado del scroll idle"+newState);
                            //int itemPos= ((CarouselLayoutManager) recyclerView.getLayoutManager()).getCenterItemPosition();
                            int itemPos= ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                            Emisora em=((EmisoraHomeAdapter)recyclerView.getAdapter()).emisoras_keys.get(itemPos);
                            SessionConfig.getSessionConfig(getContext()).AsignarEmisora(em.getNombre());
                            if(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora).equals("Radio Caravana")){
                                btn_next.setVisibility(View.VISIBLE);
                                btn_prev.setVisibility(View.GONE);
                            }
                            if(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora).equals("Radio Diblu")){
                                btn_next.setVisibility(View.GONE);
                                btn_prev.setVisibility(View.VISIBLE);
                            }
                            System.out.println(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora));
                            streamingActual= em.getUrl_streaming();
                            if(!streamingActual.equals(RadioStreamService.radioURL) && radion_on && !muted) {
                                new RestFetchProgramacionTask().execute(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora));
                                Intent intent = new Intent();
                                intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                                intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.CHANGE_PLAYER_TRACK);
                                intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, streamingActual);
                                getActivity().sendBroadcast(intent);
                                Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        System.out.println("hey xD"+ dx);
                        //if(dx<0){
                            //rv_home.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
                        //}
                    }
                });
                SnapHelper snapHelper= new LinearSnapHelper();
                snapHelper.attachToRecyclerView(rv_home);

                //Inicializacion de listeners de botones
                btn_prev.setOnClickListener(btn_back_listener);
                btn_next.setOnClickListener(btn_next_listener);
                btn_apagar.setOnClickListener(btn_apagar_listener);
                btn_silenciar.setOnClickListener(btn_mute_listener);


                if(radion_on){
                    //btn_apagar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled_left));
                    btn_apagar.setColorFilter(getContext().getColor(R.color.color_text));
                }

                if(muted){
                    //btn_silenciar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled));
                    btn_silenciar.setColorFilter(getContext().getColor(R.color.color_text2));
                }


            }
        }.execute(provincia);
    }

    /*---------- Listeners ---------*/

    public final View.OnClickListener btn_back_listener=new View.OnClickListener(){
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            int firstVisibleItemIndex = ((LinearLayoutManager)rv_home.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            int lastVisibleItemIndex = ((LinearLayoutManager)rv_home.getLayoutManager()).findLastVisibleItemPosition();
            if (firstVisibleItemIndex > 0) {
                ((LinearLayoutManager)rv_home.getLayoutManager()).smoothScrollToPosition(rv_home,null,firstVisibleItemIndex-1);
            }
            btn_next.setVisibility(View.VISIBLE);
            btn_prev.setVisibility(View.GONE);

            //System.out.println("holaxD"+ firstVisibleItemIndex);
        }
    };
    public final View.OnClickListener btn_next_listener=new View.OnClickListener(){
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            int totalItemCount = rv_home.getAdapter().getItemCount();
            if (totalItemCount <= 0) return;
            int firstVisibleItemIndex = ((LinearLayoutManager)rv_home.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            int lastVisibleItemIndex = ((LinearLayoutManager)rv_home.getLayoutManager()).findLastVisibleItemPosition();
            btn_prev.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.GONE);



            if (lastVisibleItemIndex >= totalItemCount-1){
                //((LinearLayoutManager) rv_home.getLayoutManager()).setReverseLayout(true);
                //((LinearLayoutManager)rv_home.getLayoutManager()).smoothScrollToPosition(rv_home,null,firstVisibleItemIndex-1);

                //((LinearLayoutManager) rv_home.getLayoutManager()).setReverseLayout(false);
                return;
            }
            ((LinearLayoutManager)rv_home.getLayoutManager()).smoothScrollToPosition(rv_home,null,lastVisibleItemIndex+1);
            //System.out.println("holaxD"+ lastVisibleItemIndex);
        }
    };
    /**
     * Listener para el boton de Apagar, Detiene por completo el streaming de la emisora
     */
    private final View.OnClickListener btn_apagar_listener= new View.OnClickListener(){

        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)        @Override
        public void onClick(View view) {
            if(!radion_on){
                System.out.println("Start Playing!!!!!");
                if(Utils.isNetworkAvailable(getContext())) {
                    btn_apagar.setColorFilter(getContext().getColor(R.color.color_text));
                    radion_on = true;
                    tarea6=new StartStreamingTask().execute();
                    if(muted){
                        btn_silenciar.setColorFilter(getContext().getColor(R.color.color_text));
                        muted= false;
                    }
                }
                else{
                    Toast.makeText(getContext(), "No se puede reproducir la emisora debido a que no tiene internet," +
                            "revise su conexion", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                //btn_apagar.setBackground(getContext().getDrawable(R.drawable.round_button_left));
                btn_apagar.setColorFilter(getContext().getColor(R.color.color_text2));
                Intent intent = new Intent();
                intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.STOP_MEDIA_PLAYER);
                getActivity().sendBroadcast(intent);
                radion_on =false;
            }
        }
    };

    private final View.OnClickListener btn_mute_listener= new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if(!muted){
                btn_silenciar.setColorFilter(getContext().getColor(R.color.color_text));
                Intent intent = new Intent();
                intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.PAUSE_MEDIA_PLAYER);
                getActivity().sendBroadcast(intent);
                muted=true;
            }
            else{
                btn_silenciar.setColorFilter(getContext().getColor(R.color.color_text2));
                Intent intent = new Intent();

                if(RadioStreamService.radioURL.equals(streamingActual)) {
                    intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                    intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.RESUME_MEDIA_PLAYER);
                }
                else{
                    intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                    intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.CHANGE_PLAYER_TRACK);
                    intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, streamingActual);
                    Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                }
                getActivity().sendBroadcast(intent);
                muted=false;
            }
        }
    };


    /*---------- Tasks -------------*/
/*
    private class Consular_emisora extends AsyncTask<String,Void,List<Emisora>> {
        @Override
        protected List<Emisora> doInBackground(String... strings) {
            String provincia=strings[0];
            List<Emisora> listaEmisoras=RestServices.consultarEmisoras(getContext(),provincia);
            return listaEmisoras;
        }

        @Override
        protected void onPostExecute(List<Emisora> emisoras) {
            super.onPostExecute(emisoras);
            //List<Emisora> emisoras= RestServices.consultarEmisoras(getContext(),null);
            ArrayList<String> ciudadesEmisoras= new ArrayList<String>();
            for(Emisora e: emisoras){
                if(!ciudadesEmisoras.contains(e.getProvincia().toUpperCase()))
                    ciudadesEmisoras.add(e.getProvincia().toUpperCase());
            }
            ciudades= ciudadesEmisoras.toArray(new String[0]);//getResources().getStringArray(R.array.ciudades);
            System.out.println(ciudades);
            SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);//tomar alguna provincia
            ciudad_picker.setValues(ciudades);
            ciudad_picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected(){
                @Override
                public void onItemSelected(int index) {
                    Toast.makeText(getContext(), ciudad_picker.getValues()[index], Toast.LENGTH_SHORT).show();
                    SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[index]);
                    System.out.println("Provincia"+ciudades[index]);

                    //SessionConfig.getSessionConfig(getContext()).provincia= ciudades[index];
                    //SharedPreferences preferences= getContext().getSharedPreferences("session", MODE_PRIVATE);
                    //SharedPreferences.Editor editor = preferences.edit();
                    //editor.putString("provincia",ciudades[index]);
                    //editor.apply();
                    //editor.commit();
                    tarea4=new RestFetchEmisoraHomeTask().execute();
                    tarea5=new RestFetchProgramacionTask().execute();
                }
            });
            String provincia=SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia);
            //System.out.println("hey aca puede ser -----"+provincia+"--------\n");
            int indiceProvincia= Arrays.binarySearch(ciudades, provincia);
            if(indiceProvincia>=0) {
                ciudad_picker.setSelectedItem(indiceProvincia);
                provinciaActual= ciudades[indiceProvincia];
            }
            else{
                ciudad_picker.setSelectedItem(0);
                SessionConfig.getSessionConfig(getContext()).CrearProvincia(ciudades[0]);
                provinciaActual= ciudades[0];
            }

            //Inicializacion de recyclerview para las tarjetas
            final CarouselLayoutManager lmanager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
            lmanager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
            rv_home.setLayoutManager(lmanager);
            rv_home.setHasFixedSize(true);
            rv_home.addOnScrollListener(new CenterScrollListener());
            rv_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                        int itemPos= ((CarouselLayoutManager) recyclerView.getLayoutManager()).getCenterItemPosition();
                        Emisora em=((EmisoraHomeAdapter)recyclerView.getAdapter()).emisoras_keys.get(itemPos);
                        streamingActual= em.getUrl_streaming();
                        if(!streamingActual.equals(RadioStreamService.radioURL) && radion_on && !muted) {
                            Intent intent = new Intent();
                            intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                            intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.CHANGE_PLAYER_TRACK);
                            intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, streamingActual);
                            getActivity().sendBroadcast(intent);
                            Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                        }
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            SnapHelper snapHelper= new LinearSnapHelper();
            snapHelper.attachToRecyclerView(rv_home);

            //Inicializacion de listeners de botones
            btn_apagar.setOnClickListener(btn_apagar_listener);
            btn_silenciar.setOnClickListener(btn_mute_listener);

            if(radion_on){
                btn_apagar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled_left));
            }

            if(muted){
                btn_silenciar.setBackground(getContext().getDrawable(R.drawable.round_button_enabled));
            }


        }

    }*/
    /**
     * Esta clase asincrona obtiene los datos de emisoras para presentarlos en la pantalla principal
     * como tarjetas
     */
    private class RestFetchEmisoraHomeTask extends AsyncTask<Void,Void,Void> {
        List<Emisora> emisoras;
        List<Segmento> segmentos;
        List<Segmento> favoritos;

        @Override
        protected void onPreExecute() {
            progress_emisoras.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("EXTRAYENDO DATOS");
            favoritos= RestServices.consultarFavoritos(getContext(),SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.userEmail));
            emisoras= RestServices.consultarEmisoras(getContext(),SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia));
            segmentos= RestServices.consultarSegmentosDelDia(getContext(),SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia));
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            progress_emisoras.setVisibility(View.GONE);
            System.out.println("INICIALIZANDO DATASET!!");
            if ((emisoras == null || segmentos == null) && getContext() != null) {
                Toast.makeText(getContext(), "Ocurrio un error con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                tv_mensaje_emisoras_envivo.setVisibility(View.VISIBLE);
                rv_home.setAdapter(null);
                return;
            }
            else if(emisoras.size() == 0 && getContext() != null){
                Toast.makeText(getContext(), "No hay informacion para presentar o ocurrio un error de conexion!", Toast.LENGTH_SHORT).show();
                tv_mensaje_emisoras_envivo.setVisibility(View.VISIBLE);
                rv_home.setAdapter(null);
                return;
            }

            tv_mensaje_emisoras_envivo.setVisibility(View.INVISIBLE);
            System.out.println("DATASETS: " + emisoras + "\n" + segmentos);


            /*
            //codigo para hacer pruebas con emisoras y segmentos de prueba
            emisoras=Utils.generarEmisorasPrueba();
            segmentos= new ArrayList<>();
            for (int i = 0; i < emisoras.size(); i++) {
                segmentos.addAll(Utils.generarSegmentosPrueba(emisoras.get(i).getId()));
            }
            */


            Map<Emisora, Segmento> mapa_emisoras = new TreeMap<>();

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            String horaActual = timeFormatter.format(currentTime.getTime());

            for (int i = 0; i < emisoras.size(); i++) {
                Emisora emisora = emisoras.get(i);
                for (int j = 0; j < segmentos.size(); j++) {
                    Segmento segmento = segmentos.get(j);
                    if (segmento.getIdEmisora()!=null && segmento.getIdEmisora().equals(emisora.getId())) {
                        Horario h = Utils.compararHorarioActual(horaActual, segmento.getHorarios());
                        if (h != null) {
                            segmento.setHorarios(new Horario[]{h});
                            mapa_emisoras.put(emisora, segmento);
                            break;
                        }
                    }
                }
                if (!mapa_emisoras.containsKey(emisora)) {
                    mapa_emisoras.put(emisora, null);
                }
            }

            EmisoraHomeAdapter adapter = new EmisoraHomeAdapter(mapa_emisoras,favoritos, getContext());
            rv_home.setAdapter(adapter);

            Log.i("RV ITEMS: ", "" + mapa_emisoras.size());

            /*Se busca la posicion de la url en el adapter para hacer que el recyclerview se dirija
            * a la posicion de la emisora en reproduccion actualmente
            */
            if(adapter.getItemCount() != 0) {
                if (!RadioStreamService.radioURL.equals("")) {
                    for (int i = 0; i < adapter.emisoras_keys.size(); i++) {
                        if (adapter.emisoras_keys.get(i).getUrl_streaming().equals(RadioStreamService.radioURL)) {
                            rv_home.scrollToPosition(i);
                            streamingActual = adapter.emisoras_keys.get(i).getUrl_streaming();
                            //System.out.println("oyeeeeeee + "+adapter.emisoras_keys.get(i));
                        }
                    }
                    new RestFetchProgramacionTask().execute(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora));

                } else if (!RadioStreamService.radioURL.equals(adapter.emisoras_keys.get(0).getUrl_streaming())) {
                    streamingActual = adapter.emisoras_keys.get(0).getUrl_streaming();
                    SessionConfig.getSessionConfig(getContext()).AsignarEmisora(adapter.emisoras_keys.get(0).getNombre());
                    new RestFetchProgramacionTask().execute(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora));
                    if (radion_on && !muted) {
                        tarea5=new StartStreamingTask().execute();
                    }
                }

                //Verificar si se ha cambiado de provincia
                if (!provinciaActual.equals(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia))) {
                    System.out.println("****** LA PROVINCIA HA CAMBIADO *******!!!!");
                    streamingActual = adapter.emisoras_keys.get(0).getUrl_streaming();
                    if (radion_on && !muted) {
                        Intent intent = new Intent();
                        intent.setAction(RadioStreamService.BROADCAST_TO_SERVICE);
                        intent.putExtra(RadioStreamService.PLAYER_FUNCTION_TYPE, RadioStreamService.CHANGE_PLAYER_TRACK);
                        intent.putExtra(RadioStreamService.PLAYER_TRACK_URL, streamingActual);
                        getActivity().sendBroadcast(intent);
                       Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                    }
                }
            }

            if(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora).equals("Radio Caravana")){
                btn_next.setVisibility(View.VISIBLE);
                btn_prev.setVisibility(View.GONE);
            }
            if(SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.Emisora).equals("Radio Diblu")){
                btn_next.setVisibility(View.GONE);
                btn_prev.setVisibility(View.VISIBLE);
            }

            provinciaActual= SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia);

        }
    }

    /**
     * Esta clase interna realiza el trabajo de detectar la conexion a internet
     * del telefono, si hay una conexion activa, entonces inicia el reproductor
     */
    private class StartStreamingTask extends AsyncTask<Object,Object,Boolean>{

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
            if(result && getActivity() != null){
                Utils.mostrarMensajeSnackBar(getActivity().getWindow().getDecorView().getRootView(),"Conectando al servidor de la emisora....");
                startMediaPlayer(streamingActual);
            }
            else{

                //NotificationManagement.notificarError("AppRadio - Error de Reproduccion","No se puede conectar al servidor de la radio",getActivity().getApplicationContext());
                if(getContext() !=null)
                    Toast.makeText(getContext(), "No se puede reproducir la emisora debido a que no tiene internet," +
                        "revise su conexion", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Clase Asincrona para extraer La info de La programacion del dia y presentarla en una ListView
     */
    private class RestFetchProgramacionTask extends AsyncTask<String,Void,List<Segmento>>{
        Fecha horaActual;
        ArrayList<Segmento> favoritos;

        @Override
        protected void onPreExecute() {
            progress_programacion.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Segmento> doInBackground(String... strings) {
            horaActual= RestServices.consultarHoraActual(getContext());
            String favoritoSession=SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.userEmail);
            favoritos= (ArrayList) RestServices.consultarFavoritos(getContext(), (favoritoSession!=null)?favoritoSession:"default");
            List<Segmento> listaSegmentos = RestServices.consultarSegmentosDelDia(getContext(),SessionConfig.getSessionConfig(getContext()).getValue(SessionConfig.provincia));
            List<Segmento> copia=new ArrayList<>(listaSegmentos);
            listaSegmentos.clear();
            for(int i=0;i<copia.size();i++){
                String emisora=strings[0];
                if(copia.get(i).getEmisora().getNombre().equals(emisora)){
                    listaSegmentos.add(copia.get(i));
                }
            }
            return  listaSegmentos;
        }

        @Override
        protected void onPostExecute(List<Segmento> listaSegmentos) {
            progress_programacion.setVisibility(View.GONE);
            System.out.println("IMPRIMIENDO RESULTADO_______");
            System.out.println(Arrays.toString(listaSegmentos.toArray()));
            System.out.println("FECHA ACTUAL: " + horaActual);
            if(listaSegmentos == null){
                if(getContext() != null)
                    Toast.makeText(getContext(), "Ocurrio un error con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                tv_mensaje_programacion.setVisibility(View.VISIBLE);
                listview_programacion.setAdapter(null);
                //SessionConfig.getSessionConfig(getContext()).AsignarTarea("vacio");
                return;
            }
            else if(listaSegmentos.size()==0){
                if(getContext() != null)
                    Toast.makeText(getContext(), "No hay programacion para presentar o ocurrio algun error!", Toast.LENGTH_SHORT).show();
                tv_mensaje_programacion.setVisibility(View.VISIBLE);
                listview_programacion.setAdapter(null);
                //SessionConfig.getSessionConfig(getContext()).AsignarTarea("vacio");
                return;
            }

            tv_mensaje_programacion.setVisibility(View.INVISIBLE);



            /*
            //CODIGO DE PRUEBA
            List<Emisora> listaEmisoras= Utils.generarEmisorasPrueba();
            listaSegmentos.clear();
            for(Emisora e: listaEmisoras){
                listaSegmentos.addAll(Utils.generarSegmentosPrueba(e));
            }
            Collections.shuffle(listaSegmentos);
            System.out.println("LISta PRUEBA: "+listaSegmentos);
            */

            Map<Horario,Set<Segmento>> mapa_segmentos=new TreeMap<>();
            for (int i = 0; i < listaSegmentos.size(); i++) {
                Segmento segmento =  listaSegmentos.get(i);
                for (int j = 0; j < segmento.getHorarios().length; j++) {
                    Horario h = segmento.getHorarios()[j];
                    if(mapa_segmentos.containsKey(h)){
                        mapa_segmentos.get(h).add(segmento);
                    }
                    else {
                        mapa_segmentos.put(h, new HashSet<>());
                        mapa_segmentos.get(h).add(segmento);
                    }
                }
            }

            ArrayList<Horario> horarios= new ArrayList<>();
            ArrayList<Segmento> segmentos= new ArrayList<>();

            for(Horario h: mapa_segmentos.keySet()){
                for(Segmento seg: mapa_segmentos.get(h)){
                    horarios.add(h);
                    segmentos.add(seg);
                }
            }

            ProgramacionAdapter adapter= new ProgramacionAdapter(getContext(),horarios,segmentos,horaActual,favoritos);
            listview_programacion.setAdapter(adapter);
        }

    }
}
