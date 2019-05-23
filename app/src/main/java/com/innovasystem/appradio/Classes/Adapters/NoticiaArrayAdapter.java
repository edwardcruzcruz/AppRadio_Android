package com.innovasystem.appradio.Classes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovasystem.appradio.Classes.Models.Noticia;
import com.innovasystem.appradio.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import twitter4j.Status;

public class NoticiaArrayAdapter extends ArrayAdapter<Noticia> {
    private final Context context;
    private final ArrayList<Noticia> noticias;
    private final ArrayList<Status> statuses;
    private final int layoutLoad;


    public NoticiaArrayAdapter(Context context, int layoutLoad, ArrayList<Noticia> values, ArrayList<Status> status) {
        super(context, layoutLoad, values);
        this.context = context;
        this.noticias = values;
        this.statuses=status;
        this.layoutLoad = layoutLoad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.layoutLoad, parent, false);
        TextView firstline = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondline = (TextView) rowView.findViewById(R.id.secondLine);
        TextView thirdline = (TextView) rowView.findViewById(R.id.thirdLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        try{
            Picasso.with(getContext()).load(noticias.get(position).getFoto()).into(imageView);
        }catch(Exception e){
            Picasso.with(getContext()).load("https://moonvillageassociation.org/wp-content/uploads/2018/06/default-profile-picture1-744x744.jpg").into(imageView);
        }


        firstline.setText(noticias.get(position).getUsuario());
        secondline.setText(noticias.get(position).getMensaje());

        firstline.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Intent whatsapp = new Intent("android.intent.action.MAIN");
            @Override
            public void onClick(View view) {
                //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
                //String url=tv_telef_emisora.getText().toString();
                try {
                    Uri uri = Uri.parse("https://twitter.com/"+noticias.get(position).getUsuario().substring(1));
                    intent.setData(uri);
                    getContext().startActivity(intent);
                    //whatsapp.setAction(Intent.ACTION_VIEW);
                    //whatsapp.setPackage("com.whatsapp");
                }catch (Exception e){
                    e.printStackTrace();
                }



                //System.out.println("holaxD");
            }
        });
        secondline.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Intent whatsapp = new Intent("android.intent.action.MAIN");
            @Override
            public void onClick(View view) {
                //System.out.println("aqui esta la posicion---------------"+((LinearLayoutManager)rv_home.getLayoutManager()).findFirstVisibleItemPosition());
                //String url=tv_telef_emisora.getText().toString();
                try {
                    Uri uri = Uri.parse("https://twitter.com/"+noticias.get(position).getUsuario().substring(1)+"/status/"+statuses.get(position).getId());
                    intent.setData(uri);
                    getContext().startActivity(intent);
                    //whatsapp.setAction(Intent.ACTION_VIEW);
                    //whatsapp.setPackage("com.whatsapp");
                }catch (Exception e){
                    e.printStackTrace();
                }



                //System.out.println("holaxD");
            }
        });

        String pattern = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);



        thirdline.setText(simpleDateFormat.format(noticias.get(position).getFecha_creacion()));
        return rowView;
    }

    public Noticia getMap(int position){
        return noticias.get(position);
    }

}
