package com.asparmar6262.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.viewHolder> {
    Context context;
    ArrayList<Patient> patientList;

    public DoctorAdapter(Context context, ArrayList<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public DoctorAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_recycler_view,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.viewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.patientName.setText(patient.getName());
        holder.tvdescription.setText(patient.getDescription());
        Picasso.get().load(patient.getImage()).into(holder.ivPatientImage);

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + patient.getMobileNo()));
                context.startActivity(intent);
            }
        });

        holder.ivVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //yet to implement
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView patientName, tvdescription;
        ImageView ivCall, ivVideoCall, ivPatientImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivCall = itemView.findViewById(R.id.ivCall);
            ivVideoCall = itemView.findViewById(R.id.ivVideoCall);
            ivPatientImage = itemView.findViewById(R.id.ivPatientImage);
            patientName = itemView.findViewById(R.id.patientName);
            tvdescription = itemView.findViewById(R.id.tvDiscription);
        }
    }
}
