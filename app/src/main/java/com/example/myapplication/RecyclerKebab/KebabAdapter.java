package com.example.myapplication.RecyclerKebab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerOpinion.Opinion;
import com.example.myapplication.RecyclerOpinion.OpinionList;
import com.example.myapplication.RecyclerOpinion.OpinionViewHolder;

public class KebabAdapter  extends RecyclerView.Adapter<KebabViewHolder>{

    private KebabList kebabList;

    public KebabAdapter(KebabList kebabList) {

    }

    @NonNull
    @Override
    public KebabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewcell = inflater.inflate(R.layout.kebab_celda, parent, false);

        KebabViewHolder kebabHolder = new KebabViewHolder(viewcell);


        return kebabHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KebabViewHolder holder, int position) {
        Kebab kebab= this.kebabList.getKebabs().get(position);
        holder.ShowKebab(kebab);
    }

    @Override
    public int getItemCount() {
        return this.kebabList.getKebabs().size();
    }
}
