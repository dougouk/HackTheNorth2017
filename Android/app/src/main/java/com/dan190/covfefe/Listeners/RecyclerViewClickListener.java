package com.dan190.covfefe.Listeners;

import android.view.View;

/**
 * Created by Dan on 02/06/2017.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
