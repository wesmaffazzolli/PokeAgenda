package br.com.androidpro.pokeagenda;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CadastrarPokemon extends AppCompatActivity {

    public Bitmap imageBitmap;
    private ImageView imagem;
    EditText nome, especie, peso, altura;
    AlertDialog alerta;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private int idTreinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pokemon);

        nome = (EditText) findViewById(R.id.nomeEditText);
        especie = (EditText) findViewById(R.id.especieEditText);
        peso = (EditText) findViewById(R.id.pesoEditText);
        altura = (EditText) findViewById(R.id.alturaEditText);
        imagem = (ImageView) findViewById(R.id.imageViewCadastro);

        //Código da flecha de botão voltar nativo do Android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent myIntent = getIntent();
        idTreinador = myIntent.getIntExtra("idTreinador", 0);

        //Método que faz a permissão para acessar a galeria de fotos
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        //Código que realiza a chamada das funções relacionadas ao botão que seleciona imagens da galeria.
        Button botaoSelecionar = (Button) findViewById(R.id.buttonSelecionarImagem);
        botaoSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

        //Código que realiza a chamada das funções relacionadas ao botão que tira foto.
        Button tirarFoto = (Button) findViewById(R.id.buttonTirarFoto);
        tirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, TIRAR_FOTO);
                }
            }
        });
    }

    protected void enviarFormularioCadastro(View view) {
        if (!nome.getText().toString().isEmpty() && !especie.getText().toString().isEmpty() && !altura.getText().toString().isEmpty() && !peso.getText().toString().isEmpty()) {
            if (imagem.getDrawable() != null) {
                double p = Double.parseDouble(peso.getText().toString());
                double a = Double.parseDouble(altura.getText().toString());
                Call<Integer> call = new RetrofitConfig().getPokeAgendaAPI().insertPokemon(nome.getText().toString(), especie.getText().toString(), p, a, idTreinador);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        int idResposta = response.body();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte imagemBytes[] = stream.toByteArray();
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        Pokemon pokemon = new Pokemon();
                        pokemon.setIdPokemon(idResposta);
                        pokemon.setFoto(imagemBytes);
                        realm.copyToRealm(pokemon);
                        realm.commitTransaction();
                        realm.close();
                        //Toast.makeText(getApplicationContext(), "Foto  armazenada  com  sucesso", Toast.LENGTH_LONG).show();
                        instaciaDialog("Sucesso!", "O cadastro foi realizado.");
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("PokeAgendaAPI   ", "Erro ao inserir pokemon: " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(this, "Selecione ou tire uma foto!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    //Código que recebe o retorno das funções de selecionar imagem da galeria ou tirar foto (RETORNO = OBJETO IMAGEM)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se o retorno vier da galeria de fotos, este é o código que trata o retorno.
        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));

            //Adaptar/redimensionar o tamanho da imagem para o tamanho da tela do dispositivo.
            //Resolve o problema do erro: OpenGLRenderer: Bitmap too large to be uploaded into a texture (2432x4320, max=4096x4096)
            int scale = (int) (imagemGaleria.getHeight() * (512.0 / imagemGaleria.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(imagemGaleria, 512, scale, true);
            imageBitmap = scaled;
            //imagem.setImageBitmap(imagemGaleria);
            imagem.setImageBitmap(imageBitmap);
        }

        //Se o retorno vier da câmera, este é o código que trata o retorno.
        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imagem.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // A permissão foi concedida. Pode continuar
            } else {
                // A permissão foi negada. Precisa ver o que deve ser desabilitado
            }
            return;
        }
    }

    private void instaciaDialog(String title, String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(title);
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
