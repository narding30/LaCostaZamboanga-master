package com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

public class ArrivedViewHolder extends RecyclerView.ViewHolder {

    public TextView vname,vtype,vdaysched,vOrgin,vDestination,vETD,vETA,vHoursTrav,vTimeArrived;
    public ImageView vImage;


    public ArrivedViewHolder(View itemView) {
        super(itemView);

        vname = itemView.findViewById(R.id.TVArrivedVesselName);
        vtype = itemView.findViewById(R.id.TVArrivedVesselType);
        vdaysched = itemView.findViewById(R.id.TVArrivedSchedDay);
        vOrgin = itemView.findViewById(R.id.TVArrivedOrigin);
        vDestination = itemView.findViewById(R.id.TVArrivedDestination);
        vETD = itemView.findViewById(R.id.TVArrivedETD);
        vETA = itemView.findViewById(R.id.TVArrivedETA);
        vHoursTrav = itemView.findViewById(R.id.TVArrivedHoursTravelled);
        vTimeArrived = itemView.findViewById(R.id.TVArrivedActualTime);
        vImage = itemView.findViewById(R.id.TVArrivedImageVessel);
    }
}
