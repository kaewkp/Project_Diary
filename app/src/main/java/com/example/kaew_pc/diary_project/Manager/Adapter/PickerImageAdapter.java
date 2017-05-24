package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kaew_pc.diary_project.Activity.Note.ShowPicture;
import com.example.kaew_pc.diary_project.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekachart-PC on 16/5/2560.
 */

public class PickerImageAdapter extends RecyclerView.Adapter<PickerImageAdapter.ViewHolder> {

    private final ArrayList<Uri> mData;
    private Context context;
    private RecyclerView re;

    public PickerImageAdapter(Context con, RecyclerView r) {
        mData = new ArrayList<>();
        context = con;
        re = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note_create, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = re.getChildLayoutPosition(view);
                Intent intent = new Intent(context, ShowPicture.class);
                intent.putExtra("URI", mData);
                intent.putExtra("Touch", itemPosition);
                context.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri data = mData.get(position);
        Picasso.with(holder.mImageView.getContext())
                .load(data)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit()
                .centerCrop()
                .placeholder(R.color.gallery_item_background)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void swapData(@Nullable List<Uri> data) {
        if (!mData.equals(data)) {
            mData.clear();
            if (data != null) {
                mData.addAll(data);
            }
            notifyDataSetChanged();
        }
    }

    public List<Uri> getAllURI(){
        return mData;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mImageView;

        private ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }
    }
}
