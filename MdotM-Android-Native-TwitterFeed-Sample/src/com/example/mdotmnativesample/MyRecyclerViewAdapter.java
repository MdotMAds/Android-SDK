package com.example.mdotmnativesample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;
import com.bumptech.glide.Glide;
import com.mdotm.android.nativeads.MdotMNativeAd;
import com.mdotm.android.utils.MdotMLogger;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<FeedListRowHolder> implements OnClickListener{
    private Context mContext;
    private TwitterResponseModel response;
    ArrayList<MdotMNativeAd> nativeAds;
    int pos=0;
    MdotMNativeAd mdotMNativeAd=new MdotMNativeAd();
    private Activity activity;

    
	public MyRecyclerViewAdapter(Context context,TwitterResponseModel response,ArrayList<MdotMNativeAd> nativeAds,Activity activity) {
		this.mContext=context;
		this.response=response;
		this.nativeAds=nativeAds;
		this.activity=activity;
		// TODO Auto-generated constructor stub
	}

	@Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup,false);
        FeedListRowHolder mh = new FeedListRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {
    	Random randomGenerator = new Random();
    	final int randomInt = randomGenerator.nextInt(response.statuses.size()-1);
        if(i!=0&&i%3==0&& nativeAds!=null)
        {
        	int randomnativead = randomGenerator.nextInt(nativeAds.size());
        	feedListRowHolder.nameView.setText(nativeAds.get(randomnativead).getTitle());
        	feedListRowHolder.descriptionView.setText(nativeAds.get(randomnativead).getBody());
        	feedListRowHolder.adFlagView.setVisibility(View.VISIBLE);
        	nativeAds.get(randomnativead).displayImage(nativeAds.get(randomnativead).getIcon(),feedListRowHolder.profileIcon, mContext);
        	mdotMNativeAd.regesterView(feedListRowHolder.linearLayout, nativeAds.get(randomnativead),activity);
        }
     else{
        feedListRowHolder.adFlagView.setVisibility(View.INVISIBLE);
    	feedListRowHolder.nameView.setText(response.statuses.get(randomInt).user.name);
    	feedListRowHolder.descriptionView.setText(response.statuses.get(randomInt).user.description);
    	mdotMNativeAd.unRegesterView(feedListRowHolder.linearLayout);
		//profileImageView.setim
		Glide.with(mContext)
	    .load(response.statuses.get(randomInt).user.profile_image_url_https)
	    .into(feedListRowHolder.profileIcon);
		if(null!=response.statuses.get(randomInt).user.url){
		feedListRowHolder.linearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW,
						Uri.parse(response.statuses.get(randomInt).user.url));
				
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				try {

					activity.startActivity(i);

				} catch (Exception e) {

					MdotMLogger.i(this,
							"Could not open browser on ad click to "
									+ e);
				}
			}
		});
		}
        }
		Log.d("this", "set Recycle Adapter"+randomInt);
    }

    @Override
    public int getItemCount() {
        return 500;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

