package br.com.tastyfast.tastyfastapp.config;

import br.com.tastyfast.tastyfastapp.interfaces.ClienteService;
import br.com.tastyfast.tastyfastapp.interfaces.DispositivoService;
import br.com.tastyfast.tastyfastapp.interfaces.ReservaService;
import br.com.tastyfast.tastyfastapp.interfaces.RestauranteService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.9:8080/tastyfastservice/api/")
                        //.baseUrl("http://ec2-54-152-254-213.compute-1.amazonaws.com:8080/tastyfastservice/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
    }

    public RestauranteService getRestauranteService(){
        return this.retrofit.create(RestauranteService.class);
    }

    public ClienteService getClienteService(){
        return this.retrofit.create(ClienteService.class);
    }

    public ReservaService getReservaService(){
        return this.retrofit.create(ReservaService.class);
    }

    public DispositivoService getDispositivoService(){
        return this.retrofit.create(DispositivoService.class);
    }
}
