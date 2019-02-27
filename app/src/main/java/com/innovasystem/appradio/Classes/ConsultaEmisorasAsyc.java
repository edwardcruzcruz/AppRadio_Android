package com.innovasystem.appradio.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.innovasystem.appradio.Classes.Models.Emisora;
import com.innovasystem.appradio.R;
import com.innovasystem.appradio.Utils.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ConsultaEmisorasAsyc extends AsyncTask<String,Void,List<Emisora>> {
        private Context context;

                public ConsultaEmisorasAsyc(Context c){
                        super();
                        this.context=c;
                }
                @Override
                protected List<Emisora> doInBackground(String... provincia) {
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
                        String token=SessionConfig.getSessionConfig(context).getValue(SessionConfig.userToken);
                        String tokenDesencriptado=SessionConfig.getSessionConfig(context).DesencriptarToken(token);
                        requestHeaders.set("Authorization", tokenDesencriptado);
                        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
                        RestTemplate templ = new RestTemplate();
                        Emisora[] emisoras = null;
                        String url = Constants.serverDomain + Constants.uriEmisoras + (provincia[0] != null ? String.format("?provincia=%s",provincia[0]) : "" );
                        try {
                                ResponseEntity<Emisora[]> responseEntity = templ.exchange(url, HttpMethod.GET, requestEntity, Emisora[].class);
                                System.out.println(responseEntity.getStatusCode());
                                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                                        emisoras = responseEntity.getBody();
                                }
                        } catch (Exception e) {
                                Log.e("RestGetError", e.getMessage());
                                return null;
                        }
                        //response=Emisoras[0];
                        return new ArrayList<>(Arrays.asList(emisoras));
                }

                @Override
                protected void onPostExecute(List<Emisora> result) {
                        //do stuff
                        super.onPostExecute(result);
                        //listener.processFinish(result);
                        //parent.metodo(result,this.response);

                }



}