package parent.guard.service;

public class SystemService extends BaseService {
  private static final String KEY_SYSTEM_LOGGER = "system_logger";
  private static final String KEY_SYSTEM_PROTECTED = "system_protected";
  
  public boolean setLogEnabled(boolean pEnabled) {
    return setPreference(KEY_SYSTEM_LOGGER, pEnabled);
  }
  
  public boolean getLogEnabled() {
    return getPreferenceAsBoolean(KEY_SYSTEM_LOGGER);
  }
  
  public boolean setProtected() {
    return setPreference(KEY_SYSTEM_PROTECTED, true);
  }
  
  public boolean getProtected() {
    return getPreferenceAsBoolean(KEY_SYSTEM_PROTECTED);
  }
}
