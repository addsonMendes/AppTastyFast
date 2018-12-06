package br.com.tastyfast.tastyfastapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.tastyfast.tastyfastapp.config.FirebaseMessage;

public class SplashActivity extends AppCompatActivity {

    private TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        inicializaComponentes();

        Typeface fonte = Typeface.createFromAsset(getAssets(), "Abel-Regular.ttf");
        txtLogo.setTypeface(fonte);

        mostrarSplash();
    }

    public void mostrarSplash(){
        new FirebaseMessage().onTokenRefresh(); // Enviando token firebase do dispositivo para o servidor
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                if(preferences.contains("usuarioLogado")){
                    mostrarRestaurantes();
                } else {
                    mostrarLogin();
                }
            }
        }, 2000);
    }

    private void inicializaComponentes(){
        txtLogo = findViewById(R.id.txtSplashTitulo);
    }

    public void mostrarRestaurantes(){
        Intent vaiParaTelaDeListagemRestaurante = new Intent(SplashActivity.this, ListaRestaurantesActivity.class);
        startActivity(vaiParaTelaDeListagemRestaurante);
        finish();
    }

    public void mostrarLogin(){
        Intent vaiParaTelaDeLogin = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(vaiParaTelaDeLogin);
        finish();
    }
}
