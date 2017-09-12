package com.atividades.ufrpe.solparatodos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atividades.ufrpe.solparatodos.Dominio.Painel;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaineisActivity extends AppCompatActivity {

    private Button botaoInserePainel;
    private TextView textoInfoPainel;
    private DatabaseReference firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paineis);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference().child("painel").child(firebaseAuth.getCurrentUser().getUid());
        botaoInserePainel = (Button) findViewById(R.id.botaoInserePainelId);
        textoInfoPainel = (TextView) findViewById(R.id.tvInfoSistemaID);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Painel painel = dataSnapshot.getValue(Painel.class);
                if (painel != null) {
                    textoInfoPainel.setText(painel.toString());
                    botaoInserePainel.setText("Atualizar");
                } else {
                    textoInfoPainel.setText("Você ainda não tem um Sistema de Painel Solar!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        botaoInserePainel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaineisActivity.this, PainelCadastroActivity.class));
            }
        });


    }
}
