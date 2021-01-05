package com.example.habii.nbmreadingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.ViewHolder> {

    public List<TeachersModelClass> teachersModelClassList;

    Context context;


    public TeachersAdapter(List<TeachersModelClass> teachersModelClassList, Context context) {
        this.teachersModelClassList = teachersModelClassList;
        this.context = context;
    }



    @NonNull
    @Override
    public TeachersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_model,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersAdapter.ViewHolder holder, int position) {

        final String post_id = teachersModelClassList.get(position).PostID;
        final  String email = teachersModelClassList.get(position).getEmail();

        holder.name.setText(teachersModelClassList.get(position).getName());
        holder.depertment.setText(teachersModelClassList.get(position).getDepertment()+" - Depertment");
        holder.initail.setText("["+teachersModelClassList.get(position).getInitial()+"]");
        String url = teachersModelClassList.get(position).getPic();

        RequestOptions placeholderReq = new RequestOptions();
        placeholderReq.placeholder(R.drawable.user);


        Glide.with(context).setDefaultRequestOptions(placeholderReq).load(url).into(holder.pic);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ClassRoomActivity.class);
                intent.putExtra("postid", post_id);
                context.startActivity(intent);

            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email));
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return teachersModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        View mView;

        TextView name;
        TextView initail;
        TextView depertment;
        CircleImageView pic;
        ImageView email;

        public ViewHolder(View itemView) {
            super(itemView);


            mView = itemView;

            name = (TextView)mView.findViewById(R.id.name);
            initail = (TextView)mView.findViewById(R.id.initial);
            depertment = (TextView)mView.findViewById(R.id.depertment);
            pic = (CircleImageView) mView.findViewById(R.id.profilepic);
            email = (ImageView)mView.findViewById(R.id.email);

        }
    }
}
