package com.atividades.ufrpe.solparatodos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.atividades.ufrpe.solparatodos.Adapter.EquipamentAdapter;
import com.atividades.ufrpe.solparatodos.Dominio.Equipamento;
import com.atividades.ufrpe.solparatodos.Dominio.Estado;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HouseControlActivity extends AppCompatActivity {

    private ListView listaEquipamentos;
    private Button botaoInserir;
    private DatabaseReference firebase;
    private ArrayList<Equipamento> arrayEquipamento;
    private ArrayAdapter adapter;
    private ValueEventListener valueEventListener;
    private TextView consumogeral;

    @Override
    protected void onStart() {
        super.onStart();
        handler();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_control);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference().child("equipamento").child(firebaseAuth.getCurrentUser().getUid());
        arrayEquipamento = new ArrayList<>();
        consumogeral = (TextView) findViewById(R.id.tvConsumoGeralID);
        botaoInserir = (Button) findViewById(R.id.botaoInsereEquipamentoId);
        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HouseControlActivity.this, EquipamentosActivity.class));
            }
        });

        listaEquipamentos = (ListView) findViewById(R.id.lvEquipamentosId);
        adapter = new EquipamentAdapter(this, arrayEquipamento);
        listaEquipamentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayEquipamento.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Equipamento equipamento = dados.getValue(Equipamento.class);
                    arrayEquipamento.add(equipamento);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listaEquipamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Equipamento equipamento = (Equipamento) listaEquipamentos.getAdapter().getItem(i);
                Map<String, Object> taskMap = new HashMap<String, Object>();
                String valor;
                if (equipamento.getEstado().equals(Estado.LIGADO.getValor())) {
                    valor = "0";
                    taskMap.put("consumoGeral", equipamento.getConsumoGeral() + equipamento.getConsumo());
                    taskMap.put("consumo", 0);
                } else {
                    Date date = new Date();
                    valor = "1";
                    taskMap.put("data", date);
                }
                taskMap.put("estado", valor);
                firebase.child(equipamento.getIdentificador()).updateChildren(taskMap);
                firebase.addValueEventListener(valueEventListener);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void handler() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                float contador = 0;
                for (Equipamento equipamento : arrayEquipamento) {
                    if (equipamento.getEstado().equals(Estado.LIGADO.getValor())) {
                        float consumo;
                        Map<String, Object> taskMap2 = new HashMap<String, Object>();
                        Date date = new Date();
                        consumo = ((date.getTime() - equipamento.getData().getTime()) / 1000) * (equipamento.getConsumoPadrao() / 3600);
                        contador += consumo;
                        taskMap2.put("consumo", consumo);
                        firebase.child(equipamento.getIdentificador()).updateChildren(taskMap2);
                    }
                    contador += equipamento.getConsumoGeral();
                }

                firebase.addValueEventListener(valueEventListener);
                adapter.notifyDataSetChanged();
                consumogeral.setText(String.valueOf(contador));
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }
}
