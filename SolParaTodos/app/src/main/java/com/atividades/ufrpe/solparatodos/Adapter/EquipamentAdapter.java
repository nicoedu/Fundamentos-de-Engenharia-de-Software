package com.atividades.ufrpe.solparatodos.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.atividades.ufrpe.solparatodos.Dominio.Equipamento;
import com.atividades.ufrpe.solparatodos.Dominio.Estado;
import com.atividades.ufrpe.solparatodos.R;

import java.util.ArrayList;

/**
 * Created by Pichau on 22/08/2017.
 */

public class EquipamentAdapter extends ArrayAdapter<Equipamento> {
    private ArrayList<Equipamento> listaEquipamento;
    private Context context;

    public EquipamentAdapter(Context context, ArrayList<Equipamento> equipamentos) {
        super(context, 0, equipamentos);
        this.listaEquipamento = equipamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (listaEquipamento != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listaequipamento, parent, false);

            TextView nomeEquipamento = (TextView) view.findViewById(R.id.nomeEquipId);
            TextView estadoEquipamento = (TextView) view.findViewById(R.id.estadoEquipId);
            TextView consumoEquipamento = (TextView) view.findViewById(R.id.consumoEquipID);

            Equipamento equipamento = listaEquipamento.get(position);

            String estado;
            int cor;
            if (equipamento.getEstado().equals(Estado.DELISGADO.getValor())) {
                estado = "Desigado";
                cor = ContextCompat.getColor(context, R.color.vermelho);
            } else {
                estado = "Ligado";
                cor = ContextCompat.getColor(context, R.color.verde);
            }

            estadoEquipamento.setText(estado);
            estadoEquipamento.setTextColor(cor);
            nomeEquipamento.setText(equipamento.getNome());
            consumoEquipamento.setText(String.valueOf(equipamento.getConsumoGeral() + equipamento.getConsumo()));
        }

        return view;
    }
}
