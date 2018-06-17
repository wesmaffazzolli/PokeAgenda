package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;



public class MainActivity extends AppCompatActivity {

    TextView text;
    Button login;
    EditText usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.loginTextView);
        login = (Button) findViewById(R.id.loginButton);
        usuario = (EditText) findViewById(R.id.usuarioEditText);
        senha = (EditText) findViewById(R.id.senhaEditText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticar();
            }
        });
    }

    public void autenticar() {
        if (!usuario.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()) {
            final String usuarioDigitado = usuario.getText().toString();
            String senhaDigitada = senha.getText().toString();
            Call<Treinador> call = new RetrofitConfig().getPokeAgendaAPI().autenticar(usuarioDigitado, senhaDigitada);
            call.enqueue(new Callback<Treinador>() {
                @Override
                public void onResponse(Call<Treinador> call, retrofit2.Response<Treinador> response) {
                    Treinador treinador = response.body();
                    if (treinador == null) {
                        text.setText("Acesso negado!");
                        text.setTextColor(Color.RED);
                    } else {
                        chamaActivity(NavigationActivity.class, treinador);
                        text.setText("Login");
                        text.setTextColor(Color.BLACK);
                        usuario.setText("");
                        senha.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Treinador> call, Throwable t) {
                    Log.e("PokeAgendaAPI   ", "Erro ao buscar o treinador:" + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Preencher usuário e senha!", Toast.LENGTH_LONG).show();
        }
    }


    public void chamaActivity(Class cls, Treinador tr) {
        Intent it = new Intent(this, cls);
        it.putExtra("idTreinador",tr.getIdTreinador());
        it.putExtra("nomeTreinador",tr.getNomeTreinador());
        startActivity(it);
    }
}


