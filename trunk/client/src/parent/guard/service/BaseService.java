package parent.guard.service;

import parent.guard.GuardApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BaseService {
  
  protected GuardApplication getGuardApplication() {
    return GuardApplication.getGuardApplication();
  }
  
  private SharedPreferences getSharedPreferences() {
    return getGuardApplication().getSharedPreferences("pattern",
        Context.MODE_PRIVATE);
  }
  
  protected boolean setPreference(String pKey, String pValue) {
    Editor editor = getSharedPreferences().edit();
    editor.putString(pKey, pValue);
    return editor.commit();
  }
  
  protected boolean setPreference(String pKey, boolean pValue) {
    Editor editor = getSharedPreferences().edit();
    editor.putBoolean(pKey, pValue);
    return editor.commit();
  }
  
  protected String getPreferenceAsString(String pKey) {
    return getSharedPreferences().getString(pKey, "");
  }
  
  protected boolean getPreferenceAsBoolean(String pKey) {
    return getSharedPreferences().getBoolean(pKey, false);
  }
  
  protected void doHttpGet() {
    
  }
  
  protected void doHttpPost() {
    
  }
}
