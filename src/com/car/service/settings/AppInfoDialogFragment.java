package com.car.service.settings;

import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.car.service.R;

/**
 * Created by r.savuschuk on 9/5/2014.
 */
public class AppInfoDialogFragment  extends DialogFragment {
     TextView version;
	String versionName;


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			versionName =  getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}



	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.appinfo, container, false);
		getDialog().setTitle(R.string.about_app);
		version=(TextView)v.findViewById(R.id.version);
		version.append(" "+versionName);
		return v;
	}


}

