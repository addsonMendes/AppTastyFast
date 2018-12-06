package br.com.tastyfast.tastyfastapp.interfaces;

import java.util.List;

import br.com.tastyfast.tastyfastapp.model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ClienteService {

    @GET("cliente")
    Call<List<Cliente>> buscarClientes();

    @GET("cliente/{email}/{senha}")
    Call<Cliente> login(@Path("email") String email, @Path("senha") String senha);

    @POST("cliente")
    Call<Cliente> cadastrarCliente(@Body Cliente cliente);

    @PUT("cliente")
    Call<Cliente> atualizaToken(@Body Cliente cliente);
}
