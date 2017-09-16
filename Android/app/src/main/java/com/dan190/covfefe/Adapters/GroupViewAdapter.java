package com.dan190.covfefe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan190.covfefe.Group.GroupViewFragment;
import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.R;

import java.util.List;

/**
 * Created by Dan on 16/09/2017.
 */

public class GroupViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = GroupViewAdapter.class.getSimpleName();
    private static final int GROUP_VIEW_TYPE = 1;

    private Context context;
    private String groupName;
    private long lastOrder;
    private List<User> members;

    public GroupViewAdapter(Context context, String groupName, long lastOrder, List<User> members){
        this.context = context;
        this.groupName = groupName;
        this.lastOrder = lastOrder;
        this.members = members;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView groupName;
        private TextView groupPopulation;
        private TextView groupLastOrder;
        private ImageView groupFavorite;
        private RecyclerView groupMemberList;
        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.groupName);
            groupPopulation = (TextView) itemView.findViewById(R.id.groupPopulation);
            groupLastOrder = (TextView) itemView.findViewById(R.id.groupLastOrder);
            groupFavorite = (ImageView) itemView.findViewById(R.id.groupFavoriteButton);
            groupMemberList = (RecyclerView) itemView.findViewById(R.id.groupMemberRecyclerView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case GROUP_VIEW_TYPE:
                View groupView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_group_view, parent, false);
                return new GroupViewHolder(groupView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return GROUP_VIEW_TYPE;
    }
}
