package com.projeto.organizze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.organizze.config.ConfiguracaoFirebase;
import com.projeto.organizze.helper.Base64Custom;
import com.projeto.organizze.helper.DateUtil;
import com.projeto.organizze.model.Movimentacao;
import com.projeto.organizze.model.Usuario;

public class ReceitasActivity extends AppCompatActivity {
    private TextInputEditText campoData, campoCategoria,campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);



        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);

        campoData.setText(DateUtil.dataAtual());

        recuperarReceitaTotal();

    }
    public void salvarReceita (View view) {
        if (validarCamposReceita()){

            String data = campoData.getText().toString();
            movimentacao = new Movimentacao();
            Double valorRecuperado =  Double.parseDouble(campoValor.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData (data);
            movimentacao.setTipo ("r");


            Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);
            movimentacao.salvar(data);

        }

    }
    public Boolean validarCamposReceita  () {
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        // validar campos
        if ( !textoValor.isEmpty() ) {
            if ( !textoData.isEmpty() ) {
                if ( !textoCategoria.isEmpty() ) {
                    if ( !textoDescricao.isEmpty() ) {
                        return true;


                    } else {
                        Toast.makeText(ReceitasActivity.this,
                                "Preencha a descricao!",
                                Toast.LENGTH_SHORT).show();
                        return false;

                    }

                } else {
                    Toast.makeText(ReceitasActivity.this,
                            "Preencha a Categoria!",
                            Toast.LENGTH_SHORT).show();
                    return false;

                }

            } else {
                Toast.makeText(ReceitasActivity.this,
                        "Data nao foi preenchida !",
                        Toast.LENGTH_SHORT).show();
                return false;

            }

        } else {
            Toast.makeText(ReceitasActivity.this,
                    "Preencha o Valor!",
                    Toast.LENGTH_SHORT).show();
            return false;

        }

    }
    public void recuperarReceitaTotal() {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("Usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void atualizarReceita   (Double receita){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("receitaTotal").setValue(receita);
    }
}