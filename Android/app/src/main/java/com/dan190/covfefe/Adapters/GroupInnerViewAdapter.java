package com.dan190.covfefe.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Dan on 16/09/2017.
 */

public class GroupInnerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = GroupInnerViewAdapter.class.getSimpleName();

    private List<User> members;
    private Context context;

    public GroupInnerViewAdapter(Context context, List<User> members){
        this.context = context;
        this.members = members;
    }

    class GroupInnerViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public GroupInnerViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.view_inner_recycler, parent, false);
        return new GroupInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "on bind inner holder");
        GroupInnerViewHolder viewHolder = (GroupInnerViewHolder) holder;

        /*URL url = null;
        try {
            url = new URL(members.get(position).getPhotoUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            viewHolder.imageView.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Bitmap bmp = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                R.drawable.userphoto);
        viewHolder.imageView.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
