package com.salgado.cristopher.catalogooptativo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button btnIniciar;
    private TextView textView;

    String param1, param2, mensaje;
    SoapPrimitive resulString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.edtUsername);
        password = (EditText) findViewById(R.id.edtPassword);
        textView = (TextView) findViewById(R.id.textView);
        btnIniciar = (Button) findViewById(R.id.btnIniciarSesion);
        Toast.makeText(this, "Holiwiii", Toast.LENGTH_LONG).show();
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                param1 = username.getText() + "";
                param2 = password.getText() + "";
                SegundoPlano tareaSeg = new SegundoPlano();
                tareaSeg.execute();
                //Log.d("----> -> ", 1 + " sgfhmfdgh");
            }
        });

    }


    private class SegundoPlano extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            convertir();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //textView.setText("Response -> " + result.toString() + " , " + mensaje);
        }
    }

    private void convertir() {
        String NAMESPACE = "http://Servicios/";
        String METHOD_NAME = "Autenticacion";
        String URL = "http://192.168.11.38:8080/MisOfertasWebService/ClienteService?WSDL";
        URL = "http://192.168.56.1:8080/MisOfertasWebService/ClienteService?WSDL";
        String SOAP_ACTION = NAMESPACE + "" + METHOD_NAME;
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("correo", param1);
            request.addProperty("contrasena", param2);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            //soapEnvelope.getResponse().toString();
            //Object obj =(Object) soapEnvelope.getResponse();
            SoapObject res = (SoapObject) soapEnvelope.bodyIn;
            Log.d("----> -> ", 8 + " " + res.getProperty(0));
            //Menu[] menus = new Menu[res.getPropertyCount()];
            for (int i = 0; i < res.getPropertyCount(); i++) {
                SoapObject so = (SoapObject) res.getProperty(0);
                Log.d("----> -> ", i + " " + so.getProperty("actualizacion"));
            }
            //Log.d(" ----> Resp -> ", primitive.toString());
            //Toast.makeText(this, resulString.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(" ----> Resp -> ", ex.getMessage().toString());
            //Toast.makeText(this, "error ->" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
