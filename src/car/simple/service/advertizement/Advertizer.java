package car.simple.service.advertizement;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.baby.observer.R;
import car.simple.service.utils.WLog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by r.savuschuk on 9/29/2014.
 */
public class Advertizer {
	private static volatile Advertizer theOnlyInstance;
	Context context;
	AdvertiseThread timer;
	Handler eventHandler;
	private InterstitialAd interstitial;

	private Advertizer(Context context) {
		this.context = context;
		interstitial = new InterstitialAd(context);
		interstitial.setAdUnitId(context.getResources().getString(R.string.banner_ad_unit_id_release));
		interstitial.setAdListener(new InterstitialAdListener());
		// Create ad request.
		final AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		adRequestBuilder.addTestDevice(context.getResources().getString(R.string.interstitial_ad_unit_id_test_device1));
        adRequestBuilder.addTestDevice(context.getResources().getString(R.string.interstitial_ad_unit_id_test_device2));
		eventHandler = new Handler() {
			@Override
			public void handleMessage(final Message msg) {
				super.handleMessage(msg);
				if (msg.what == 10) {
					interstitial.loadAd(adRequestBuilder.build());
				}
			}
		};
		timer = new AdvertiseThread(eventHandler);
		timer.start();
	}

	/**
	 * the realization of Singleton getInstance method.
	 * @return the ONLY instance of singleton
	 */
	public static Advertizer getInstance(Context context) {
		if (theOnlyInstance == null) {
			synchronized (Advertizer.class) {
				if (theOnlyInstance == null) {
					theOnlyInstance = new Advertizer(context.getApplicationContext());
				}
			}
		}
		return theOnlyInstance;
	}


	public void displayInterstitial() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		} else {
			WLog.d("Intersitial", "Interstitial ad was not ready to be shown.");
		}
	}

	/**
	 * An ad listener that logs and toasts ad events, and enables/disables the "Show Interstitial"
	 * button when appropriate.
	 */
	private class InterstitialAdListener extends AdListener {
		private static final String LOG_TAG = "Interstitial";

		public InterstitialAdListener() {
		}

		/** Called when an ad is loaded. */
		@Override
		public void onAdLoaded() {
			Log.d(LOG_TAG, "onAdLoaded");
			displayInterstitial();
		}

		/** Called when an ad failed to load. */
		@Override
		public void onAdFailedToLoad(int errorCode) {
			String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
			Log.d(LOG_TAG, message);
		}

		/**
		 * Called when an Activity is created in front of the app (e.g. an interstitial is shown, or an
		 * ad is clicked and launches a new Activity).
		 */
		@Override
		public void onAdOpened() {
			Log.d(LOG_TAG, "onAdOpened");
			//Toast.makeText(InterstitialBanner.this, "onAdOpened", Toast.LENGTH_SHORT).show();
		}

		/** Called when an ad is closed and about to return to the application. */
		@Override
		public void onAdClosed() {
			Log.d(LOG_TAG, "onAdClosed");
			//Toast.makeText(InterstitialBanner.this, "onAdClosed", Toast.LENGTH_SHORT).show();
		}

		/**
		 * Called when an ad is clicked and going to start a new Activity that will leave the
		 * application (e.g. breaking out to the Browser or Maps application).
		 */
		@Override
		public void onAdLeftApplication() {
			Log.d(LOG_TAG, "onAdLeftApplication");
			//Toast.makeText(InterstitialBanner.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();
		}

		/** Gets a string error reason from an error code. */
		private String getErrorReason(int errorCode) {
			switch (errorCode) {
				case AdRequest.ERROR_CODE_INTERNAL_ERROR:
					return "Internal error";
				case AdRequest.ERROR_CODE_INVALID_REQUEST:
					return "Invalid request";
				case AdRequest.ERROR_CODE_NETWORK_ERROR:
					return "Network Error";
				case AdRequest.ERROR_CODE_NO_FILL:
					return "No fill";
				default:
					return "Unknown error";
			}
		}
	}

	/*
	    true- pause, false- resume
	*/
	public synchronized boolean pauseTimer(boolean pause) {
		if (timer != null) {
			if (pause) {
				timer.pauseTimer();
			} else {
				timer.resumeTimer();
			}
			return true;
		}
		return false;
	}
}
