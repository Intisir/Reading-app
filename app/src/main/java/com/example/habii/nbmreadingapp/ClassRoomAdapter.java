package com.example.habii.nbmreadingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Random;

public class ClassRoomAdapter extends RecyclerView.Adapter<ClassRoomAdapter.ViewHolder> {


    public List<Classes> classesList;

    Context context;

    public ClassRoomAdapter(List<Classes> classesList, Context context) {
        this.classesList = classesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_class_model,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassRoomAdapter.ViewHolder holder, int position) {


        final String classid = classesList.get(position).PostID;

        final String teacherid = classesList.get(position).PostID2;


        holder.className.setText(classesList.get(position).getId());
        holder.coursename.setText(classesList.get(position).getName());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Random rnd = new Random();
        String[] col = {

                "#B71C1C",
                "#880E4F",
                "#4A148C",
                "#311B92",
                "#1A237E",
                "#0D47A1",
                "#01579B",
                "#006064",
                "#004D40",
                "#1B5E20",
                "#33691E",
                "#1B5E20",
                "#827717",
                "#F57F17",
                "#E65100",
                "#BF360C",
                "#3E2723",
                "#212121",
                "#000000",
                "#3E2723"
        };

        holder.unknown.setBackgroundColor(Color.parseColor(col[rnd.nextInt(20)]));



        firebaseFirestore.collection("Teachers").document(teacherid).collection("Class")
                .document(classid).collection("Students").document(mAuth.getCurrentUser().getUid().toString())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.getResult().exists()){

                    holder.join.setVisibility(View.GONE);
                    holder.visit.setVisibility(View.VISIBLE);

                }else{

                    holder.join.setVisibility(View.VISIBLE);
                    holder.visit.setVisibility(View.GONE);

                }

            }
        });




        holder.visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("postid", classid);
                intent.putExtra("teachersid", teacherid);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return classesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView className,coursename;

        ImageView join,visit,unknown;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            className = (TextView)mView.findViewById(R.id.Classname);
            coursename = (TextView)mView.findViewById(R.id.coursename);
            join = (ImageView)mView.findViewById(R.id.join);

            visit = (ImageView)mView.findViewById(R.id.visit);

            unknown = (ImageView)mView.findViewById(R.id.unknown);

        }
    }
}
