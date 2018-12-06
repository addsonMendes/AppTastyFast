package br.com.tastyfast.tastyfastapp.interfaces;

import java.util.List;

import br.com.tastyfast.tastyfastapp.model.Restaurante;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestauranteService {

    @GET("restaurante")
    Call<List<Restaurante>> buscarRestaurantes();
}
