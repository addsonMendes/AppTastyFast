package br.com.tastyfast.tastyfastapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.tastyfast.tastyfastapp.config.RetrofitConfig;
import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.util.Mensagens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroClienteActivity extends AppCompatActivity {

    private Button btCadastrar;
    private EditText edtNome, edtEmail, edtSenha, edtConfEmail, edtConfSenha;
    private Cliente cliente;
    private String nome, email, emailConf, senha, senhaConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_client);

        inicializaComponentes();

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todosCamposPreenchidos()){
                    Mensagens.exibeToast(CadastroClienteActivity.this, "Todos os campos devem estar preenchidos!");
                } else
                if(!confirmacoesCorrespondentes()){
                    Mensagens.exibeToast(CadastroClienteActivity.this, "O campo de confirmação de email ou senha não estão corretos!");
                } else {
                    cliente = new Cliente();
                    nome = edtNome.getText().toString();
                    email = edtEmail.getText().toString();
                    senha = edtSenha.getText().toString();

                    cliente.setNome(nome);
                    cliente.setEmail(email);
                    cliente.setSenha(senha);

                    gravarClienteWS(cliente);
                }
            }
        });
    }

    private void inicializaComponentes(){
        btCadastrar = findViewById(R.id.btCadCliRealizaCad);
        edtNome = findViewById(R.id.edtCadCliNome);
        edtEmail = findViewById(R.id.edtCadCliEmail);
        edtConfEmail = findViewById(R.id.edtCadCliConfEmail);
        edtSenha = findViewById(R.id.edtCadCliSenha);
        edtConfSenha = findViewById(R.id.edtCadCliConfSenha);
    }

    private boolean todosCamposPreenchidos(){

        boolean retorno = false;

        nome = edtNome.getText().toString();
        email = edtEmail.getText().toString();
        emailConf = edtConfEmail.getText().toString();
        senha = edtSenha.getText().toString();
        senhaConf = edtConfSenha.getText().toString();

        if( ! (nome.equals("") || email.equals("") || emailConf.equals("")
                || senha.equals("") || senhaConf.equals("")) )
            retorno =  false;
        else
            retorno = true;

        return retorno;
    }

    private boolean confirmacoesCorrespondentes(){

        boolean retorno = false;

        email = edtEmail.getText().toString();
        emailConf = edtConfEmail.getText().toString();
        senha = edtSenha.getText().toString();
        senhaConf = edtConfSenha.getText().toString();

        if(!(email.equals(emailConf) ) && !(senha.equals(senhaConf)) )
            retorno = false;
        else
            retorno = true;

        return retorno;
    }

    public void gravarClienteWS(final Cliente cliente){
        Call<Cliente> call = new RetrofitConfig().getClienteService().cadastrarCliente(cliente);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CadastroClienteActivity.this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setTitle("Reserva Rápida");
        progressDialog.show();

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                progressDialog.dismiss();
                limpaCampos();
                Toast.makeText(CadastroClienteActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Erro ao recuperar", t.getMessage());
            }
        });
    }

    public void limpaCampos(){
        edtNome.setText("");
        edtEmail.setText("");
        edtSenha.setText("");
    }
}
