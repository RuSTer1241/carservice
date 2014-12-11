package car.simple.service.eventcontroller;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.baby.observer.R;
import car.simple.service.database.DbEngine;
import car.simple.service.database.DbError;
import car.simple.service.utils.WLog;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class CrapController extends EventController {

	public CrapController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout) {
		super(activity, titleLayout, commentLayout);
		setTitle(activity.getResources().getString(R.string.crap));
		setTitleImage(R.drawable.crap_dialogtitle);
	}

	@Override
	public boolean saveEventToDb() {
		engine.dbWriteRequest(DbEngine.Action.KA, null, comment.getText().toString(), new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data) {
					WLog.e("DbEngine", " OnSuccess");
				}

				@Override
				public void onFail(final DbError error) {
					WLog.e("DbEngine", " OnFail");
					Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
				}
			});
		return true;
	}
}
