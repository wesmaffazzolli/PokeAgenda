package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button login;
    EditText usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.loginTextView);
        login = (Button)findViewById(R.id.loginButton);
        usuario = (EditText)findViewById(R.id.usuarioEditText);
        senha = (EditText)findViewById(R.id.senhaEditText);

    }

    public void autenticar(View view) {
        if(!usuario.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()) {
            //Validações
            String usuarioDigitado = usuario.getText().toString();
            String senhaDigitada = senha.getText().toString();

            //Puxar WebService de Validação AQUI....

            if(usuarioDigitado.equals("teste") && senhaDigitada.equals("123")) {
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

}


