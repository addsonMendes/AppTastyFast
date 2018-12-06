package br.com.tastyfast.tastyfastapp.util;

import android.content.SharedPreferences;

import br.com.tastyfast.tastyfastapp.model.Cliente;

public class UsuarioPreference {

    public Cliente buscaUsuarioLogado(SharedPreferences preferences){

        Cliente clienteLogado = new Cliente();
        clienteLogado.setIdCliente(preferences.getInt("idUsuarioLogado", 0));
        clienteLogado.setNome(preferences.getString("nomeUsuario", ""));
        clienteLogado.setEmail(preferences.getString("emailUsuario", ""));

        return clienteLogado;
    }
}
