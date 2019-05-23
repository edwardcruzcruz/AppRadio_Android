package com.innovasystem.appradio.Fragments;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovasystem.appradio.Activities.HomeActivity;
import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.Classes.Models.RedSocialEmisora;
import com.innovasystem.appradio.Classes.Models.TelefonoEmisora;
import com.innovasystem.appradio.Classes.RestServices;
import com.innovasystem.appradio.R;

import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmisoraInfoFragment extends Fragment {


    JustifiedTextView tv_descripcion_emisora;
    TextView tv_web_emisora,tv_telef_emisora,tv_ubicacion_emisora,tv_instagram,tv_facebook,tv_twitter;
    LinearLayout layoutManager,redesSociales;
    ImageView logo;
    Emisora emisora;


    public EmisoraInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_emisora_info, container, false);
        tv_descripcion_emisora= root.findViewById(R.id.tv_descripcion_emisora);
        logo=root.findViewById(R.id.logo_radio);
        tv_web_emisora= root.findViewById(R.id.tv_web_emisora);
        tv_telef_emisora= root.findViewById(R.id.tv_telef_emisora);
        tv_ubicacion_emisora= root.findViewById(R.id.tv_ubicacion_emisora);
        layoutManager= root.findViewById(R.id.lin_layout_info_emisora);
        redesSociales=root.findViewById(R.id.tv_redesSociales);

        emisora= EmisoraContentFragment.emisora;

        tv_descripcion_emisora.setText(emisora.getDescripcion());
        tv_web_emisora.setText(emisora.getSitio_web().substring(7));
        //tv_telef_emisora.setText("1234567890 - 11122233345");
        tv_ubicacion_emisora.setText(emisora.getDireccion());
        tv_telef_emisora.setOnClickListener(btn_dial);
        tv_web_emisora.setOnClickListener(btn_browser);
        tv_ubicacion_emisora.setOnClickListener(btn_location);
        tv_instagram=root.findViewById(R.id.tv_instagram);
        tv_facebook=root.findViewById(R.id.tv_facebook);
        tv_twitter=root.findViewById(R.id.tv_twitter);
        new RestFetchEmisoraInfoTask().execute();

        return root;
    }


    public final View.OnClickListener btn_dial=new View.OnClickListener(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        //Intent whatsapp = new Intent("android.intent.action.MAIN");
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            String number=tv_telef_emisora.getText().toString();
            if(number!=null){
                try {
                    intent.setData(Uri.parse("tel:"+number));
                    startActivity(intent);
                    //whatsapp.setAction(Intent.ACTION_VIEW);
                    //whatsapp.setPackage("com.whatsapp");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            //System.out.println("holaxD");
        }
    };
    public final View.OnClickListener btn_browser=new View.OnClickListener(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Intent whatsapp = new Intent("android.intent.action.MAIN");
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            String url=tv_web_emisora.getText().toString();
            if(url!=null){
                try {
                    intent.setData(Uri.parse("http://"+url));
                    startActivity(intent);
                    //whatsapp.setAction(Intent.ACTION_VIEW);
                    //whatsapp.setPackage("com.whatsapp");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            //System.out.println("holaxD");
        }
    };
    public final View.OnClickListener btn_location=new View.OnClickListener(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Intent whatsapp = new Intent("android.intent.action.MAIN");
        @Override
        public void onClick(View view) {
            //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
            //String url=tv_telef_emisora.getText().toString();
            try {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Radio CARAVANA, Guayaquil");
                intent.setData(gmmIntentUri);
                startActivity(intent);
                //whatsapp.setAction(Intent.ACTION_VIEW);
                //whatsapp.setPackage("com.whatsapp");
            }catch (Exception e){
                e.printStackTrace();
            }



            //System.out.println("holaxD");
        }
    };
    /**
     * Clase Task que obtiene los datos de telefono y redes sociales de la emisora
     */
    private class RestFetchEmisoraInfoTask extends AsyncTask<Void,Void,Void>{
        List<TelefonoEmisora> telefonos;
        List<RedSocialEmisora> redes;

        @Override
        protected Void doInBackground(Void... voids) {
            telefonos= RestServices.consultarTelefonosEmisora(getContext(),emisora.getId().intValue());
            redes= RestServices.consultarRedesEmisora(getContext(),emisora.getId().intValue());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(telefonos!= null){
                String telfs="";
                for (TelefonoEmisora tel: this.telefonos) {
                    telfs+= tel.getNro_telefono() + " - ";
                }
                telfs= telfs.substring(0,telfs.length()-2);
                tv_telef_emisora.setText(telfs);
                tv_telef_emisora.setOnClickListener(btn_dial);
            }
            else{
                tv_telef_emisora.setText("Ninguno");
            }
            if(emisora.getNombre().equals("Radio Diblu")){
                logo.setBackground(getContext().getDrawable(R.drawable.logo_diblu));
            }
            if(emisora.getNombre().equals("Radio Caravana")){
                logo.setBackground(getContext().getDrawable(R.drawable.logo_caravana));
            }
            if(redes!= null){
                LinearLayout container;
                Button button;
                TextView text;
                ImageView imgView;

                for(RedSocialEmisora red: redes){
                    /*if(red.getNombre().equalsIgnoreCase("Facebook")){
                        imgView= new ImageView(getContext());
                        Picasso.with(getContext()).load(R.drawable.facebook_info).resize(35,40).into(imgView);
                    }
                    else if(red.getNombre().equalsIgnoreCase("Twitter")){
                        imgView= new ImageView(getContext());
                        Picasso.with(getContext()).load(R.drawable.twiter_info).resize(35,40).into(imgView);
                    }
                    else if(red.getNombre().equalsIgnoreCase("Instagram")){
                        imgView= new ImageView(getContext());
                        Picasso.with(getContext()).load(R.drawable.instagram).resize(35,40).into(imgView);
                    }
                    else{
                        imgView= new ImageView(getContext());
                        Picasso.with(getContext()).load(R.drawable.social_media_icon).centerInside().into(imgView);
                    }
                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0.05f);
                    layoutParams.setMarginStart(10);
                    layoutParams.setMarginEnd(10);
                    layoutParams.gravity= Gravity.CENTER;
                    imgView.setLayoutParams(layoutParams);
                    */
                    SpannableString username;
                    ///(?:http://)?(?:www.)?facebook.com/(?:(?:w)*#!/)?(?:pages/)?(?:[w-]*/)*([w-]*)/

                    if(red.getLink().matches("(.*)\\.com/(\\w|\\s|\\d)+")|| red.getLink().matches("(.*)\\.com/(\\w|\\s)+[0-9][0-9]\\.[0-9]/")|| red.getLink().matches("(.*)\\.com/(\\w|\\s)+/")){
                        int index= red.getLink().indexOf("com/");
                        if(red.getNombre().equals("Facebook")||red.getNombre().equals("Instagram")){
                            username= new SpannableString(red.getLink().substring(index+4,red.getLink().length()-1 ) );
                        }else{
                            username= new SpannableString(red.getLink().substring(index+4,red.getLink().length() ) );
                        }
                    }
                    else{
                        username= new SpannableString(red.getLink());
                    }

                    username.setSpan(new UnderlineSpan(), 0,username.length(),0);
                    if(red.getNombre().equalsIgnoreCase("Facebook")){
                        //imgView= new ImageView(getContext());
                        //Picasso.with(getContext()).load(R.drawable.facebook_info).resize(35,40).into(imgView);
                        tv_facebook.setText(username);
                        tv_facebook.setOnClickListener((View v)->{
                            WebFragment fragment= new WebFragment();
                            Bundle args= new Bundle();
                            args.putString("url",red.getLink());
                            fragment.setArguments(args);
                            ((HomeActivity) getContext()).changeFragment(fragment,R.id.frame_container,true);
                        });
                    }
                    else if(red.getNombre().equalsIgnoreCase("Twitter")){
                        //imgView= new ImageView(getContext());
                        //Picasso.with(getContext()).load(R.drawable.twiter_info).resize(35,40).into(imgView);
                        tv_twitter.setText(username);
                        tv_twitter.setOnClickListener((View v)->{
                            WebFragment fragment= new WebFragment();
                            Bundle args= new Bundle();
                            args.putString("url",red.getLink());
                            fragment.setArguments(args);
                            ((HomeActivity) getContext()).changeFragment(fragment,R.id.frame_container,true);
                        });
                    }
                    else if(red.getNombre().equalsIgnoreCase("Instagram")){
                        //imgView= new ImageView(getContext());
                        //Picasso.with(getContext()).load(R.drawable.instagram).resize(35,40).into(imgView);
                        tv_instagram.setText(username);
                        tv_instagram.setOnClickListener((View v)->{
                            WebFragment fragment= new WebFragment();
                            Bundle args= new Bundle();
                            args.putString("url",red.getLink());
                            fragment.setArguments(args);
                            ((HomeActivity) getContext()).changeFragment(fragment,R.id.frame_container,true);
                        });
                    }
                    else{
                        imgView= new ImageView(getContext());
                        Picasso.with(getContext()).load(R.drawable.social_media_icon).centerInside().into(imgView);
                    }

                    /*
                    button=new Button(getContext());
                    text=new TextView(getContext());
                    //button.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
                    text.setText(username);
                    button.setTextSize(12);
                    button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    button.setAllCaps(false);
                    button.setTextColor(getContext().getResources().getColor(R.color.color_text));
                    button.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.45f));
                    button.setOnClickListener((View v)->{
                        WebFragment fragment= new WebFragment();
                        Bundle args= new Bundle();
                        args.putString("url",red.getLink());
                        fragment.setArguments(args);
                        ((HomeActivity) getContext()).changeFragment(fragment,R.id.frame_container,true);
                    });

                    container= new LinearLayout(getContext());
                    container.addView(imgView);
                    container.addView(button);

                    redesSociales.addView(container);
                    */

                }
            }
        }
    }

}
