package car.simple.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.graphmode.GraphFragment;
import car.simple.service.listmode.ListItemFragment;
import car.simple.service.model.PreferenceEditor;
import car.simple.service.settings.AppInfoDialogFragment;
import car.simple.service.settings.MeasureSettingsFragmentDialog;
import car.simple.service.utils.Utils;

/**
 * This class manage actionbar for different activities
 */
public class ActionBarController implements PopupMenu.OnMenuItemClickListener {
	PreferenceEditor prefEditor;
	ImageButton rightButton;
	ImageButton leftButton;
	ImageButton preferButton;
	ImageButton shareButton;
	ImageButton chartButton;
	Activity activity;
	DialogFragment appInfoFragment;
	DialogFragment settingsFragment;
	View v;

	public ActionBarController(Activity activity, View v) {
		this.activity = activity;
		this.v = v;


		prefEditor = CarServiceApplication.getPrefEditor();
	}

	public void initActionViewMyActivity() {
		rightButton = (ImageButton) v.findViewById(R.id.right_button);
		rightButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu_forward));
		leftButton = (ImageButton) v.findViewById(R.id.left_button);
		leftButton.setImageDrawable(null);
		leftButton.setBackgroundResource(R.drawable.icon);
		preferButton = (ImageButton) v.findViewById(R.id.preference);
		shareButton = (ImageButton) v.findViewById(R.id.share);

		commonInit();
		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(activity, Statistic.class);
				activity.startActivity(intent);
			}
		});
	}

	ListItemFragment listFrag;
	GraphFragment graphFrag;

	public void initActionViewStatistic(ListItemFragment listFr, GraphFragment graphFr) {
		this.graphFrag = graphFr;
		this.listFrag = listFr;
		rightButton = (ImageButton) v.findViewById(R.id.right_button);
		rightButton.setImageDrawable(null);
		rightButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.icon));

		leftButton = (ImageButton) v.findViewById(R.id.left_button);
		leftButton.setBackgroundDrawable(null);
		leftButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu_back));

		preferButton = (ImageButton) v.findViewById(R.id.preference);
		chartButton = (ImageButton) v.findViewById(R.id.action_chart);
		shareButton = (ImageButton) v.findViewById(R.id.share);

		commonInit();

		chartButton.setVisibility(View.GONE);
		if (prefEditor.getCurFragment()) {
			chartButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.chart_line));
		} else {
			chartButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.list_num));
		}
		chartButton.setVisibility(View.VISIBLE);

		leftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(activity, MyActivity.class);
				activity.startActivity(intent);
			}
		});
		chartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentTransaction fTrans = activity.getFragmentManager().beginTransaction();
				if (prefEditor.getCurFragment()) {
					chartButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.list_num));
					fTrans.replace(android.R.id.tabcontent, graphFrag);
					prefEditor.saveCurFragment(false);
				} else {
					chartButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.chart_line));
					fTrans.replace(android.R.id.tabcontent, listFrag);
					prefEditor.saveCurFragment(true);
				}
				fTrans.commit();
			}
		});
	}

	private void commonInit() {
		shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Utils.share(activity);
			}
		});

		preferButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showPopup(view);
			}
		});
	}


	@Override
	public boolean onMenuItemClick(final MenuItem menuItem) {
		boolean ret = false;
		//Handle item selection
		switch (menuItem.getItemId()) {
			case R.id.about_app:
				FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
				// Create and show the dialog.
				appInfoFragment = new AppInfoDialogFragment();
				appInfoFragment.show(ft, "dialog");
				ret = true;
				break;
			case R.id.measure_settings:
				FragmentTransaction ft1 = activity.getFragmentManager().beginTransaction();
				// Create and show the dialog.
				settingsFragment = new MeasureSettingsFragmentDialog();
				settingsFragment.show(ft1, "dialog");
				ret = true;
				break;
			case R.id.exit:
				new AlertDialog.Builder(activity).setTitle(activity.getResources().getString(R.string.app_name))
					.setMessage(activity.getResources().getString(R.string.exit_msg))
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							activity.moveTaskToBack(true);
							activity.finish();
						}
					}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				}).setIcon(android.R.drawable.ic_dialog_alert).show();
				ret = true;
				break;

			default:
				ret = false;
				break;
		}
		return ret;
	}

	private void showPopup(View v) {
		PopupMenu popup = new PopupMenu(activity, v);
		popup.setOnMenuItemClickListener(this);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.activity_actions, popup.getMenu());
		popup.show();
	}
}
