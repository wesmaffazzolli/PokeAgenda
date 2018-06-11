package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    // Variáveis Volley
    public static final String REQUEST_TAG = "UserAutentication";
    private RequestQueue mQueue;

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

        /* RETROFIT
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuarioDigitado = usuario.getText().toString();
                String senhaDigitada = senha.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", usuarioDigitado);
                params.put("senha", senhaDigitada);
                PokeAgendaAPI pokeAgendaTreinador = PokeAgendaAPI.retrofit.create(PokeAgendaAPI.class);
                final Call<Treinador> call = pokeAgendaTreinador.getTreinador();
                call.enqueue(new Callback<Treinador>() {
                    @Override
                    public void onResponse(Call<Treinador> call, retrofit2.Response<Treinador> response) {
                        int code = response.code();
                        if(code == 200) {
                            Treinador treinador = response.body();
                            Toast.makeText(getBaseContext(), "Nome do Treinador: " + treinador.getNomeTreinador(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Erro: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Treinador> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });*/
    }

    public void autenticar(View view) {
        if (!usuario.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()) {
            //WebService de Validação
            String usuarioDigitado = usuario.getText().toString();
            String senhaDigitada = senha.getText().toString();
            /* VOLLEY
            mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
            String url = "http://192.168.0.182:8080/SistemaCentral/webresources/pokeagenda/autenticar";
            Map<String, String> params = new HashMap<String, String>();
            params.put("login", usuarioDigitado);
            params.put("senha", senhaDigitada);
            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST,
                    url, new JSONObject(params), this, this);
            jsonRequest.setTag(REQUEST_TAG);
            mQueue.add(jsonRequest);*/
            if (usuarioDigitado.equals("teste") && senhaDigitada.equals("123")) {
                chamaActivity(NavigationActivity.class);
            } else {
                Toast.makeText(this, "Usuario ou senha inválidos!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Preencher usuário e senha!", Toast.LENGTH_LONG).show();
        }
    }


    public void chamaActivity(Class cls) {
        Intent it = new Intent(this, cls);
        startActivity(it);
    }

    /* VOLLEY
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {
        chamaActivity(NavigationActivity.class);
    }*/
}


