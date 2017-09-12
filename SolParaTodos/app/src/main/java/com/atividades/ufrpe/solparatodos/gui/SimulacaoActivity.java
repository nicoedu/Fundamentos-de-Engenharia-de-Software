package com.atividades.ufrpe.solparatodos.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atividades.ufrpe.solparatodos.Dominio.Equipamento;
import com.atividades.ufrpe.solparatodos.Dominio.Painel;
import com.atividades.ufrpe.solparatodos.Dominio.Variaveis;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class SimulacaoActivity extends AppCompatActivity {

    private TextView geracao;
    private TextView consumo;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference firebaseReferenceVariavel;
    private DatabaseReference firebaseReferenceSistema;
    private DatabaseReference firebaseReferenceEquipamentos;
    private Painel painel;
    private Variaveis variaveis;
    private double valorConsumo;
    private int contadorEquipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulacao);

        geracao = (TextView) findViewById(R.id.tvInfoGeracaoID);
        consumo = (TextView) findViewById(R.id.tvInfoConsumoID);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseReferenceVariavel = databaseReferencia.child("variavel").child(firebaseAuth.getCurrentUser().getUid());
        firebaseReferenceSistema = databaseReferencia.child("painel").child(firebaseAuth.getCurrentUser().getUid());
        firebaseReferenceEquipamentos = databaseReferencia.child("equipamento").child(firebaseAuth.getCurrentUser().getUid());


        firebaseReferenceVariavel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                variaveis = dataSnapshot.getValue(Variaveis.class);
                textoGeracao();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        firebaseReferenceSistema.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                painel = dataSnapshot.getValue(Painel.class);
                textoGeracao();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        firebaseReferenceEquipamentos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valorConsumo = 0;
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Equipamento equipamento = dados.getValue(Equipamento.class);
                    valorConsumo += (equipamento.getConsumo() + equipamento.getConsumoGeral());
                    contadorEquipamento += 1;
                }
                textoConsumo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void textoGeracao() {
        if (variaveis != null && painel != null) {
            String saida = "Com base nas variaveis obtidas em " + new SimpleDateFormat("dd/MM/yyyy").format(variaveis.getData()) + '\n';
            BigDecimal output = new BigDecimal(painel.getTamanho() * (painel.getAproveitamento() / 100) * variaveis.getRadiacao());
            output = output.setScale(2, RoundingMode.HALF_UP);
            saida += "O " + painel.getNome() + " deve gerar " + output + " Watts nos próximos 7 dias.";
            geracao.setText(saida);

        } else {
            geracao.setText("Certifique que você cadastrou um sistema e que obteve as variáveis de ambiente através do GPS.");
        }
    }

    private void textoConsumo() {
        if (contadorEquipamento > 0) {
            String saida = "Você consumiu, através de " + contadorEquipamento + " equipamento(s) eletrônico(s), um total de ";
            BigDecimal output = new BigDecimal(valorConsumo);
            output = output.setScale(2, RoundingMode.HALF_UP);
            saida += output + " Watts.";
            consumo.setText(saida);
        } else {
            consumo.setText("Você ainda não possui equipamentos eletrônicos cadastrados.");
        }

    }
}
