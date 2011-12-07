package parent.guard.service;

public class SystemService extends BaseService {
  private static final String KEY_SYSTEM_LOGGER = "system_logger";
  private static final String KEY_SYSTEM_PROTECTED = "system_protected";
  private static final String KEY_APP_TOGGLED_OFF = "app_toggled_off";
  
  public boolean setLogEnabled(boolean pEnabled) {
    return setPatternPreference(KEY_SYSTEM_LOGGER, pEnabled);
  }
  
  public boolean getLogEnabled() {
    return getPatternPreferenceAsBoolean(KEY_SYSTEM_LOGGER);
  }
  
  public boolean setProtected() {
    return setPatternPreference(KEY_SYSTEM_PROTECTED, true);
  }
  
  public boolean getProtected() {
    return getPatternPreferenceAsBoolean(KEY_SYSTEM_PROTECTED);
  }
  
  public boolean setAppToggledOff(boolean appOff) {
	return setToggledPreference(KEY_APP_TOGGLED_OFF, appOff);
  }
  public boolean getAppToggledOff() {
    return getToggledPreferenceAsBoolean(KEY_APP_TOGGLED_OFF);
  }
}
