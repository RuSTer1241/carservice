package car.simple.service;

import android.content.Context;
import android.content.Intent;
import car.simple.service.utils.WLog;

/**
 * Created by r.savuschuk on 9/29/2014.
 */
public class IntentSender {
	private static volatile IntentSender theOnlyInstance;
	protected final String TAG = "IntentSender";
	private final Context context;

	private IntentSender(Context context) {
		this.context = context;
	}

	/**
	 * the realization of Singleton getInstance method.
	 * @return the ONLY instance of singleton
	 */
	public static IntentSender getInstance(Context context) {
		if (theOnlyInstance == null) {
			synchronized (IntentSender.class) {
				if (theOnlyInstance == null) {
					theOnlyInstance = new IntentSender(context.getApplicationContext());
				}
			}
		}
		return theOnlyInstance;
	}


	public void startInterstitialActivity() {
		WLog.d(TAG, "startMyActivity");
		//Intent intent = new Intent(context, InterstitialBanner.class);
		//startActivity(intent);
	}

	private void startActivity(Intent intent) {
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
