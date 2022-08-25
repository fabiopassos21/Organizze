package com.projeto.organizze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.projeto.organizze.config.ConfiguracaoFirebase;
import com.projeto.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {
private EditText campoNome, campoEmail, campoSenha;
private Button botaoCadastrar;
private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    campoNome = findViewById(R.id.editNome);
    campoEmail = findViewById(R.id.editEmail);
    campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.button3);

botaoCadastrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();
        // validar campos
        if ( !textoNome.isEmpty() ){
            if ( !textoEmail.isEmpty() ){
                if ( !textoSenha.isEmpty() ){

            usuario = new Usuario();
            usuario.setNome(textoNome);
            usuario.setEmail(textoEmail);
            usuario.setSenha(textoSenha);
            CadastrarUsuario();
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha a senha!",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroActivity.this,
                        "Preencha o email!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {

            Toast.makeText(CadastroActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }

    }
});

    }

public void CadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
          usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Toast.makeText(CadastroActivity.this,
                        "Sucesso ao cadastrar usuario!",
                        Toast.LENGTH_SHORT).show();
            }else {
                String exececao = "";
                try {
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e){
                    exececao = "Digite uma senha mais forte !";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    exececao = "Por favor! digite um email valido";
                }catch (FirebaseAuthUserCollisionException e ) {
                    exececao = "Essa conta ja foi cadastrada";
                }catch (Exception e) {
                    exececao = "Erro ao cadastrar Usuario" + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(CadastroActivity.this,
                       exececao,
                        Toast.LENGTH_SHORT).show();
            }
        }});

}
}
