package br.com.tastyfast.tastyfastapp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class Mensagens {

    public static void exibeToast(Context contexto, String mensagem ){
        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog mostraCarregamento(Context context){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setTitle("Tasty Fast");
        //progressDialog.show();
        return progressDialog;
    }

    public static AlertDialog exibeMensagemJanela(Context context, String msg){

        AlertDialog alerta;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tasty Fast");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alerta = builder.create();
        return alerta;
    }

}
