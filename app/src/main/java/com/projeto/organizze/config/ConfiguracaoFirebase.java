package com.projeto.organizze.config;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class ConfiguracaoFirebase {
    // retorna a instacia do firebasedatabase

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    public static DatabaseReference getFirebaseDatabase() {
        if (firebase ==null) {
            firebase = FirebaseDatabase.getInstance().getReference();

        }
        return firebase;
    }
    // retorna a instancia
    public static FirebaseAuth getFirebaseAutenticacao() {
        autenticacao = FirebaseAuth.getInstance();
        if(autenticacao==null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }


    
}
