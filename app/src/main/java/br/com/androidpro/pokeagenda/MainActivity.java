package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.loginButton);
        usuario = (EditText)findViewById(R.id.usuarioEditText);
        senha = (EditText)findViewById(R.id.senhaEditText);

    }

    public void autenticar(View view) {
        if(usuario.getText().toString() != "" && senha.getText().toString() != "") {
            //Validações
            String usuarioDigitado = usuario.getText().toString();
            String senhaDigitada = usuario.getText().toString();

            if(usuarioDigitado.equals("teste") && senhaDigitada.equals("123")) {
                chamaActivity(welcomeActivity.class);
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

}


