package com.atividades.ufrpe.solparatodos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atividades.ufrpe.solparatodos.Dominio.Equipamento;
import com.atividades.ufrpe.solparatodos.Infra.Base64Custom;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EquipamentosActivity extends AppCompatActivity {

    private Button btInsereEquipamento;
    private EditText etNomeEquipamento;
    private EditText etConsumoEquipamento;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipamentos);

        btInsereEquipamento = (Button) findViewById(R.id.btInsereEquipamentoId);
        etNomeEquipamento = (EditText) findViewById(R.id.etNomeEquipamentoId);
        etConsumoEquipamento = (EditText) findViewById(R.id.etConsumoEquipamentoId);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebase = databaseReference.child("equipamento").child(firebaseAuth.getCurrentUser().getUid());

        btInsereEquipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }


    private void cadastrar() {

        Equipamento equipamento = new Equipamento();
        equipamento.setNome(etNomeEquipamento.getText().toString());
        equipamento.setConsumoPadrao(Float.valueOf(etConsumoEquipamento.getText().toString()));
        equipamento.setEstado("0");
        equipamento.setConsumoGeral(0);
        equipamento.setConsumo(0);
        String id = Base64Custom.codificarBase64(equipamento.getNome());
        equipamento.setIdentificador(id);
        firebase.child(equipamento.getIdentificador()).setValue(equipamento);
        startActivity(new Intent(EquipamentosActivity.this, HouseControlActivity.class));
        finish();
    }
}
