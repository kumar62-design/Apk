package com.example.internshalaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {


    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.firstname.setText(model.getFirstname());
        holder.lastname.setText(model.getLastname());
        holder.dateofbirth.setText(model.getDateofbirth());
        holder.hometown.setText(model.getHometown());

        Glide.with(holder.avtarIv.getContext()).load(model.getPimg()).into(holder.avtarIv);


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

                CircleImageView avtarIv;
                TextView firstname,lastname,dateofbirth,hometown;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            avtarIv = itemView.findViewById(R.id.avtarIv);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            dateofbirth = itemView.findViewById(R.id.dateofbirth);
            hometown = itemView.findViewById(R.id.hometown);
        }
    }
}
