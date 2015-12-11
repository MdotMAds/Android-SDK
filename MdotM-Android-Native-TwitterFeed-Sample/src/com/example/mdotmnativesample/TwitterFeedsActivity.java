package com.example.mdotmnativesample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mdotm.android.listener.MdotMAdEventListener;
import com.mdotm.android.model.MdotMAdRequest;
import com.mdotm.android.nativeads.MdotMNativeAd;
import com.mdotm.android.nativeads.MdotMNativeView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class TwitterFeedsActivity extends Activity implements MdotMAdEventListener {
	private RecyclerView feedList;
	private MyRecyclerViewAdapter myRecyclerViewAdapter;
	private MdotMAdRequest request;
	private ArrayList<MdotMNativeAd> nativeAds;
	private SharedPreference sharedpreferences;
	private boolean isTwitterFeedReady = false;
	private boolean isNativeAdReady = false;
	private TwitterResponseModel twitterFeeds;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_feeds);
		activity = this;
		feedList = (RecyclerView) findViewById(R.id.recycler_view);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		feedList.setLayoutManager(llm);
		feedList.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
		request = new MdotMAdRequest();
		request.setAppKey("test:54");
		MdotMNativeView nativeView = new MdotMNativeView(TwitterFeedsActivity.this);
		nativeView.loadNativeAd(TwitterFeedsActivity.this, request);
		sharedpreferences = new SharedPreference();
		if (!sharedpreferences.getFeedResponse(getApplicationContext())) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					InputStream in = null;
					int timeoutSocket = 30000;
					int timeoutConnection = 10000;
					HttpURLConnection urlConnection = null;
					URL url = null;
					try {
						url = new URL("https://secure.mdotm.com/ads/samples/test-twitter.json");
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						urlConnection = (HttpURLConnection) url.openConnection();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					urlConnection.setConnectTimeout(timeoutConnection);
					urlConnection.setReadTimeout(timeoutSocket);
					int statusCode = 0;
					try {
						statusCode = urlConnection.getResponseCode();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (statusCode == 200 || statusCode == 201) {

						try {
							in = new BufferedInputStream(urlConnection.getInputStream());

							sharedpreferences.saveFeedResponse(getApplicationContext(), true);
							Gson gson = new Gson();

							Reader reader = new InputStreamReader(in);
							Log.d("this", "response is here" + reader.toString());
							final TwitterResponseModel response = gson.fromJson(reader, TwitterResponseModel.class);
							twitterFeeds = response;
							InternalStorage.writeObject(getApplicationContext(), "temp", response);
							Log.d("this", "response is here" + in.toString());
							runOnUiThread(new Runnable() {
								public void run() {
									if (isNativeAdReady) {
										myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(),
												response, nativeAds, activity);
										feedList.setAdapter(myRecyclerViewAdapter);
										Log.d("this", "set Recycle Adapter from network");
									} else {
										isTwitterFeedReady = true;
									}

								}
							});

						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}

			});
			t.start();
		} // if close
		else {
			try {
				final TwitterResponseModel response = (TwitterResponseModel) InternalStorage
						.readObject(getApplicationContext(), "temp");
				twitterFeeds = response;
				if (isNativeAdReady) {
					myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), response, nativeAds,
							this);
					feedList.setAdapter(myRecyclerViewAdapter);
					Log.d("this", "set Recycle Adapter from cache");
				} else {
					isTwitterFeedReady = true;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onFailedToReceiveInterstitialAd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInterstitialAdClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDismissScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInterstitialDismiss() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaveApplicationFromInterstitial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveInterstitialAd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void willShowInterstitial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void didShowInterstitial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRewardedVideoComplete(boolean skipped, String rewards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveNativeAd(ArrayList<MdotMNativeAd> nativeAd) {
		Log.d("this", "native ad size" + nativeAd.size());
		nativeAds = nativeAd;
		if (isTwitterFeedReady) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), twitterFeeds, nativeAds,
							activity);
					feedList.setAdapter(myRecyclerViewAdapter);
				}
			});

		} else
			isNativeAdReady = true;
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveNativeAd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNativeAdClick() {
		// TODO Auto-generated method stub
	}
}