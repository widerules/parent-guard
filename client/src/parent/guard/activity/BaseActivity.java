package parent.guard.activity;

import parent.guard.AppsPageExample;
import parent.guard.HomePageExample;
import parent.guard.PatternDrawer;
import parent.guard.PatternExample;
import parent.guard.PatternHelper;
import parent.guard.ServiceLocator;
import parent.guard.GuardPreference;
import parent.guard.SettingsPageExample;
import parent.guard.content.PackageDetector;
import parent.guard.service.AssetService;
import parent.guard.service.SystemService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	public static final String KEY_PACKAGE_NAME = "package_name";
	public static final String KEY_ACTIVITY_NAME = "activity_name";
	public static final String KEY_APPLICATION_ICON = "application_icon";
	public static final String KEY_PATTERN_DRAWER = "pattern_drawer";

	protected Context mContext;

	protected abstract void setActivityView();

	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);
		mContext = getApplicationContext();
		setActivityView();
	}

	protected static final int ACTIVITY_PATTERN_DRAWER = 10;
	protected static final int ACTIVITY_PATTERN_HELPER = 11;
	protected static final int ACTIVITY_PATTERN_EXAMPLE = 12;
	protected static final int ACTIVITY_HOME_PAGE_EXAMPLE = 13;
	protected static final int ACTIVITY_APPS_PAGE_EXAMPLE = 14;
	protected static final int ACTIVITY_SETTINGS_PAGE_EXAMPLE = 15;

	protected void startPreferences() {
		startActivity(new Intent(mContext, GuardPreference.class));
	}

	protected void startPatternDrawer(boolean pIsVerified) {
		Intent tIntent = new Intent(mContext, PatternDrawer.class);
		tIntent.putExtra(KEY_PATTERN_DRAWER, pIsVerified);
		startActivityForResult(tIntent, ACTIVITY_PATTERN_DRAWER);
	}

	protected void startPatternHelper() {
		Intent tIntent = new Intent(mContext, PatternHelper.class);
		startActivityForResult(tIntent, ACTIVITY_PATTERN_HELPER);
	}

	protected void startPatternExample() {
		Intent tIntent = new Intent(mContext, PatternExample.class);
		startActivityForResult(tIntent, ACTIVITY_PATTERN_EXAMPLE);
	}

	protected void startHomeExample() {
		Intent homePageExample = new Intent(mContext, HomePageExample.class);
		startActivityForResult(homePageExample, ACTIVITY_HOME_PAGE_EXAMPLE);
	}

	protected void startAppsPageExample() {
		Intent appsPageExample = new Intent(mContext, AppsPageExample.class);
		startActivityForResult(appsPageExample, ACTIVITY_APPS_PAGE_EXAMPLE);
	}

	protected void startSettingsPageExample() {
		Intent settingsPageExample = new Intent(mContext , 	SettingsPageExample.class);
		startActivityForResult(settingsPageExample, ACTIVITY_SETTINGS_PAGE_EXAMPLE );
	}

	protected PackageDetector getPackageDetector() {
		return ServiceLocator.getPackageDetector();
	}

	protected SystemService getSystemService() {
		return ServiceLocator.getSystemService();
	}

	protected AssetService getAssetService() {
		return ServiceLocator.getAssetService();
	}
}
