package parent.guard.watcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import parent.guard.model.AndroidAsset;
import parent.guard.utility.ComponentParser;

public class LogcatReader extends Thread {
  private static final String LOGCAT_CLEAN = "logcat -c";
  private static final String LOGCAT_READ = "logcat -v raw ActivityManager:I *:S";
  private static final int BUFFER_SIZE = 1024;
  
  private Process mProcess;
  private OnActivityLaunchListener mOnActivityLaunchListener;
  private AndroidAsset mAndroidAsset;
  
  public LogcatReader(OnActivityLaunchListener pOnActivityResumeListener) {
    mOnActivityLaunchListener = pOnActivityResumeListener;
    mAndroidAsset = new AndroidAsset();
  }
  
  public void run() {
    try {
      mProcess = Runtime.getRuntime().exec(LOGCAT_CLEAN);
      mProcess = Runtime.getRuntime().exec(LOGCAT_READ);
      BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(
          mProcess.getInputStream()), BUFFER_SIZE);
      String tLineReader;
      while((tLineReader = tBufferedReader.readLine()) != null) {
        checkLaunchingEvent(tLineReader.trim());
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  private synchronized void checkLaunchingEvent(String pComponent) {
    ComponentParser tComponentParser = ComponentParser.getDefault();
    if(tComponentParser.parser(pComponent, mAndroidAsset)) {
      mOnActivityLaunchListener.onResume(mAndroidAsset);
    }
  }
}
