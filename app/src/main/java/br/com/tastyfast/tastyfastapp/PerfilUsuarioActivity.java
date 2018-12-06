package br.com.tastyfast.tastyfastapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.util.Mensagens;
import br.com.tastyfast.tastyfastapp.util.UsuarioPreference;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha;
    private Button btAlterarDados;
    private Cliente clienteLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        inicializaComponentes();

        clienteLogado = new Cliente();

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        clienteLogado = new UsuarioPreference().buscaUsuarioLogado(preferences);

        edtNome.setText(clienteLogado.getNome());
        edtEmail.setText(clienteLogado.getEmail());
        edtSenha.setText(clienteLogado.getSenha());

        btAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensagens.exibeToast(PerfilUsuarioActivity.this, "Função não implementada na versão atual!");
            }
        });
    }

    private void inicializaComponentes(){
        edtNome = findViewById(R.id.edtPerfUsNome);
        edtEmail = findViewById(R.id.edtPerfUsEmail);
        edtSenha = findViewById(R.id.edtPerfUsSenha);
        btAlterarDados = findViewById(R.id.btPerfUsAlterarDados);
    }
}
