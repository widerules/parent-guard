package parent.guard.watcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class LogcatReader extends Thread {
  private static final String LOGCAT_CLEAN = "logcat -c";
  private static final String LOGCAT_READ = "logcat -v raw ActivityManager:I *:S";
  private static final int BUFFER_SIZE = 1024;
  
  private Context mContext;
  private Process mProcess;
  private OnActivityResumeListener mOnActivityResumeListener;
  
  public LogcatReader(Context pContext,
      OnActivityResumeListener pOnActivityResumeListener) {
    mContext = pContext;
    mOnActivityResumeListener = pOnActivityResumeListener;
  }
  
  public void run() {
    try {
      mProcess = Runtime.getRuntime().exec(LOGCAT_CLEAN);
      mProcess = Runtime.getRuntime().exec(LOGCAT_READ);
      BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(
          mProcess.getInputStream()), BUFFER_SIZE);
      String tLineReader;
      while((tLineReader = tBufferedReader.readLine()) != null) {
        if(tLineReader.startsWith("Starting activity: Intent { ")) {
          int tIndexStart = tLineReader.indexOf("comp={");
          int tIndexEnd = tLineReader.substring(tIndexStart).indexOf("{");
          String tComponent = tLineReader.substring(tIndexStart, tIndexEnd);
          mOnActivityResumeListener.onResume();
        }
        Log.d("++", tLineReader);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
