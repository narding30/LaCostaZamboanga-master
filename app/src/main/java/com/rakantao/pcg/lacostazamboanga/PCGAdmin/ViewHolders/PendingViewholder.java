package com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

public class PendingViewholder extends RecyclerView.ViewHolder {

    public TextView vesselname,vesseltype,origin,destination,departime,arrivaltime,runnabletime,schedday;
    public ImageView vesselimage;
    public Button btnclear;


    public PendingViewholder(View itemView) {
        super(itemView);

        vesselname = itemView.findViewById(R.id.TVPendingVesselName);
        vesseltype = itemView.findViewById(R.id.TVPendingVesselType);
        origin = itemView.findViewById(R.id.TVPendingOrigin);
        destination = itemView.findViewById(R.id.TVPendingDestination);
        departime = itemView.findViewById(R.id.TVPendingDepartureTime);
        arrivaltime = itemView.findViewById(R.id.TVPendingArrivalTime);
        runnabletime = itemView.findViewById(R.id.TVPendingRunningTime);
        schedday = itemView.findViewById(R.id.TVPendingSchedDay);

        vesselimage = itemView.findViewById(R.id.IVPendingImageVessel);

        btnclear = itemView.findViewById(R.id.BtnPendingClear);

    }
}
