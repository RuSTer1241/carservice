package car.simple.service.alarms;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import car.simple.service.model.CarServiceApplication;
import car.simple.service.utils.WLog;

/**
 * for Alarm handling (context in this class is specific, got from Alarm receiver with pending intent)
 * Created by r.savuschuk on 10/6/2014.
 */
public class AlarmProcessor {
	private final String TAG = this.getClass().getSimpleName();
	Context context;
	private MediaPlayer mPlayer;

	public AlarmProcessor(final Context context) {
		this.context = context;
		mPlayer = CarServiceApplication.getPlayer();
		mPlayer.setLooping(true);
		WLog.d(TAG, "Player created" + mPlayer.toString());
	}

	/**
	 * todo change AlertDialogActivity with usual dialog. AlertDialog shouldn't close previous dialog
	 */
	public void handleAlarm() {

		//Toast.makeText(NewBornApplication.getAppContext(), "Eat Alarm event ", Toast.LENGTH_SHORT).show();
		WLog.d(TAG, "playback start");
		mPlayer.seekTo(0);
		mPlayer.start();
		Intent intent = new Intent(context.getApplicationContext(), AlertDialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
