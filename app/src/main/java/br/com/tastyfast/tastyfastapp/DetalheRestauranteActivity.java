package br.com.tastyfast.tastyfastapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.tastyfast.tastyfastapp.model.Horario;
import br.com.tastyfast.tastyfastapp.model.Mesa;
import br.com.tastyfast.tastyfastapp.model.Restaurante;

public class DetalheRestauranteActivity extends AppCompatActivity {

    private TextView txtNomeRest, txtTipoCulinaria, txtCidadeEstado, txtEnderecoRest, txtHorarioFunc;
    private Button btFacaReserva;
    private Intent intent = new Intent();
    private Restaurante restaurante;
    private SliderLayout slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_restaurante);

        inicializaComponentes();

        intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra("restauranteSelecionado");
        txtNomeRest.setText(restaurante.getNome());
        txtTipoCulinaria.setText(restaurante.getTipoCulinaria());
        txtCidadeEstado.setText(restaurante.getEndereco().getCidade() + " - " + restaurante.getEndereco().getEstado());
        txtEnderecoRest.setText(restaurante.getEndereco().getLogradouro());
        txtHorarioFunc.setText(restaurante.getHorarioFuncionamento());

        btFacaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaTelaReserva = new Intent(DetalheRestauranteActivity.this, ReservaActivity.class);
                vaiParaTelaReserva.putExtra("restauranteSelecionado", restaurante);
                startActivity(vaiParaTelaReserva);
            }
        });


        HashMap<String, Integer> arquivoFotos = new HashMap<>();
        arquivoFotos.put("Imagem 1", R.drawable.img3);
        arquivoFotos.put("Imagem 2", R.drawable.img4);
        arquivoFotos.put("Imagem 3", R.drawable.img5);
        arquivoFotos.put("Imagem 4", R.drawable.img6);

        for(String nome : arquivoFotos.keySet()){
            TextSliderView tsv = new TextSliderView(this);
            tsv.description(nome)
                    .image(arquivoFotos.get(nome))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            slider.addSlider(tsv);
        }

        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setDuration(10000);
    }

    private void inicializaComponentes(){
        txtNomeRest = findViewById(R.id.txtDetalhesNomeRest);
        txtTipoCulinaria = findViewById(R.id.txtDetalhesTipoCulinaria);
        txtCidadeEstado = findViewById(R.id.txtDetalhesCidadeRest);
        txtEnderecoRest = findViewById(R.id.txtDetalhesEndereco);
        txtHorarioFunc = findViewById(R.id.txtDetalhesHorarioFunc);
        btFacaReserva = findViewById(R.id.btDetalhesFacaReserva);
        restaurante = new Restaurante();
        slider = findViewById(R.id.sliderDetRest);
    }
}
