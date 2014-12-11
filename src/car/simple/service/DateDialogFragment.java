package car.simple.service;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import com.baby.observer.R;

import java.util.Date;

public class DateDialogFragment extends DialogFragment {


    private Handler mHandler;
    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    private CalendarView calendarView;
	Button done_but;
	private int id;


    public DateDialogFragment(Handler handler,int viewId) {
        mHandler = handler;
	    id=viewId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = android.R.style.Theme_Holo;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.data_dialog, container, false);
        calendarView = (CalendarView) v.findViewById(R.id.calendar);
	    done_but=(Button) v.findViewById(R.id.calendar_done_but);
	    done_but.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(final View v) {
			    String dateString= DateFormat.format("dd-MM-yyyy", new Date(calendarView.getDate())).toString();
			    Bundle bundle = new Bundle();
			    bundle.putString("currentDate",dateString);
			    bundle.putLong("currentDateInt",calendarView.getDate());
			    Message msg=new Message();
			    if(id==R.id.from_date_view)
				    msg.what=10;
			    if(id==R.id.to_date_view)
				    msg.what=20;

			    msg.setData(bundle);
			    mHandler.sendMessage(msg);

		    }
	    });


        return v;
    }


}
