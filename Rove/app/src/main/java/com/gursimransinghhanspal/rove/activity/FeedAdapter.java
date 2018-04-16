package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.backend.GetImageRequestHandler;
import com.gursimransinghhanspal.rove.backend.GetRequestHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Himaneesh on 09-03-2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>
{
    private Context mCtx;
    private List<com.gursimransinghhanspal.rove.Trip> tripList;

    public FeedAdapter(Context mCtx,List<com.gursimransinghhanspal.rove.Trip> tripList)
    {
        this.mCtx=mCtx;
        this.tripList=tripList;
    }


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item,null);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, int position)
    {
        com.gursimransinghhanspal.rove.Trip list = tripList.get(position);

        // Get cover image from the server.
        GetImageRequestHandler requestHandler = new GetImageRequestHandler();
        Bitmap coverImg = requestHandler.getImage("/images/cover/" + list.getCoverPhotoName());
        // Get user details from the server.
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userId", list.getUserId()));
        GetRequestHandler userProfileGetRequestHandler = new GetRequestHandler();
        JSONObject jsonResponse = userProfileGetRequestHandler.getJSON("/profile/", params);

        if (jsonResponse != null) {
            try {
                if (jsonResponse.getBoolean("res")) {
                    JSONObject profile = jsonResponse.getJSONObject("profile");
                    String firstName = profile.getString("firstname");
                    String lastName = profile.getString("lastname");
                    holder.textViewPerson.setText(firstName + " " + lastName);
                    String profilePictureName = profile.getString("profile_picture_name");
                    requestHandler = new GetImageRequestHandler();
                    Bitmap profileImg = requestHandler.getImage("/images/profile/" + profilePictureName);
                    holder.userProfilePicture.setImageBitmap(profileImg);
                } else {
                    holder.textViewPerson.setText("John Doe");
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        holder.textViewDesc.setText(list.getTitle());
        holder.imageView.setImageBitmap(coverImg);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Date diaryTime = list.getUpdatedAt();
        DecimalFormat dFormat= new DecimalFormat("00");
        String diaryTimeMonth = new SimpleDateFormat("MMM").format(diaryTime);
        holder.diaryTime.setText(diaryTimeMonth + " " +
                String.valueOf(dFormat.format(diaryTime.getDate())) + " at " +
                String.valueOf(dFormat.format(diaryTime.getHours())) + ":" +
                String.valueOf(dFormat.format(diaryTime.getMinutes())));
        if(Search.bookMarkPresent == 0)
        {
            //holder.imageButton.setClickable(false);
        }
        else {
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),holder.textViewDesc.getText(),Toast.LENGTH_SHORT).show();


                    if (holder.imageButton.getTag() != null && holder.imageButton.getTag().equals(R.drawable.ic_bookmark)) {
                        holder.imageButton.setImageResource(R.drawable.ic_bookmark_border);
                        holder.imageButton.setColorFilter(Color.parseColor("#00838f"));
                        Iterator<BookmarkedPost> iterator = BookMark.bookmarkhimulist.iterator();
                        while (iterator.hasNext()) {
                            BookmarkedPost bookmarkedPost = iterator.next();
                            if (bookmarkedPost.getShortdesc().equals(holder.textViewDesc.getText().toString())) {
                                iterator.remove();
                            }
                        }
                        holder.imageButton.setTag(R.drawable.ic_bookmark_border);
                    } else {
                        holder.imageButton.setImageResource(R.drawable.ic_bookmark);
                        holder.imageButton.setColorFilter(Color.parseColor("#00838f"));
//                    for(int i=0;i<tripList.size();i++) {
//                        if (holder.textViewDesc.getText().toString().equals(ActivityHomeFeed.tripList.get(i).getShortdesc())) {
//                            BookmarkedPost bookmarkedPost = new BookmarkedPost(ActivityHomeFeed.tripList.get(i).getUsernmae(), ActivityHomeFeed.tripList.get(i).getShortdesc(), ActivityHomeFeed.tripList.get(i).getImage());
//                            BookMark.bookmarkhimulist.add(bookmarkedPost);
//                        }
//                    }
                        holder.imageButton.setTag(R.drawable.ic_bookmark);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewPerson, textViewDesc, diaryTime;
        ImageView imageView;
        ImageButton imageButton;
        CircleImageView userProfilePicture;
        public FeedViewHolder(View itemView) {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.name);
            textViewDesc = itemView.findViewById(R.id.about);
            imageView = itemView.findViewById(R.id.description);
            imageButton = itemView.findViewById(R.id.pinbookmark);
            if(Search.bookMarkPresent == 0)
            {
                imageButton.setClickable(false);
                imageButton.setVisibility(itemView.INVISIBLE);
                //Toast.makeText(this,"Not logged in",Toast.LENGTH_SHORT).show();
            }
            else if(Search.bookMarkPresent ==1)
            {
                imageButton.setClickable(true);
                imageButton.setVisibility(itemView.VISIBLE);
                //Toast.makeText(this,"Not logged in",Toast.LENGTH_SHORT).show();
            }
            userProfilePicture = itemView.findViewById(R.id.profile);
            diaryTime = itemView.findViewById(R.id.time);
        }
    }
}
