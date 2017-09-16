package com.dan190.covfefe.Listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dan on 02/06/2017.
 */

public class RecyclerViewOnTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private RecyclerViewClickListener mClickListener;

    public RecyclerViewOnTouchListener(Context context, final RecyclerView recyclerView, final RecyclerViewClickListener recyclerViewClickListener){
        mClickListener = recyclerViewClickListener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && mClickListener != null){
                    mClickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if(child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)){
            mClickListener.onClick(child, rv.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
