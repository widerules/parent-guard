package parent.guard;

import parent.guard.activity.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class GuardPreference extends PreferenceActivity
    implements OnPreferenceClickListener {
  private Context mContext;
  private Preference mPattern;
  private CheckBoxPreference mDebugStatus;
  private EditTextPreference mEmailPreference ;
  private EditTextPreference mAgePreference ;
  @Override
  protected void onCreate(Bundle pBundle) {
    super.onCreate(pBundle);
    mContext = getApplicationContext();
    addPreferencesFromResource(R.xml.preference);
    setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
    mPattern = (Preference) findPreference("lockerPattern");
    mPattern.setOnPreferenceClickListener(GuardPreference.this);
    mDebugStatus = (CheckBoxPreference) findPreference("debugStatus");
    mDebugStatus.setOnPreferenceClickListener(GuardPreference.this);
    mEmailPreference = (EditTextPreference) findPreference( "email") ;
    mAgePreference = (EditTextPreference) findPreference( "age") ;
    boolean tIsLogEnabled = ServiceLocator.getSystemService().getLogEnabled();
    mDebugStatus.setChecked(tIsLogEnabled);
  }

  public boolean onPreferenceClick(Preference pPreference) {
    String tPreferenceKey = pPreference.getKey();
    if("lockerPattern".equals(tPreferenceKey)) {
      Intent tIntent = new Intent(mContext, PatternDrawer.class);
      tIntent.putExtra(BaseActivity.KEY_PATTERN_DRAWER, false);
      startActivity(tIntent);
    } else if("debugStatus".equals(tPreferenceKey)) {
      boolean tIsLogEnabled = ServiceLocator.getSystemService().getLogEnabled();
      mDebugStatus.setChecked(!tIsLogEnabled);
      ServiceLocator.getSystemService().setLogEnabled(!tIsLogEnabled);
    } else if( "email".equals( tPreferenceKey)){
    	
    	
    	
    }else if( "age".equals( tPreferenceKey)){
    	
    	
    	
    }
    return true;
  }
}
