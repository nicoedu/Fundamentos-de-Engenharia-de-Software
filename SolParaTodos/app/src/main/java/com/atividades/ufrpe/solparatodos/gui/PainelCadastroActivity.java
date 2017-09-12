package com.atividades.ufrpe.solparatodos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atividades.ufrpe.solparatodos.Dominio.Painel;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PainelCadastroActivity extends AppCompatActivity {

    private EditText entradaNome;
    private EditText entradaTamanho;
    private EditText entradaAproveitamento;
    private Button botaoCadastrar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference painelReferencia = databaseReference.child("painel");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_cadastro);
        entradaNome = (EditText) findViewById(R.id.etNomeId);
        entradaTamanho = (EditText) findViewById(R.id.etTamanhoID);
        entradaAproveitamento = (EditText) findViewById(R.id.etAproveitamentoID);
        botaoCadastrar = (Button) findViewById(R.id.botaoInserePainelId);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastar();
            }
        });


    }

    private void cadastar() {
        Painel painel = new Painel();
        painel.setNome(entradaNome.getText().toString());
        painel.setTamanho(Long.valueOf(entradaTamanho.getText().toString()));
        painel.setAproveitamento(Float.valueOf(entradaAproveitamento.getText().toString()));

        painelReferencia.child(firebaseAuth.getCurrentUser().getUid()).setValue(painel);
        startActivity(new Intent(PainelCadastroActivity.this, PaineisActivity.class));
        finish();

    }
}
