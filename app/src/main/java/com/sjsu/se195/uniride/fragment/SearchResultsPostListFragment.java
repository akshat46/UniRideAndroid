package com.sjsu.se195.uniride.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.sjsu.se195.uniride.MainSubcategoryActivity;
import com.sjsu.se195.uniride.PostDetailActivity;
import com.sjsu.se195.uniride.R;
import com.sjsu.se195.uniride.models.Carpool;
import com.sjsu.se195.uniride.models.DriverOfferPost;
import com.sjsu.se195.uniride.models.Post;
import com.sjsu.se195.uniride.models.RideRequestPost;
import com.sjsu.se195.uniride.models.User;
import com.sjsu.se195.uniride.viewholder.PostListRecyclerAdapter;
import com.sjsu.se195.uniride.viewholder.PostViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResultsPostListFragment extends Fragment {

    private static final String TAG = "PostListFragment";

    private User currentUser;

    // [START define_database_reference]
    protected DatabaseReference mDatabase;
    // [END define_database_reference]

    // private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    protected boolean postType; //true = driverpost ; false = riderequest

    private ArrayList<Post> mPostList;

    public SearchResultsPostListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        postType = getArguments().getBoolean("postType");

        mPostList = getArguments().getParcelableArrayList("searchResults");

        System.out.println("in SearchResultsPostListFragment: just got mPostList = " + mPostList);

        View rootView;

        rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("in Fragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        //postType = savedInstanceState.getBundle("postType");

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Get User Object and Set up FirebaseRecyclerAdapter with the Query:
        setCurrentUserAndLoadPosts();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mAdapter != null) {
//            // mAdapter.cleanup(); // TODO?
//        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // TODO: change to throw error if user doesn't have a default organization:
    public String getUserDefaultOrganizationId() {

        String defaultUserOrganizationId = "";

        if (getCurrentUser() != null) {
            defaultUserOrganizationId = getCurrentUser().defaultOrganizationId;
        }

        if (defaultUserOrganizationId == null || defaultUserOrganizationId.equals("")) {
            System.out.println("ERROR: No default Org Id found for user: " + getCurrentUser());

            // TODO: show load page with no results and prompt user to choose a default organization.

            // WIP ONLY: For testing purposes: set an arbitrary Org Id: // TODO: REMOVE: FOR WIP STATE ONLY.
            defaultUserOrganizationId = "-L47q6ayVu4wPq23hnmm"; // "Marta's Organization"
            System.out.println("WIP ONLY: Setting default Org Id to arbitrary Id: " + defaultUserOrganizationId);
        }

        System.out.println("User's default Org Id = " + defaultUserOrganizationId);

        return defaultUserOrganizationId;
    }

    // public abstract Query getQuery(DatabaseReference databaseReference);


    public void setCurrentUserAndLoadPosts() {

        // System.out.println("Starting to set user....");

        mDatabase.child("users").child(getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Need to get the user object before loading posts because the query to find posts requires user.

                // Get User object and use the values to update the UI
                currentUser = dataSnapshot.getValue(User.class);

                loadPosts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //getUid()
    }

    private void loadPosts() {
        System.out.println("About to load posts.....");

        mAdapter = new PostListRecyclerAdapter(mPostList);

        mRecycler.setAdapter(mAdapter);
    }


    public User getCurrentUser() {
        return currentUser;
    }


}
