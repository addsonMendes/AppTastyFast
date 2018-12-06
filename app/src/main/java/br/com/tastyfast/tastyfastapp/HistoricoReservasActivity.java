package br.com.tastyfast.tastyfastapp;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.tastyfast.tastyfastapp.config.RetrofitConfig;
import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.model.Reserva;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoReservasActivity extends AppCompatActivity {

    private ListView lstReservas;
    private List<Reserva> reservas;
    private Cliente cliente;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_reservas);

        inicializaComponentes();

        cliente = new Cliente();

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        cliente.setIdCliente(preferences.getInt("idUsuarioLogado", 0));

        carregaHistoricoReservas();

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregaHistoricoReservas();
            }
        });

    }

    private void inicializaComponentes(){
        lstReservas = findViewById(R.id.lstHistoricoReservas);
        layout = findViewById(R.id.historicoReservasLayout);
    }

    public void carregaHistoricoReservas(){
        Call<List<Reserva>> call = new RetrofitConfig().getReservaService().listaReservasDoUsuario(cliente.getIdCliente().toString());

        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                reservas = response.body();

                ArrayAdapter<Reserva> adapter = new ArrayAdapter<>(HistoricoReservasActivity.this,
                        android.R.layout.simple_list_item_1,
                        reservas);

                lstReservas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.e("Histórico de reservas", "Problemas ao listar histórico...\n" + t.getMessage());
            }
        });

        layout.setRefreshing(false);

    }
}
