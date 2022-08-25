package com.projeto.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;
    // retorna a instancia
    public static FirebaseAuth getFirebaseAutenticacao() {
        autenticacao = FirebaseAuth.getInstance();
        if(autenticacao==null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }


    
}
