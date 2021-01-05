package com.example.habii.nbmreadingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {


    public List<Classes> bookList;

    Context context;

    public BooksAdapter(List<Classes> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_model,parent,false);

        return new BooksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, final int position) {

        final String postid = bookList.get(position).PostID;

        holder.bookname.setText(bookList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ReadingActivity.class);
                intent.putExtra("booklink", bookList.get(position).getId());
                context.startActivity(intent);
                Toast.makeText(context, bookList.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        TextView bookname;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;


            bookname = (TextView)mView.findViewById(R.id.bookname);
        }
    }
}
