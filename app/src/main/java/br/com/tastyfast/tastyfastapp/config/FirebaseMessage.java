package br.com.tastyfast.tastyfastapp.config;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseMessage extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token Firebase", "Refreshed token: " + refreshedToken);

        //enviaTokenParaServidor(refreshedToken);
    }

    /*private void enviaTokenParaServidor(final String token) {

        if(preferences.contains("usuarioLogado")){
            final Cliente logado = new Cliente();
            logado.setIdCliente(preferences.getInt("idUsuarioLogado", 0));
            logado.setNome(preferences.getString("nomeUsuario", ""));
            logado.setEmail(preferences.getString("emailUsuario", ""));
            logado.setTokenDispositivo(token);

            Call<Void> call = new RetrofitConfig().getDispositivoService().enviaToken(logado);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("Envio de Token", "Token enviado ao cliente " + logado.getNome() + ": " + token);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Erro Token", "Problemas ao enviar token...");
                }
            });
        }
    } */



}
