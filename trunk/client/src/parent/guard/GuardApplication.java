package parent.guard;

import parent.guard.watcher.ActivityWatcher;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

public class GuardApplication extends Application {
  private static GuardApplication sGuardApplication;
  @Override
  public void onCreate() {
    super.onCreate();
    sGuardApplication = this;
    ServiceLocator.initilize(sGuardApplication);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    ServiceLocator.destroy();
  }
  
  public static GuardApplication getGuardApplication() {
    return sGuardApplication;
  }
  
  public void startWatcher() {
    new Thread() {
      public void run() {
        Intent serviceIntent = new Intent("swin.appjoy.watcher.ActivityWatcher");
        serviceIntent.setComponent(new ComponentName(getPackageName(),
            ActivityWatcher.class.getName()));
        startService(serviceIntent);
      }
    }.start();
  }
}
