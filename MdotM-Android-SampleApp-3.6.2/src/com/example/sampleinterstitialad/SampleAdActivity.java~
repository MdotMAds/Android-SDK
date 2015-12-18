package com.example.sampleinterstitialad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import com.mdotm.android.listener.MdotMAdEventListener;
import com.mdotm.android.model.MdotMAdRequest;
import com.mdotm.android.view.MdotMInterstitial;

public class SampleAdActivity extends Activity implements MdotMAdEventListener {
	private boolean isInitialized = false;
	private Button initButton;
	private Button reqAd;
	private Button showAd;
	private TextView statusText;
	private MdotMAdRequest request;
	private MdotMInterstitial interstitial;
	private CheckBox chkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButton = (Button) findViewById(R.id.initButton);
		initButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InitAction();
			}
		});

		// cache
		reqAd = (Button) findViewById(R.id.reqButton);
		reqAd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isInitialized)
					CacheAction();

			}
		});

		// show
		showAd = (Button) findViewById(R.id.showButton);
		showAd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isInitialized)
					DisplayAction();
				updateTextInUI("");
			}
		});

		statusText = (TextView) findViewById(R.id.statusText);
		chkBox = (CheckBox) findViewById(R.id.checkBoxIsRewarded);
		chkBox.setVisibility(View.GONE);
	}

	public void InitAction() {
		chkBox.setVisibility(View.VISIBLE);
		request = new MdotMAdRequest();
		request.setAppKey("nofill");

		request.setTestMode("0");

		interstitial = new MdotMInterstitial(this);
		isInitialized = true;
		updateTextInUI("Initialize Ad");

	}

	// sending request for ad is here.
	public void CacheAction() {

		updateTextInUI("Receiving Ad...");

		if (chkBox.isChecked()) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Rewards");
			alert.setMessage("Enter reward item");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value;
							value = input.getText().toString();
							request.setTestMode("video");
							interstitial.loadRewardedVideo(
									SampleAdActivity.this, request, value);

						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});

			alert.show();

		} else {
			request.setTestMode("0");
			chkBox.setVisibility(View.GONE);
			interstitial.loadInterstitial(this, request);
		}

	}

	public void DisplayAction() {
		chkBox.setVisibility(View.GONE);
		if (interstitial.isInterstitialReady) {
			updateTextInUI("Show Ad");
			interstitial.showInterstitial(this);
		} else {
			updateTextInUI("No ad available!");
		}
	}

	private void updateTextInUI(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (statusText != null)
					statusText.setText(text);
			}
		});
	}

	// Call backs from MdotM
	public void onReceiveInterstitialAd() {
		updateTextInUI("Ad cached!");
		// Listener on successful receive of ads
	}

	public void onFailedToReceiveInterstitialAd() {
		updateTextInUI("Fail to cache!");
		// Listener on successful receive of ads
	}

	public void onInterstitialAdClick() {
		// Listener on ad click for Google Play redirection
	}

	public void onInterstitialDismiss() {
		// Listener on closing ads
	}

	public void onLeaveApplicationFromInterstitial() {
		// Listener close
	}

	public void willShowInterstitial() {
	}

	public void didShowInterstitial() {
		// Listener on already shown ads
	}

	@Override
	public void onDismissScreen() {
		// Listener on closing ads

	}

	@Override
	public void onRewardedVideoComplete(boolean skipped, String rewards) {

		CharSequence text;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SampleAdActivity.this);

		alertDialogBuilder.setTitle("Rewards");

		if (!skipped)
			text = "You got " + rewards + " as reward";

		else
			text = "You lost " + rewards + " for skipping the ad";
		alertDialogBuilder.setMessage(text).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		alertDialogBuilder.show();

	}

	

	@Override
	public void onFailedToReceiveNativeAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNativeAdClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveNativeAd(ArrayList<com.mdotm.android.nativeads.MdotMNativeAd> arg0) {
		// TODO Auto-generated method stub
		
	}

}
