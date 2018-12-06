package br.com.tastyfast.tastyfastapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.tastyfast.tastyfastapp.model.Horario;
import br.com.tastyfast.tastyfastapp.model.Mesa;
import br.com.tastyfast.tastyfastapp.model.Reserva;
import br.com.tastyfast.tastyfastapp.model.Restaurante;
import br.com.tastyfast.tastyfastapp.util.Mensagens;

public class ReservaActivity extends AppCompatActivity {

    private TextView txtNomeRestaurante;
    private EditText edtData, edtHorario;
    private ListView lstHorarios;
    private Button btContinuar;
    private Spinner spnMesa;
    private CheckBox chkCardapio;
    private final Calendar calendario = Calendar.getInstance();
    private Intent intent = new Intent();
    private Restaurante restaurante;
    private Reserva reserva;
    private Horario horario;
    private Mesa mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        inicializaComponentes();

        intent = getIntent();
        restaurante = new Restaurante();
        restaurante = (Restaurante) intent.getSerializableExtra("restauranteSelecionado");
        reserva = new Reserva();
        horario = new Horario();
        mesa = new Mesa();

        txtNomeRestaurante.setText(restaurante.getNome());

        ArrayAdapter<Horario> adapter = new ArrayAdapter<>(ReservaActivity.this, android.R.layout.simple_list_item_1, restaurante.getHorarios());
        lstHorarios.setAdapter(adapter);

        if(restaurante.getMesas() == null){
            restaurante.setMesas(new ArrayList<Mesa>());
            spnMesa.setEnabled(false);
            Mensagens.exibeMensagemJanela(ReservaActivity.this, "Ainda não existem mesas cadastradas, impossibilitando a realização de reservas. " +
                                                                              "Entre em contato com o restaurante em questão para maiores informações!");
        } else {
            ArrayAdapter<Mesa> spnAdapter = new ArrayAdapter<>(ReservaActivity.this, android.R.layout.simple_list_item_1, restaurante.getMesas());
            spnMesa.setAdapter(spnAdapter);
        }

        lstHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                horario = (Horario) parent.getItemAtPosition(position);
                edtHorario.setText(horario.getHorario());
            }
        });

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!camposPreechidos()){
                    Mensagens.exibeToast(ReservaActivity.this, "Todos os campos devem ser preenchidos para prosseguir!");
                } else if(dataInvalida()){
                    Mensagens.exibeToast(ReservaActivity.this, "Não é possível criar uma reserva com data anterior a atual!");
                } else{
                    reserva.setDataReserva(edtData.getText().toString());
                    reserva.setMesa( (Mesa) spnMesa.getSelectedItem() );
                    reserva.setHorario(horario.getHorario());

                    if(chkCardapio.isChecked()){
                        AlertDialog alerta;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReservaActivity.this);
                        builder.setTitle("Tasty Fast - Atenção");
                        builder.setMessage("A indicação do cardápio na reserva não " +
                                "garante que o restaurante possuirá tal " +
                                "prato!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent vaiParaTelaDeCardapio = new Intent(ReservaActivity.this, CardapioActivity.class);
                                vaiParaTelaDeCardapio.putExtra("restaurante", restaurante);
                                vaiParaTelaDeCardapio.putExtra("reserva", reserva);
                                vaiParaTelaDeCardapio.putExtra("mesaSelecionada", mesa);
                                startActivity(vaiParaTelaDeCardapio);
                            }
                        });
                        alerta = builder.create();
                        alerta.show();
                    } else{
                        carregaTelaDeConfirmacao();
                    }
                }
            }
        });

        final DatePickerDialog.OnDateSetListener data = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                atualizaData();
            }
        };

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReservaActivity.this, data, calendario.get(Calendar.YEAR),
                        calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private boolean camposPreechidos(){
        boolean preenchido = false;

        if( edtData.getText().toString().equals("") || edtHorario.getText().toString().equals("") || (!spnMesa.isEnabled()) )
            preenchido = false;
        else
            preenchido = true;

        return preenchido;
    }

    private boolean dataInvalida(){
        return false;
    }

    private void inicializaComponentes(){
        txtNomeRestaurante = findViewById(R.id.txtReservaNomeRest);
        edtData = findViewById(R.id.edtReservaDia);
        edtHorario = findViewById(R.id.edtReservaHorarioSelecionado);
        spnMesa = findViewById(R.id.spnReservaMesa);
        lstHorarios = findViewById(R.id.lstReservasMesasQtdPessoas);
        btContinuar = findViewById(R.id.btReservaConfirmaReserva);
        chkCardapio = findViewById(R.id.chkReservaCardapio);

    }

    private void atualizaData(){
        String formatoData = "dd/MM/yyyy";
        SimpleDateFormat sdf= new SimpleDateFormat(formatoData, new Locale("pt", "BR"));
        edtData.setText(sdf.format(calendario.getTime()));
    }

    public void carregaTelaDeConfirmacao(){
        Intent vaiParaTelaConfirmacaoReserva = new Intent(ReservaActivity.this, ConfirmacaoReservaActivity.class);
        vaiParaTelaConfirmacaoReserva.putExtra("restauranteSelecionado", restaurante);
        vaiParaTelaConfirmacaoReserva.putExtra("reserva", reserva);
        vaiParaTelaConfirmacaoReserva.putExtra("mesaSelecionada", mesa);
        startActivity(vaiParaTelaConfirmacaoReserva);
    }
}
