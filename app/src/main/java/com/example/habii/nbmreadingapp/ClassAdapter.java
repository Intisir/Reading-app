package com.example.habii.nbmreadingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    public List<Classes> claasNameList;

    Context context;

    public ClassAdapter(List<Classes> claasNameList, Context context) {
        this.claasNameList = claasNameList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_model,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {

        final String postid = claasNameList.get(position).PostID;

        holder.className.setText(claasNameList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TeachersClassActivity.class);
                intent.putExtra("postid", postid);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return claasNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView className;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            className = (TextView)mView.findViewById(R.id.Classname);
        }
    }
}
