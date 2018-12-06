package br.com.tastyfast.tastyfastapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tastyfast.tastyfastapp.model.Cardapio;
import br.com.tastyfast.tastyfastapp.model.Mesa;
import br.com.tastyfast.tastyfastapp.model.Reserva;
import br.com.tastyfast.tastyfastapp.model.Restaurante;

public class CardapioActivity extends AppCompatActivity {

    private TextView txtNomeRest;
    private ListView lstCardapio, lstItensSelecionados;
    List<Cardapio> itensSelecionados;
    private Button btConfirmar;
    private Restaurante restaurante;
    private Reserva reserva;
    private Mesa mesa;
    private Intent intent = new Intent();
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        inicializaComponentes();

        layout.setVisibility(View.INVISIBLE);

        intent = getIntent();
        restaurante = new Restaurante();
        reserva = new Reserva();
        mesa = new Mesa();

        restaurante = (Restaurante) intent.getSerializableExtra("restaurante");
        reserva = (Reserva) intent.getSerializableExtra("reserva");
        mesa = (Mesa) intent.getSerializableExtra("mesaSelecionada");

        itensSelecionados = new ArrayList<>();

        txtNomeRest.setText(restaurante.getNome());

        ArrayAdapter<Cardapio> adapter = new ArrayAdapter<>(CardapioActivity.this, android.R.layout.simple_list_item_1, restaurante.getCardapios());
        lstCardapio.setAdapter(adapter);

        lstCardapio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cardapio cardapio = (Cardapio) parent.getItemAtPosition(position);
                adicionaCardapioNoCard(cardapio);
                layout.setVisibility(View.VISIBLE);
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaDeConfirmacao = new Intent(CardapioActivity.this, ConfirmacaoReservaActivity.class);
                vaiParaTelaDeConfirmacao.putExtra("restauranteSelecionado", restaurante);
                vaiParaTelaDeConfirmacao.putExtra("reserva", reserva);
                vaiParaTelaDeConfirmacao.putExtra("mesaSelecionada", mesa);
                startActivity(vaiParaTelaDeConfirmacao);
            }
        });

    }

    private void inicializaComponentes(){
        txtNomeRest = findViewById(R.id.txtCardapioNomeRest);
        lstCardapio = findViewById(R.id.lstCardapio);
        lstItensSelecionados = findViewById(R.id.lstCardapioItensSelecionados);
        btConfirmar = findViewById(R.id.btCardapioConfreserva);
        layout = findViewById(R.id.cardCardapioLayout);
    }

    private void adicionaCardapioNoCard(Cardapio cardapio){
        itensSelecionados.add(cardapio);
        ArrayAdapter<Cardapio> cardAdapter = new ArrayAdapter<>(CardapioActivity.this, android.R.layout.simple_list_item_1, itensSelecionados);
        lstItensSelecionados.setAdapter(cardAdapter);
    }
}
