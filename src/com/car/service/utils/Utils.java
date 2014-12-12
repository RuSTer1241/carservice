package com.car.service.utils;

import android.content.Context;
import android.content.Intent;
import com.car.service.R;

/**
 * Created by r.savuschuk on 11/13/2014.
 */
public class Utils {

	public static void share(Context context){
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_for_sharing));
		shareIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getText(R.string.googleplay_url));
		shareIntent.setType("text/plain");
		context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.share)));
	}
}
