package br.com.tastyfast.tastyfastapp.interfaces;

import java.util.List;

import br.com.tastyfast.tastyfastapp.model.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservaService {

    @POST("reserva")
    Call<Reserva> cadastrarReserva(@Body Reserva reserva);

    @GET("reserva/historico/{idCliente}")
    Call<List<Reserva>> listaReservasDoUsuario(@Path("idCliente") String idCliente);
}
