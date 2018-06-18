package com.rakantao.pcg.lacostazamboanga.PCGPersonnel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

import org.w3c.dom.Text;

import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ViewHolder> {

    public List<String> fileNameList;
    public List<String> fileDoneList;

    public UploadListAdapter(List<String> fileNameList, List<String> fileDoneList){
        this.fileDoneList = fileDoneList;
        this.fileNameList = fileNameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String fileName = fileNameList.get(position);
        holder.filenameView.setText(fileName);

        String fileDone = fileDoneList.get(position);

        if (fileDone.equals("Uploading")){

            holder.fileDoneView.setImageResource(R.drawable.progress);

        } else {

            holder.fileDoneView.setImageResource(R.drawable.checked);

        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView filenameView;
        public ImageView fileDoneView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            filenameView = (TextView) mView.findViewById(R.id.upload_filename);
            fileDoneView = (ImageView) mView.findViewById(R.id.upload_loading);

        }
    }
}
