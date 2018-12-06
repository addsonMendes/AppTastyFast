package br.com.tastyfast.tastyfastapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.tastyfast.tastyfastapp.adapter.RestauranteAdapter;
import br.com.tastyfast.tastyfastapp.config.RetrofitConfig;
import br.com.tastyfast.tastyfastapp.model.Cliente;
import br.com.tastyfast.tastyfastapp.model.Restaurante;
import br.com.tastyfast.tastyfastapp.util.Mensagens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaRestaurantesActivity extends AppCompatActivity {

    private ImageView ivSettings;
    private SwipeRefreshLayout layoutTelaRestaurantes;
    private ListView lstRestaurantes;
    private List<Restaurante> restaurantes;
    private Restaurante restaurante;
    private Cliente logado;
    private TextView nomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);

        inicializaComponentes(); // Inicializa todos os componentes da tela

        logado = new Cliente();
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        logado.setNome(preferences.getString("nomeUsuario", ""));
        nomeUsuario.setText(logado.getNome());

        try{
            buscaRestaurantesNoWS();
        } catch(Exception ex){
            Mensagens.exibeMensagemJanela(ListaRestaurantesActivity.this, "Problemas ao carregar listagem de restaurantes! Tente novamente!");
        }

        lstRestaurantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                restaurante = (Restaurante) parent.getItemAtPosition(position);
                Intent vaiParaTelaDeDetalheRestaurante = new Intent(ListaRestaurantesActivity.this, DetalheRestauranteActivity.class);
                vaiParaTelaDeDetalheRestaurante.putExtra("restauranteSelecionado", restaurante);
                startActivity(vaiParaTelaDeDetalheRestaurante);
                finish();
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeConfiguracoes = new Intent(ListaRestaurantesActivity.this, MenuConfiguracoesActivity.class);
                startActivity(vaiParaTelaDeConfiguracoes);
            }
        });

        layoutTelaRestaurantes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscaRestaurantesNoWS();
            }
        });
    }

    private void inicializaComponentes(){
        ivSettings = findViewById(R.id.btLstRestConfig);
        lstRestaurantes = findViewById(R.id.lstRest);
        layoutTelaRestaurantes = findViewById(R.id.layoutListagemRestaurantes);
        restaurante = new Restaurante();
        restaurantes = new ArrayList<>();
        nomeUsuario = findViewById(R.id.txtListaRestNomeUsuario);
    }

    private void buscaRestaurantesNoWS(){
        Call<List<Restaurante>> call = new RetrofitConfig().getRestauranteService().buscarRestaurantes();

        call.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                layoutTelaRestaurantes.setRefreshing(true);
                if(response.body() != null){
                    layoutTelaRestaurantes.setRefreshing(false);
                    restaurantes = response.body();
                    //ArrayAdapter<Restaurante> adapter = new ArrayAdapter<>(ListaRestaurantesActivity.this, android.R.layout.simple_list_item_1, restaurantes);
                    RestauranteAdapter adapter = new RestauranteAdapter(restaurantes, ListaRestaurantesActivity.this);
                    lstRestaurantes.setAdapter(adapter);
                } else {
                    layoutTelaRestaurantes.setRefreshing(false);
                    Mensagens.exibeMensagemJanela(ListaRestaurantesActivity.this, "Nenhum restaurante Localizado!");
                }

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Mensagens.exibeToast(ListaRestaurantesActivity.this, "Problemas ao carregar listagem de restaurantes! Tente novamente!");
                Log.e("Erro restaurante", t.getMessage());
            }
        });

        layoutTelaRestaurantes.setRefreshing(false);
    }
}
