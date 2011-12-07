package parent.guard.service;

import parent.guard.GuardApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BaseService {
  private static final String KEY_PATTERN = "preference_pattern";
  private static final String KEY_RESTRICTION = "preference_restriction";
  private static final String KEY_APP_TOGGLED_OFF = "app_toggled_off";
  
  protected GuardApplication getGuardApplication() {
    return GuardApplication.getGuardApplication();
  }
  
  private SharedPreferences getSharedPreferences(String pName) {
    return getGuardApplication().getSharedPreferences(pName,
        Context.MODE_PRIVATE);
  }
  
  protected boolean setPatternPreference(String pKey, String pValue) {
    Editor editor = getSharedPreferences(KEY_PATTERN).edit();
    editor.putString(pKey, pValue);
    return editor.commit();
  }
  
  protected boolean setPatternPreference(String pKey, boolean pValue) {
    Editor editor = getSharedPreferences(KEY_PATTERN).edit();
    editor.putBoolean(pKey, pValue);
    return editor.commit();
  }
  
  protected boolean setToggledPreference(String pKey, boolean pValue) {
	Editor editor = getSharedPreferences(KEY_APP_TOGGLED_OFF).edit();
	editor.putBoolean(pKey, pValue);
	return editor.commit();
  }
  
  protected boolean setRestrictionPreference(String pKey, boolean pValue) {
    Editor editor = getSharedPreferences(KEY_RESTRICTION).edit();
    editor.putBoolean(pKey, pValue);
    return editor.commit();
  }
  
  protected String getPatternPreferenceAsString(String pKey) {
    return getSharedPreferences(KEY_PATTERN).getString(pKey, "");
  }
  
  protected boolean getPatternPreferenceAsBoolean(String pKey) {
    return getSharedPreferences(KEY_PATTERN).getBoolean(pKey, false);
  }
  
  protected boolean getToggledPreferenceAsBoolean(String pKey) {
	return getSharedPreferences(KEY_APP_TOGGLED_OFF).getBoolean(pKey, false);
  }
  
  protected boolean getRestrictionPreferenceAsBoolean(String pKey) {
    return getSharedPreferences(KEY_RESTRICTION).getBoolean(pKey, true);
  }
  
  protected void doHttpGet() {
    
  }
  
  protected void doHttpPost() {
    
  }
}
