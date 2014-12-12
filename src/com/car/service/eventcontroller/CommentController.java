package com.car.service.eventcontroller;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.car.service.R;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.utils.WLog;

/**
 * Created by r.savuschuk on 11/21/2014.
 */
public class CommentController extends EventController {

	public CommentController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout) {
		super(activity, titleLayout, commentLayout);
		setTitle(activity.getResources().getString(R.string.comment));
		setTitleImage(R.drawable.comment_dialogtitle);
	}

	@Override
	public boolean saveEventToDb() {
		engine.dbWriteRequest(DbEngine.Action.COMMENT, null, comment.getText().toString(), new DbEngine.Callback<Long>() {
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
