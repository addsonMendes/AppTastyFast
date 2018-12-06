package br.com.tastyfast.tastyfastapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.tastyfast.tastyfastapp.config.RetrofitConfig;
import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.model.Mesa;
import br.com.tastyfast.tastyfastapp.model.Reserva;
import br.com.tastyfast.tastyfastapp.model.Restaurante;
import br.com.tastyfast.tastyfastapp.model.StatusReserva;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmacaoReservaActivity extends AppCompatActivity {

    private TextView txtNomeRest, txtDataReserva, txtHorario, txtNomeCliente, txtQtdPessoas, txtTitulo;
    private Button btConfirmar;
    private Intent intent = new Intent();
    private Restaurante restaurante;
    private Reserva reserva;
    private Mesa mesa;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_reserva);

        inicializaComponentes();

        intent = getIntent();
        restaurante = new Restaurante();
        reserva = new Reserva();
        mesa = new Mesa();
        cliente = new Cliente();

        Typeface fonte = Typeface.createFromAsset(getAssets(), "Abel-Regular.ttf");
        txtTitulo.setTypeface(fonte);

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        cliente.setIdCliente(preferences.getInt("idUsuarioLogado", 0));
        cliente.setNome(preferences.getString("nomeUsuario", ""));
        cliente.setEmail(preferences.getString("emailUsuario", ""));

        restaurante = (Restaurante) intent.getSerializableExtra("restauranteSelecionado");
        reserva = (Reserva) intent.getSerializableExtra("reserva");
        mesa = (Mesa) intent.getSerializableExtra("mesaSelecionada");

        txtNomeRest.setText(restaurante.getNome());
        txtDataReserva.setText(reserva.getDataReserva());
        txtHorario.setText(reserva.getHorario());
        txtNomeCliente.setText(cliente.getNome());
        txtQtdPessoas.setText(String.valueOf(reserva.getMesa().getQtdPessoas()));

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserva.setCliente(cliente);
                //reserva.setMesa(mesa);
                reserva.setStatus(StatusReserva.Aguardando);
                reserva.setRestaurante(restaurante);
                confirmaReserva(reserva);
            }
        });
    }

    private void inicializaComponentes(){
        txtNomeRest = findViewById(R.id.txtRestauranteConf);
        txtDataReserva = findViewById(R.id.txtDtReservaConf);
        txtHorario = findViewById(R.id.txtHorarioConf);
        txtNomeCliente = findViewById(R.id.txtClienteConf);
        txtQtdPessoas = findViewById(R.id.txtQtdPessoasConf);
        btConfirmar = findViewById(R.id.btConfirmacaoReserva);
        txtTitulo = findViewById(R.id.txtTituloReservaConf);
    }

    public void confirmaReserva(Reserva reserva){
        Call<Reserva> call = new RetrofitConfig().getReservaService().cadastrarReserva(reserva);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ConfirmacaoReservaActivity.this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setTitle("Tasty Fast");
        progressDialog.show();

        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                progressDialog.dismiss();
                exibeConfirmacao();
                Log.i("resposta da gravacao", response.body().toString());
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Erro gravação", t.getMessage());
                Toast.makeText(ConfirmacaoReservaActivity.this, "Problemas ao gravar reserva...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void exibeConfirmacao(){

        AlertDialog alerta;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tasty Fast");
        builder.setMessage("Obrigado por utilizar o Tasty Fast!\n" +
                "Sua solicitação de reserva foi enviada ao restaurante selecionado!\n" +
                "Assim que a reserva for confirmada, você receberá uma notificação em seu dispositivo!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent vaiParaTelaDeRestaurante = new Intent(ConfirmacaoReservaActivity.this, ListaRestaurantesActivity.class);
                startActivity(vaiParaTelaDeRestaurante);
                finish();
            }
        });

        alerta = builder.create();
        alerta.show();
    }
}
