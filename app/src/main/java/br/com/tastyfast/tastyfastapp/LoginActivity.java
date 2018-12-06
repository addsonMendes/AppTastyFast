package br.com.tastyfast.tastyfastapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.tastyfast.tastyfastapp.config.RetrofitConfig;
import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.util.MD5;
import br.com.tastyfast.tastyfastapp.util.Mensagens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private TextView txtEsqueceu, txtNaoPossui, txtLogo;
    private Button btEntrar;
    private Cliente logado = new Cliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializaComponentes();

        Typeface fonte = Typeface.createFromAsset(getAssets(), "Abel-Regular.ttf");
        txtLogo.setTypeface(fonte);

        txtEsqueceu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensagens.exibeToast(LoginActivity.this, "Um email de recuperação será enviado ao usuário!");
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !(edtEmail.getText().toString().equals("") && edtSenha.getText().toString().equals("")) ){
                    Cliente usuario = new Cliente();
                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setSenha(new MD5().criptografa(edtSenha.getText().toString()));
                    fazLogin(usuario);
                } else {
                    Mensagens.exibeToast(LoginActivity.this, "Preencha usuário e senha para continuar!");
                }
            }
        });

        txtNaoPossui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeCadastroDeClientes = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                startActivity(vaiParaTelaDeCadastroDeClientes);
            }
        });
    }

    private void inicializaComponentes(){
        edtEmail = findViewById(R.id.edtLoginEmail);
        edtSenha = findViewById(R.id.edtLoginSenha);
        txtEsqueceu = findViewById(R.id.txtLoginEsqueceu);
        txtNaoPossui = findViewById(R.id.txtLoginNaoPossuiConta);
        btEntrar = findViewById(R.id.btLoginEntrar);
        txtLogo = findViewById(R.id.txtLoginTitulo);
    }

    private void fazLogin(Cliente usuarioApp){
        Call<Cliente> call = new RetrofitConfig().getClienteService().login(usuarioApp.getEmail(), usuarioApp.getSenha());

        final ProgressDialog dialog = Mensagens.mostraCarregamento(LoginActivity.this); //Mostra loading...
        dialog.show();

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.body() != null){
                    dialog.dismiss(); // Oculta Loading...
                    logado = response.body();

                    String tokenAtual = FirebaseInstanceId.getInstance().getToken();
                    if( !(tokenAtual.equals(logado.getTokenAparelho())) ){
                        logado.setTokenAparelho(tokenAtual);
                        atualizaTokenCliente(logado);
                    }

                    SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                    adicionaPreferenceJaAbriu(preferences, logado);

                    Intent vaiParaTelaDeListagemRestaurante = new Intent(LoginActivity.this, ListaRestaurantesActivity.class);
                    vaiParaTelaDeListagemRestaurante.putExtra("clienteLogado", logado);
                    startActivity(vaiParaTelaDeListagemRestaurante);
                    finish();
                } else {
                    dialog.dismiss(); // Oculta Loading...
                    Mensagens.exibeMensagemJanela(LoginActivity.this, "Usuário ou senha inválidos!").show();
                }
            }

            private void adicionaPreferenceJaAbriu(SharedPreferences preferences, Cliente cliente){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("usuarioLogado", true);
                editor.putInt("idUsuarioLogado", cliente.getIdCliente());
                editor.putString("nomeUsuario", cliente.getNome());
                editor.putString("emailUsuario", cliente.getEmail());
                editor.commit();
            }

            private void atualizaTokenCliente(Cliente cliente){
                Call<Cliente> call = new RetrofitConfig().getClienteService().atualizaToken(cliente);

                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        Log.i("Informação Token", "Token atualizado");
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("Erro Token", "Problemas ao atualizar token: " + t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                dialog.dismiss();
                Mensagens.exibeMensagemJanela(LoginActivity.this, "Problemas ao realizar login!");
                Log.e("Erro Login", t.getMessage());
            }

        });

    }
}
