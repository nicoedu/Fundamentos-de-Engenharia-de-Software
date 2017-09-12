package com.atividades.ufrpe.solparatodos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atividades.ufrpe.solparatodos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etSenha;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmailID);
        etSenha = (EditText) findViewById(R.id.etSenhaId);
        Button login = (Button) findViewById(R.id.btLogarId);
        final Button cadastro = (Button) findViewById(R.id.btCadastroId);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }

    private void logar() {
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if (validaCampos(email, senha)) {
            firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tentativa de login falhou" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void cadastrar() {
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        if (validaCampos(email, senha)) {
            firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        logar();
                    } else {
                        Toast.makeText(LoginActivity.this, "Falha no Cadastro" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validaCampos(String email, String senha) {
        boolean validacao = true;

        if (email.trim().length() == 0) {
            etEmail.setError("Email inválido");
            validacao = false;
        }
        if (senha.trim().length() == 0) {
            etSenha.setError("Senha inválida");
            validacao = false;
        }

        return validacao;
    }
}
