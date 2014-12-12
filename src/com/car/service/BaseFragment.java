package com.car.service;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by r.savuschuk on 9/15/2014.
 */
public class BaseFragment extends Fragment {
	protected Statistic activity;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=(Statistic)getActivity();
	}
}
