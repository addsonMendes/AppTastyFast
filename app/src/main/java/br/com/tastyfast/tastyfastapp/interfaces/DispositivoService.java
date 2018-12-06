package br.com.tastyfast.tastyfastapp.interfaces;

import br.com.tastyfast.tastyfastapp.model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface DispositivoService {

    @POST("dispositivo/cliente")
    Call<Void> enviaToken(@Body Cliente cliente);
}
