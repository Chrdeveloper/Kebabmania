package com.example.myapplication.RecyclerKebab;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.KebabDetail;
import com.example.myapplication.R;

public class KebabViewHolder extends RecyclerView.ViewHolder{
    TextView nombre;
    TextView lugar;
    TextView nota;
    String id;
    Kebab kebab;
    Button vermas;
    public KebabViewHolder(@NonNull View itemView) {
        super(itemView);
        Context context = itemView.getContext();

        nombre = itemView.findViewById(R.id.nombreOfertaCell);
        lugar = itemView.findViewById(R.id.lugarOfertaCell);
        nota = itemView.findViewById(R.id.puntuacionOfertaCell);
        vermas = itemView.findViewById(R.id.botonOfertaCell);
        vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, KebabDetail.class);
                myIntent.putExtra("ID_KEBAB",id );
                myIntent.putExtra("NOMBRE_KEBAB", nombre.getText().toString());
                myIntent.putExtra("LUGAR_KEBAB", lugar.getText().toString());
                myIntent.putExtra("NOTA_KEBAB", nota.getText().toString());
                context.startActivity(myIntent);
            }
        });



    }
    public void ShowKebab(Kebab kebab){
        this.kebab = kebab;

        nombre.setText(kebab.getNombre());
        nota.setText(String.valueOf(kebab.getNotaMedia()));
        lugar.setText(kebab.getLugar());
        id = kebab.getId();

    }
}
