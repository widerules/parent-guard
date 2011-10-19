package parent.guard.activity;

import parent.guard.ServiceLocator;
import parent.guard.content.PackageDetector;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
  public static final String KEY_PACKAGE_NAME = "package_name";
  public static final String KEY_ACTIVITY_NAME = "activity_name";
  public static final String KEY_APPLICATION_ICON = "application_icon";
  
  protected Context mContext;

  @Override
  protected void onCreate(Bundle pBundle) {
    super.onCreate(pBundle);
    mContext = getApplicationContext();
  }
  
  protected PackageDetector getPackageDetector() {
    return ServiceLocator.getPackageDetector();
  }
  
}
