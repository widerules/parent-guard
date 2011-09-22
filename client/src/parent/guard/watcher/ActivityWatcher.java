package parent.guard.watcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ActivityWatcher extends Service {
  private Context mContext;
  private LogcatReader mLogcatReader;
  
  private OnActivityResumeListener mListener = new OnActivityResumeListener() {
    public void onResume() {
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
    mLogcatReader = new LogcatReader(mContext, mListener);
    mLogcatReader.start();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }
  
  
}
