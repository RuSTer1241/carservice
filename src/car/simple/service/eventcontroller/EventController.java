package car.simple.service.eventcontroller;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.database.DbEngine;
import car.simple.service.model.PreferenceEditor;

/**
 * Created by r.savuschuk on 11/18/2014.
 */
public class EventController  {
	Context activity;
	protected DbEngine engine;
	protected EditText comment;
	protected TextView title;
	ImageView titleImage;
	private boolean scrolling = false;
	PreferenceEditor prefEditor;

	public EventController(Activity activity,RelativeLayout titleLayout, LinearLayout commentLayout) {
		this.activity=activity;
		engine = CarServiceApplication.getDbEngine();
		prefEditor= CarServiceApplication.getPrefEditor();
		this.comment = (EditText) commentLayout.findViewById(R.id.comment_edit_text);
		this.title = (TextView) titleLayout.findViewById(R.id.title);
		titleImage = (ImageView) titleLayout.findViewById(R.id.dialog_icon);
	}
	public  boolean saveEventToDb(){
		return false;
	}

	public boolean isScrolling() {
		return scrolling;
	}

	public void setScrolling(final boolean scrolling) {
		this.scrolling = scrolling;
	}
	public void setTitleImage(int titleImage) {
		this.titleImage.setImageResource(titleImage);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

}
