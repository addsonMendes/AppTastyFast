package br.com.tastyfast.tastyfastapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuConfiguracoesActivity extends AppCompatActivity {

    private Button btPerfil, btBuscaAvancada, btReservas, btSair;
    private EditText edtPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_configuracoes);

        inicializaComponentes();

        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDePerfil = new Intent(MenuConfiguracoesActivity.this, PerfilUsuarioActivity.class);
                startActivity(vaiParaTelaDePerfil);
            }
        });

        btReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeHistorico = new Intent(MenuConfiguracoesActivity.this, HistoricoReservasActivity.class);
                startActivity(vaiParaTelaDeHistorico);
            }
        });

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("usuarioLogado");
                editor.remove("idUsuarioLogado");
                editor.remove("nomeUsuario");
                editor.remove("emailUsuario");
                editor.commit();

                Intent vaiParaTelaDeLogin = new Intent(MenuConfiguracoesActivity.this, LoginActivity.class);
                startActivity(vaiParaTelaDeLogin);
                finish();
            }
        });
    }

    private void inicializaComponentes(){
        btPerfil = findViewById(R.id.btMenuConfPerfil);
        btBuscaAvancada = findViewById(R.id.btMenuConfBuscaAvanc);
        btReservas = findViewById(R.id.btMenuConfReservas);
        btSair = findViewById(R.id.btMenuConfSair);
        edtPesquisar = findViewById(R.id.edtMenuConfPesquisar);
    }
}
