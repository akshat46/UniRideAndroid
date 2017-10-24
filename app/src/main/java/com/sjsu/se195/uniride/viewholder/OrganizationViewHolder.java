package com.sjsu.se195.uniride.viewholder;

/**
 * Created by timhdavis on 10/8/17.
 */


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjsu.se195.uniride.R;
//import com.sjsu.se195.uniride.models.Post; //TODO: remove
import com.sjsu.se195.uniride.models.Organization;

public class OrganizationViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView; // TODO
    // TODO: add more

    public OrganizationViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.organization_name); // TODO
//        authorView = (TextView) itemView.findViewById(R.id.post_author);// TODO
//        starView = (ImageView) itemView.findViewById(R.id.star);// TODO
//        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);// TODO
//        bodyView = (TextView) itemView.findViewById(R.id.post_body);// TODO
    }

    public void bindToPost(Organization organization, View.OnClickListener starClickListener) {
        nameView.setText(organization.name);// TODO
//        authorView.setText(organization.author);// TODO
//        numStarsView.setText(String.valueOf(organization.starCount));// TODO: don't need.
//        bodyView.setText(organization.body);// TODO

//        starView.setOnClickListener(starClickListener);// TODO: don't need.
    }
}