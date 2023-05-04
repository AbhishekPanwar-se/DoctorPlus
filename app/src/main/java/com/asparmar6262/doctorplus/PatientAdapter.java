package com.asparmar6262.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.viewHolder> {
    Context context;
    ArrayList<Doctor> doctorList;

    public PatientAdapter(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public PatientAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_recycler_view,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.viewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.DoctorName.setText(doctor.getName());
        holder.tvdescription.setText(doctor.getSpecialties());
        Picasso.get().load(doctor.getImage()).into(holder.ivDoctorImage);

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + doctor.getMobileNo()));
                context.startActivity(intent);
            }
        });

        holder.ivVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //yet to implement
            }
        });

        holder.bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookAppoinmentPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView DoctorName, tvdescription;
        Button bookbtn;
        ImageView ivCall, ivVideoCall, ivDoctorImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivCall = itemView.findViewById(R.id.ivCall);
            ivVideoCall = itemView.findViewById(R.id.ivVideoCall);
            ivDoctorImage = itemView.findViewById(R.id.ivDoctorImage);
            DoctorName = itemView.findViewById(R.id.DoctorName);
            tvdescription = itemView.findViewById(R.id.tvDiscription);
            bookbtn = itemView.findViewById(R.id.bookbtn);
        }
    }
}
