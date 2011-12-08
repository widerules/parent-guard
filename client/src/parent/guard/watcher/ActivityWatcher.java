package parent.guard.watcher;

import parent.guard.Locker;
import parent.guard.activity.BaseActivity;
import parent.guard.model.AndroidAsset;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ActivityWatcher extends Service {
  private Context mContext;
  private LogcatReader mLogcatReader;
  
  private OnActivityLaunchListener mListener = new OnActivityLaunchListener() {
    public void onResume(AndroidAsset pAndroidAsset) {
      Intent tIntent = new Intent(ActivityWatcher.this, Locker.class);
      tIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      tIntent.putExtra(BaseActivity.KEY_PACKAGE_NAME,
          pAndroidAsset.getPackageName());
      tIntent.putExtra(BaseActivity.KEY_ACTIVITY_NAME,
          pAndroidAsset.getActivityName());
      mContext.startActivity(tIntent);
    }
  };

  @Override
  public IBinder onBind(Intent pIntent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
    mLogcatReader = new LogcatReader(mListener);
    mLogcatReader.start();
  }

  @Override
  public void onDestroy() {
	  
    super.onDestroy();
    mLogcatReader.destroy() ;
    
  }
}
