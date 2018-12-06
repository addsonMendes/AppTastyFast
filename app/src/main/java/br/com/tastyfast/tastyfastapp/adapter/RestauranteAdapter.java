package br.com.tastyfast.tastyfastapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.tastyfast.tastyfastapp.R;
import br.com.tastyfast.tastyfastapp.model.Restaurante;

public class RestauranteAdapter extends BaseAdapter {

    private final List<Restaurante> restaurantes;
    private final Activity activity;

    public RestauranteAdapter(List<Restaurante> restaurantes, Activity activity) {
        this.restaurantes = restaurantes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.listagem_restaurantes_personalizada, parent,false);
        Restaurante restaurante = restaurantes.get(position);

        TextView nomeRestaurante = view.findViewById(R.id.txtLayResNomeRestaurante);
        TextView cidadeEstado = view.findViewById(R.id.txtLayResCidadeEstado);
        TextView tipoCulinaria = (TextView) view.findViewById(R.id.txtLayResTipoCulinaria);

        nomeRestaurante.setText(restaurante.getNome());
        cidadeEstado.setText(restaurante.getEndereco().getCidade() + " - " + restaurante.getEndereco().getEstado());
        tipoCulinaria.setText(restaurante.getTipoCulinaria());
        return view;
    }
}
