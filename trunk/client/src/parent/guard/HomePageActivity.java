package parent.guard;

import parent.guard.activity.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import parent.guard.R;

public class HomePageActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!getSystemService().getProtected()) {
			startPatternDrawer(true);
			startHomeExample() ;
			
		}

	}

	@Override
	protected void onActivityResult(int pRequestCode, int pResultCode,
			Intent pIntent) {
		super.onActivityResult(pRequestCode, pResultCode, pIntent);

		if (pRequestCode == ACTIVITY_PATTERN_DRAWER) {
			if (pResultCode == RESULT_CANCELED) {
				finish();
			} else if (pResultCode == RESULT_OK) {
				setActivityView();
			}
		}
	}

	protected void onResume() {

		super.onResume();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton1);

		if (ServiceLocator.getSystemService().getAppToggledOff()) {

			button.setImageResource(R.drawable.logo_unlocked);

		} else {

			button.setImageResource(R.drawable.logo_locked);
		}

	}

	@Override
	protected void setActivityView() {

		setContentView(R.layout.home_main);
		ImageButton button = (ImageButton) findViewById(R.id.imageButton1);
		button.setClickable(true);
		button.setOnClickListener(this);

	}

	public void onClick(View v) {

		ImageButton button = (ImageButton) findViewById(R.id.imageButton1);

		if (ServiceLocator.getSystemService().getAppToggledOff()) {
			GuardApplication.getGuardApplication().startWatcher();
			ServiceLocator.getSystemService().setAppToggledOff(false);
			button.setImageResource(R.drawable.logo_locked);
		} else {
			GuardApplication.getGuardApplication().pauseWatcher();
			ServiceLocator.getSystemService().setAppToggledOff(true);
			button.setImageResource(R.drawable.logo_unlocked);
		}
	}

}
